/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

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
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.service.EmployeeTypeService;

/**
 * 员工类型Controller
 * @author cdoublej
 * @version 2016-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/per/employeeType")
public class EmployeeTypeController extends BaseController {

	@Autowired
	private EmployeeTypeService employeeTypeService;
	
	@ModelAttribute
	public EmployeeType get(@RequestParam(required=false) String id) {
		EmployeeType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeeTypeService.get(id);
		}
		if (entity == null){
			entity = new EmployeeType();
		}
		return entity;
	}
	
	@RequiresPermissions("per:employeeType:view")
	@RequestMapping(value = {"list", ""})
	public String list(EmployeeType employeeType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EmployeeType> page = employeeTypeService.findPage(new Page<EmployeeType>(request, response), employeeType); 
		model.addAttribute("page", page);
		return "modules/per/employeeTypeList";
	}

	@RequiresPermissions("per:employeeType:view")
	@RequestMapping(value = "form")
	public String form(EmployeeType employeeType, Model model) {
		model.addAttribute("employeeType", employeeType);
		return "modules/per/employeeTypeForm";
	}

	@RequiresPermissions("per:employeeType:edit")
	@RequestMapping(value = "save")
	public String save(EmployeeType employeeType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employeeType)){
			return form(employeeType, model);
		}
		employeeTypeService.save(employeeType);
		addMessage(redirectAttributes, "保存员工类型成功");
		return "redirect:"+Global.getAdminPath()+"/per/employeeType/?repage";
	}
	
	@RequiresPermissions("per:employeeType:edit")
	@RequestMapping(value = "delete")
	public String delete(EmployeeType employeeType, RedirectAttributes redirectAttributes) {
		try{
			employeeTypeService.delete(employeeType);
		}catch(Exception e){
			addMessage(redirectAttributes, "员工类型已经被关联，不能删除该员工类型");
			return "redirect:"+Global.getAdminPath()+"/per/employeeType/?repage";
		}
		addMessage(redirectAttributes, "删除员工类型成功");
		return "redirect:"+Global.getAdminPath()+"/per/employeeType/?repage";
	}

}