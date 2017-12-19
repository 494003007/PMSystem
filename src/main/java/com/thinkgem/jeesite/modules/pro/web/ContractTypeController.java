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
import com.thinkgem.jeesite.modules.pro.entity.ContractType;
import com.thinkgem.jeesite.modules.pro.service.ContractTypeService;

/**
 * 合同类型Controller
 * @author cdoublej
 * @version 2016-10-08
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractType")
public class ContractTypeController extends BaseController {

	@Autowired
	private ContractTypeService contractTypeService;
	
	@ModelAttribute
	public ContractType get(@RequestParam(required=false) String id) {
		ContractType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractTypeService.get(id);
		}
		if (entity == null){
			entity = new ContractType();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractType contractType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractType> page = contractTypeService.findPage(new Page<ContractType>(request, response), contractType); 
		model.addAttribute("page", page);
		return "modules/pro/contractTypeList";
	}

	@RequiresPermissions("pro:contractType:view")
	@RequestMapping(value = "form")
	public String form(ContractType contractType, Model model) {
		model.addAttribute("contractType", contractType);
		return "modules/pro/contractTypeForm";
	}

	@RequiresPermissions("pro:contractType:edit")
	@RequestMapping(value = "save")
	public String save(ContractType contractType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractType)){
			return form(contractType, model);
		}
		contractTypeService.save(contractType);
		addMessage(redirectAttributes, "保存合同类型成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractType/?repage";
	}
	
	@RequiresPermissions("pro:contractType:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractType contractType, RedirectAttributes redirectAttributes) {
		contractTypeService.delete(contractType);
		addMessage(redirectAttributes, "删除合同类型成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractType/?repage";
	}

}