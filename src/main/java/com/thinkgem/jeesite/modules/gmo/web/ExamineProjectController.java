/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.Customer;

/**
 * 项目Controller
 * @author cdoublej
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/examineProject")
public class ExamineProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute
	public Project get(@RequestParam(required=false) String id) {
		Project entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectService.get(id);
		}
		if (entity == null){
			entity = new Project();
		}
		return entity;
	}
	
	@RequiresPermissions("gmo:examineProject:view")
	@RequestMapping(value = {"list", ""})
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Project> page = projectService.findExaminePage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/gmo/examineProjectList";
	}

	@RequiresPermissions("gmo:examineProject:view")
	@RequestMapping(value = "form")
	public String form(Project project, Model model) {
		model.addAttribute("project", project);
		return "modules/gmo/examineProjectForm";
	}

	@RequiresPermissions("gmo:examineProject:edit")
	@RequestMapping(value = "save")
	public String save(Project project, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String status = request.getParameter("status");
		if (!beanValidator(model, project)){
			return form(project, model);
		}
		project.setExaminestatus(status);
		projectService.save(project);
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/examineProject/?repage";
	}
 
}