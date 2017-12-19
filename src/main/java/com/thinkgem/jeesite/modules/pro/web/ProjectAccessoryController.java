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
import com.thinkgem.jeesite.modules.pro.entity.ProjectAccessory;
import com.thinkgem.jeesite.modules.pro.service.ProjectAccessoryService;

/**
 * 项目附件Controller
 * @author cdoublej
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/projectAccessory")
public class ProjectAccessoryController extends BaseController {

	@Autowired
	private ProjectAccessoryService projectAccessoryService;
	
	@ModelAttribute
	public ProjectAccessory get(@RequestParam(required=false) String id) {
		ProjectAccessory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectAccessoryService.get(id);
		}
		if (entity == null){
			entity = new ProjectAccessory();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:projectAccessory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectAccessory projectAccessory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectAccessory> page = projectAccessoryService.findPage(new Page<ProjectAccessory>(request, response), projectAccessory); 
		model.addAttribute("page", page);
		return "modules/pro/projectAccessoryList";
	}

	@RequiresPermissions("pro:projectAccessory:view")
	@RequestMapping(value = "form")
	public String form(ProjectAccessory projectAccessory, Model model) {
		model.addAttribute("projectAccessory", projectAccessory);
		return "modules/pro/projectAccessoryForm";
	}

	@RequiresPermissions("pro:projectAccessory:edit")
	@RequestMapping(value = "save")
	public String save(ProjectAccessory projectAccessory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectAccessory)){
			return form(projectAccessory, model);
		}
		projectAccessoryService.save(projectAccessory);
		addMessage(redirectAttributes, "保存项目附件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/projectAccessory/?repage";
	}
	
	@RequiresPermissions("pro:projectAccessory:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectAccessory projectAccessory, RedirectAttributes redirectAttributes) {
		projectAccessoryService.delete(projectAccessory);
		addMessage(redirectAttributes, "删除项目附件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/projectAccessory/?repage";
	}

}