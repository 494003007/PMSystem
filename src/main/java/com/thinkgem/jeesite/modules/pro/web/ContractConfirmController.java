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
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.service.ContractConfirmService;

import static javax.swing.text.StyleConstants.ModelAttribute;

/**
 * 项目进度Controller
 * @author cdoublej
 * @version 2016-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractConfirm")
public class ContractConfirmController extends BaseController {

	@Autowired
	private ContractConfirmService contractConfirmService;
	
	@ModelAttribute
	public ContractConfirm get(@RequestParam(required=false) String id) {
		ContractConfirm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractConfirmService.get(id);
		}
		if (entity == null){
			entity = new ContractConfirm();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractConfirm contractConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractConfirm> page = contractConfirmService.findPage(new Page<ContractConfirm>(request, response), contractConfirm); 
		model.addAttribute("page", page);
		return "modules/pro/contractConfirmList";
	}

	@RequiresPermissions("pro:contractConfirm:view")
	@RequestMapping(value = "form")
	public String form(ContractConfirm contractConfirm, Model model) {
		model.addAttribute("contractConfirm", contractConfirm);
		return "modules/pro/contractConfirmForm";
	}

	@RequiresPermissions("pro:contractConfirm:edit")
	@RequestMapping(value = "save")
	public String save(ContractConfirm contractConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractConfirm)){
			return form(contractConfirm, model);
		}
		contractConfirmService.save(contractConfirm);
		addMessage(redirectAttributes, "保存项目进度成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractConfirm/?repage";
	}
	
	@RequiresPermissions("pro:contractConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractConfirm contractConfirm, RedirectAttributes redirectAttributes) {
		contractConfirmService.delete(contractConfirm);
		addMessage(redirectAttributes, "删除项目进度成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractConfirm/?repage";
	}

}