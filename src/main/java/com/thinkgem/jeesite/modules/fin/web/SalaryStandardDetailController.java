/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

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
import com.thinkgem.jeesite.modules.fin.entity.SalaryStandardDetail;
import com.thinkgem.jeesite.modules.fin.service.SalaryStandardDetailService;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2016-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/salaryStandardDetail")
public class SalaryStandardDetailController extends BaseController {

	@Autowired
	private SalaryStandardDetailService salaryStandardDetailService;
	
	@ModelAttribute
	public SalaryStandardDetail get(@RequestParam(required=false) String id) {
		SalaryStandardDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salaryStandardDetailService.get(id);
		}
		if (entity == null){
			entity = new SalaryStandardDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fin:salaryStandardDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SalaryStandardDetail salaryStandardDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SalaryStandardDetail> page = salaryStandardDetailService.findPage(new Page<SalaryStandardDetail>(request, response), salaryStandardDetail); 
		model.addAttribute("page", page);
		return "modules/fin/salaryStandardDetailList";
	}

	@RequiresPermissions("fin:salaryStandardDetail:view")
	@RequestMapping(value = "form")
	public String form(SalaryStandardDetail salaryStandardDetail, Model model) {
		model.addAttribute("salaryStandardDetail", salaryStandardDetail);
		return "modules/fin/salaryStandardDetailForm";
	}

	@RequiresPermissions("fin:salaryStandardDetail:edit")
	@RequestMapping(value = "save")
	public String save(SalaryStandardDetail salaryStandardDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, salaryStandardDetail)){
			return form(salaryStandardDetail, model);
		}
		salaryStandardDetailService.save(salaryStandardDetail);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/fin/salaryStandardDetail/?repage";
	}
	
	@RequiresPermissions("fin:salaryStandardDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SalaryStandardDetail salaryStandardDetail, RedirectAttributes redirectAttributes) {
		salaryStandardDetailService.delete(salaryStandardDetail);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/fin/salaryStandardDetail/?repage";
	}

}