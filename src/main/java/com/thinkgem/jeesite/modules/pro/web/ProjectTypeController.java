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
import com.thinkgem.jeesite.modules.pro.entity.ProjectType;
import com.thinkgem.jeesite.modules.pro.service.ProjectTypeService;

/**
 * 项目类型Controller
 * @author cdoublej
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/projectType")
public class ProjectTypeController extends BaseController {

	@Autowired
	private ProjectTypeService projectTypeService;
	
	@ModelAttribute
	public ProjectType get(@RequestParam(required=false) String id) {
		ProjectType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectTypeService.get(id);
		}
		if (entity == null){
			entity = new ProjectType();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:projectType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectType projectType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectType> page = projectTypeService.findPage(new Page<ProjectType>(request, response), projectType); 
		model.addAttribute("page", page);
		return "modules/pro/projectTypeList";
	}

	@RequiresPermissions("pro:projectType:view")
	@RequestMapping(value = "form")
	public String form(ProjectType projectType, Model model) {
		model.addAttribute("projectType", projectType);
		return "modules/pro/projectTypeForm";
	}

	@RequiresPermissions("pro:projectType:edit")
	@RequestMapping(value = "save")
	public String save(ProjectType projectType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectType)){
			return form(projectType, model);
		}
		projectTypeService.save(projectType);
		addMessage(redirectAttributes, "保存项目类型成功");
		return "redirect:"+Global.getAdminPath()+"/pro/projectType/?repage";
	}
	
	@RequiresPermissions("pro:projectType:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectType projectType, RedirectAttributes redirectAttributes) {
		try{
			projectTypeService.delete(projectType);
		}catch(Exception e){
			addMessage(redirectAttributes, "项目类型已经被关联，不能删除该项目类型");
			return "redirect:"+Global.getAdminPath()+"/pro/projectType/?repage";
		}
		addMessage(redirectAttributes, "删除项目类型成功");
		return "redirect:"+Global.getAdminPath()+"/pro/projectType/?repage";
	}

}