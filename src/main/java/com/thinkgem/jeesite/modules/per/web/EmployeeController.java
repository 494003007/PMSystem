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
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;

/**
 * 员工Controller
 * @author cdoublej
 * @version 2016-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/per/employee")
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		Employee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeeService.get(id);
		}
		if (entity == null){
			entity = new Employee();
		}
		return entity;
	}

	@RequiresPermissions("per:employee:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/per/employeeList";
	}

	@RequiresPermissions("per:quitemployee:view")
	@RequestMapping("quitlist")
	public String quitlist(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findquitPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/per/quitEmployeeList";
	}
	@RequiresPermissions("per:quitemployee:view")
	@RequestMapping(value = "quitForm")
	public String quiteForm(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/per/quitEmployeeForm";
	}

	
	@RequiresPermissions("per:quitemployee:edit")
	@RequestMapping(value = "quitSave")
	public String quitSave(Employee employee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employee)){
			return form(employee, model);
		}
		employee.setStatus("3");//申请离职
		employee.setQuitExamineStatus("1");//离职未审核
		employeeService.quitUpdate(employee);
		addMessage(redirectAttributes, "员工离职申请成功");
		return "redirect:"+Global.getAdminPath()+"/per/employee/quitlist/?repage";
	}
	
	
	
//	@RequiresPermissions("per:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/per/employeeForm";
	}

	@RequiresPermissions("per:employee:edit")
	@RequestMapping(value = "save")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
	
		if (!beanValidator(model, employee)){
			return form(employee, model);
		}else{
			int count = employeeService.getOACodeCount(employee);
			if(count > 0){
			    addMessage(model, "OA编号已存在，请输入其他编号");			 
			    return form(employee, model);
			}else{
				int DAcount = employeeService.getDACodeCount(employee);
				if(DAcount > 0){
				    addMessage(model, "档案编号已存在，请输入其他编号");			 
				    return form(employee, model);
				}
			}
		}
		employee.setEntryExamineStatus("1");
		employeeService.save(employee);
		addMessage(redirectAttributes, "保存员工成功");
		return "redirect:"+Global.getAdminPath()+"/per/employee/?repage";
	}

	@RequiresPermissions("per:employee:edit")
	@RequestMapping(value = "delete")
	public String delete(Employee employee, RedirectAttributes redirectAttributes) {
		try{
			employeeService.delete(employee);
		}catch(Exception e){
			addMessage(redirectAttributes, "员工已经被关联，不能删除该员工");
			return "redirect:"+Global.getAdminPath()+"/per/employee/?repage";
		}
		addMessage(redirectAttributes, "删除员工成功");
		return "redirect:"+Global.getAdminPath()+"/per/employee/?repage";
	}

}