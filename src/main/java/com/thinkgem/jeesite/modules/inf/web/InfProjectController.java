/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inf.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 项目Controller
 * @author cdoublej
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/inf/project")
public class InfProjectController extends BaseController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ContractService contractService;
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
	
	@RequiresPermissions("inf:project:view")
	@RequestMapping(value = {"list", ""})
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
/*		//权限
		Integer employeeId = UserUtils.getUser().getEmployeeId();
	 
		project.setAuthorityEmployeeId(employeeId.toString());*/
		Page<Project> page = projectService.findInfDetailPage(new Page<Project>(request, response), project); 
		
		model.addAttribute("page", page);
		return "modules/inf/projectDetailList";
	}

//	@RequiresPermissions("pro:project:view")
	@RequestMapping(value = "form")
	public String form(Project project, Model model) {
		if(project.getCustomer() ==null || project.getProjectCode().equals("")){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date date = new Date();
			project.setProjectCode("0756AA"+simpleDateFormat.format(date));
		}
		model.addAttribute("project", project);
		return "modules/inf/projectForm";
	}

}