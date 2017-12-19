/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.service.DepartmentService;

/**
 * 部门设置Controller
 * @author cdoublej
 * @version 2016-10-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/department")
public class DepartmentController extends BaseController {

	@Autowired
	private DepartmentService departmentService;
	
	@ModelAttribute
	public Department get(@RequestParam(required=false) String id) {
		Department entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = departmentService.get(id);
		}
		if (entity == null){
			entity = new Department();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:department:view")
	@RequestMapping(value = {"list", ""})
	public String list(Department department, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Department> page = departmentService.findPage(new Page<Department>(request, response), department); 
		model.addAttribute("page", page);
		return "modules/sys/departmentList";
	}

	@RequiresPermissions("sys:department:view")
	@RequestMapping(value = "form")
	public String form(Department department, Model model) {
		model.addAttribute("department", department);
		return "modules/sys/departmentForm";
	}

	@RequiresPermissions("sys:department:edit")
	@RequestMapping(value = "save")
	public String save(Department department, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, department)){
			return form(department, model);
		}
		departmentService.save(department);
		addMessage(redirectAttributes, "保存部门成功");
		return "redirect:"+Global.getAdminPath()+"/sys/department/?repage";
	}
	
	@RequiresPermissions("sys:department:edit")
	@RequestMapping(value = "delete")
	public String delete(Department department, RedirectAttributes redirectAttributes) {
		try{
			departmentService.delete(department);
		}catch(Exception e){
			addMessage(redirectAttributes, "部门已经被关联，不能删除该部门");
			return "redirect:"+Global.getAdminPath()+"/sys/department/?repage";
		}
		addMessage(redirectAttributes, "删除部门成功");
		return "redirect:"+Global.getAdminPath()+"/sys/department/?repage";
	}

}