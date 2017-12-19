/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import com.thinkgem.jeesite.modules.fin.service.CurrentSalaryStandardService;
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
import com.thinkgem.jeesite.modules.fin.entity.LifeSubsidyDetail;
import com.thinkgem.jeesite.modules.fin.service.LifeSubsidyDetailService;

/**
 * life_subsidyController
 * @author czy
 * @version 2016-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/lifeSubsidyDetail")
public class LifeSubsidyDetailController extends BaseController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private LifeSubsidyDetailService lifeSubsidyDetailService;
	@Autowired
	private CurrentSalaryStandardService currentSalaryStandardService;
	@ModelAttribute
	public LifeSubsidyDetail get(@RequestParam(required=false) String id) {
		LifeSubsidyDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lifeSubsidyDetailService.get(id);
		}
		if (entity == null){
			entity = new LifeSubsidyDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fin:lifeSubsidyDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(LifeSubsidyDetail lifeSubsidyDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (lifeSubsidyDetail.getEmployeeId()!=null)
			lifeSubsidyDetail.setEmployee(employeeService.get(""+lifeSubsidyDetail.getEmployeeId()));
		Page<LifeSubsidyDetail> page = lifeSubsidyDetailService.findPage(new Page<LifeSubsidyDetail>(request, response), lifeSubsidyDetail);
		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("page", page);
		return "modules/fin/lifeSubsidyDetailList";
	}

	@RequiresPermissions("fin:lifeSubsidyDetail:view")
	@RequestMapping(value = "form")
	public String form(LifeSubsidyDetail lifeSubsidyDetail, Model model) {
		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("lifeSubsidyDetail", lifeSubsidyDetail);
		return "modules/fin/lifeSubsidyDetailForm";
	}

	@RequiresPermissions("fin:lifeSubsidyDetail:edit")
	@RequestMapping(value = "save")
	public String save(LifeSubsidyDetail lifeSubsidyDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, lifeSubsidyDetail)){
			return form(lifeSubsidyDetail, model);
		}
		lifeSubsidyDetailService.save(lifeSubsidyDetail);
		addMessage(redirectAttributes, "保存life_subsidy成功");
		return "redirect:"+Global.getAdminPath()+"/fin/lifeSubsidyDetail/?repage";
	}
	
	@RequiresPermissions("fin:lifeSubsidyDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(LifeSubsidyDetail lifeSubsidyDetail, RedirectAttributes redirectAttributes) {
		lifeSubsidyDetailService.delete(lifeSubsidyDetail);
		addMessage(redirectAttributes, "删除life_subsidy成功");
		return "redirect:"+Global.getAdminPath()+"/fin/lifeSubsidyDetail/?repage";
	}

}