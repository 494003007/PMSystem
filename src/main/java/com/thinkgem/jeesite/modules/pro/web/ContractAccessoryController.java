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
import com.thinkgem.jeesite.modules.pro.entity.ContractAccessory;
import com.thinkgem.jeesite.modules.pro.service.ContractAccessoryService;

/**
 * 合同附件Controller
 * @author cdoublej
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractAccessory")
public class ContractAccessoryController extends BaseController {

	@Autowired
	private ContractAccessoryService contractAccessoryService;
	
	@ModelAttribute
	public ContractAccessory get(@RequestParam(required=false) String id) {
		ContractAccessory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractAccessoryService.get(id);
		}
		if (entity == null){
			entity = new ContractAccessory();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractAccessory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractAccessory contractAccessory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractAccessory> page = contractAccessoryService.findPage(new Page<ContractAccessory>(request, response), contractAccessory); 
		model.addAttribute("page", page);
		return "modules/pro/contractAccessoryList";
	}

	@RequiresPermissions("pro:contractAccessory:view")
	@RequestMapping(value = "form")
	public String form(ContractAccessory contractAccessory, Model model) {
		model.addAttribute("contractAccessory", contractAccessory);
		return "modules/pro/contractAccessoryForm";
	}

	@RequiresPermissions("pro:contractAccessory:edit")
	@RequestMapping(value = "save")
	public String save(ContractAccessory contractAccessory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractAccessory)){
			return form(contractAccessory, model);
		}
		contractAccessoryService.save(contractAccessory);
		addMessage(redirectAttributes, "保存合同附件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractAccessory/?repage";
	}
	
	@RequiresPermissions("pro:contractAccessory:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractAccessory contractAccessory, RedirectAttributes redirectAttributes) {
		contractAccessoryService.delete(contractAccessory);
		addMessage(redirectAttributes, "删除合同附件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractAccessory/?repage";
	}

}