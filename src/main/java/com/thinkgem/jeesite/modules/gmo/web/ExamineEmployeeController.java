/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

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
@RequestMapping(value = "${adminPath}/gmo/examineemployee")
public class ExamineEmployeeController extends BaseController {

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
 

	@RequiresPermissions("gmo:quitemployee:view")
	@RequestMapping("examinequitlist")
	public String examinequitlist(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findexaminequitPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/gmo/quitEmployeeList";
	}
	
	
	@RequiresPermissions("gmo:quitemployee:view")
	@RequestMapping(value = "examinequitForm")
	public String examinequiteForm(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/gmo/quitEmployeeForm";
	}

	
	@RequiresPermissions("gmo:quitemployee:edit")
	@RequestMapping(value = "examinequitSave")
	public String examinequitSave(Employee employee, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		 
		String quitexaminestatus = request.getParameter("quitexaminestatus");
		employee.setQuitExamineStatus(quitexaminestatus);
		if(quitexaminestatus.equals("2"))//通过
			employee.setStatus("4");//成功 离职状态
			
		else{
			employee.setStatus("2");//失败 在职状态
		}

		employeeService.examineQuitUpdate(employee);
		addMessage(redirectAttributes, "员工离职审核成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/examineemployee/examinequitlist/?repage";
	}
	
	@RequiresPermissions("gmo:entryemployee:view")
	@RequestMapping(value = "examineentrylist")
	public String examineentrylist(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findexamineentryPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/gmo/employeeList";
	}
	
	@RequiresPermissions("gmo:entryemployee:view")
	@RequestMapping(value = "examineentryform")
	public String examineentryform(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/gmo/employeeForm";
	}

	@RequiresPermissions("gmo:entryemployee:edit")
	@RequestMapping(value = "examineentrysave")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String entryexaminestatus = request.getParameter("entryexaminestatus");
		employee.setEntryExamineStatus(entryexaminestatus);
		if(entryexaminestatus.equals("2"))//通过
			employee.setStatus("2");//成功 在职状态
			
		else{
			employee.setStatus("1");//失败 在职状态
		}

		
		employeeService.examineEntryUpdate(employee);
		addMessage(redirectAttributes, "员工入职审核成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/examineemployee/examineentrylist?repage";
	}
 

}