/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
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
import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import com.thinkgem.jeesite.modules.fin.service.CurrentSalaryStandardService;

/**
 * current_salary_standardController
 * @author czy
 * @version 2016-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/currentSalaryStandard")
public class CurrentSalaryStandardController extends BaseController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CurrentSalaryStandardService currentSalaryStandardService;
	@ModelAttribute
	public CurrentSalaryStandard get(@RequestParam(required=false) String id) {
		CurrentSalaryStandard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentSalaryStandardService.get(id);
		}
		if (entity == null){
			entity = new CurrentSalaryStandard();
		}
		return entity;
	}
	
	@RequiresPermissions("fin:currentSalaryStandard:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentSalaryStandard currentSalaryStandard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentSalaryStandard> page = currentSalaryStandardService.findPage(new Page<CurrentSalaryStandard>(request, response), currentSalaryStandard);

		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("page", page);
		return "modules/fin/currentSalaryStandardList";
	}

	@RequiresPermissions("fin:currentSalaryStandard:view")
	@RequestMapping(value = "form")
	public String form(CurrentSalaryStandard currentSalaryStandard, Model model) {
		model.addAttribute("currentSalaryStandard", currentSalaryStandard);
		return "modules/fin/currentSalaryStandardForm";
	}

	@RequiresPermissions("fin:currentSalaryStandard:edit")
	@RequestMapping(value = "save")
	public String save(CurrentSalaryStandard currentSalaryStandard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentSalaryStandard)){
			return form(currentSalaryStandard, model);
		}

		currentSalaryStandardService.save(currentSalaryStandard);
		addMessage(redirectAttributes, "保存current_salary_standard成功");
		return "redirect:"+Global.getAdminPath()+"/fin/currentSalaryStandard/?repage";
	}
	
	@RequiresPermissions("fin:currentSalaryStandard:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentSalaryStandard currentSalaryStandard, RedirectAttributes redirectAttributes) {
		currentSalaryStandardService.delete(currentSalaryStandard);
		addMessage(redirectAttributes, "删除current_salary_standard成功");
		return "redirect:"+Global.getAdminPath()+"/fin/currentSalaryStandard/?repage";
	}

}