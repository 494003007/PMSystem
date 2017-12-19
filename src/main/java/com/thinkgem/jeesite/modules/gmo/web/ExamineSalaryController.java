/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.SalaryService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 员工薪资基准Controller
 * @author cdoublej
 * @version 2016-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/salary")
public class ExamineSalaryController extends BaseController {
	@Autowired
	private AttendanceListService attendanceListService;
	@Autowired
	private PositionTypeService positionTypeService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired 
	private SysAreaService sysAreaService;

	@Autowired
	private SalaryService salaryService;
	
	@ModelAttribute
	public Salary get(@RequestParam(required=false) String id) {
		Salary entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salaryService.get(id);
		}
		if (entity == null){
			entity = new Salary();
		}
		return entity;
	}
	 
	@RequiresPermissions("gmo:salary:view")
	@RequestMapping(value = {"list", ""})
	public String list(Salary salary ,HttpServletRequest request, HttpServletResponse response, Model model) {
		 
		Page<Salary> page = salaryService.findExaminePage(new Page<Salary>(request, response), salary); 
		model.addAttribute("page", page);
 
		return "modules/gmo/salaryList";
	}

	
	
	@RequiresPermissions("gmo:salary:view")
	@RequestMapping(value = "form")
	public String form(Salary salary, Model model,HttpServletRequest request, HttpServletResponse response) {
 
		model.addAttribute("salary", salary);
	 
		return "modules/gmo/salaryForm";
	}

 
	
	@RequiresPermissions("gmo:salary:edit")
	@RequestMapping(value = "save")
	public String save(Salary salary, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, salary)){
			return form(salary, model,request,response);
		}
		String examineStatus = request.getParameter("type");
		salary.setExamineStatus(examineStatus);
		salaryService.examineUpdate(salary);
		addMessage(redirectAttributes, "员工薪资基准审核成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/salary";
	}
	
	 

}