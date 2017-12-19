/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inf.web;

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
@RequestMapping(value = "${adminPath}/inf/employee")
public class InfEmployeeController extends BaseController {

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

	@RequiresPermissions("inf:employee:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/inf/employeeDetailList";
	}

	

	
//	@RequiresPermissions("per:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/per/employeeForm";
	}
}
