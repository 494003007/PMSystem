/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.service.ContractPayService;

/**
 * 付款Controller
 * @author cdoublej
 * @version 2016-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractPay")
public class ContractPayController extends BaseController {

	@Autowired
	private ContractPayService contractPayService;
	
	@ModelAttribute
	public ContractPay get(@RequestParam(required=false) String id) {
		ContractPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractPayService.get(id);
		}
		if (entity == null){
			entity = new ContractPay();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractPay contractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractPay> page = contractPayService.findPage(new Page<ContractPay>(request, response), contractPay); 
		model.addAttribute("page", page);
		return "modules/pro/contractPayList";
	}

	@RequiresPermissions("pro:contractPay:view")
	@RequestMapping(value = "form")
	public String form(ContractPay contractPay, Model model) {
		model.addAttribute("contractPay", contractPay);
		return "modules/pro/contractPayForm";
	}

	@RequiresPermissions("pro:contractPay:edit")
	@RequestMapping(value = "save")
	public String save(ContractPay contractPay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractPay)){
			return form(contractPay, model);
		}
		contractPayService.save(contractPay);
		addMessage(redirectAttributes, "保存付款成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractPay/?repage";
	}
	
	@RequiresPermissions("pro:contractPay:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractPay contractPay, RedirectAttributes redirectAttributes) {
		contractPayService.delete(contractPay);
		addMessage(redirectAttributes, "删除付款成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractPay/?repage";
	}

}