/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

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
import com.thinkgem.jeesite.modules.pro.entity.Contract;
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
@RequestMapping(value = "${adminPath}/pro/project")
public class ProjectController extends BaseController {
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
	
	@RequiresPermissions("pro:project:view")
	@RequestMapping(value = {"list", ""})
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userId = UserUtils.getUser().getId();
		if(userId.equals("1") || userId.equals("2")){
			//超级管理员，开发人员账号
		}else{
			//权限
			Integer employeeId = UserUtils.getUser().getEmployeeId();
		 
			project.setAuthorityEmployeeId(employeeId.toString());
		}
		Page<Project> page = projectService.findPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/pro/projectList";
	}

	
	@RequiresPermissions("pro:project:view")
	@RequestMapping("projectList")
	public String projectList( HttpServletRequest request, HttpServletResponse response, Model model) {
		Contract contract = new Contract();
		String projectId = request.getParameter("projectId");
		contract.setProjectId(Integer.parseInt(projectId));
//		//权限
//		Integer employeeId = UserUtils.getUser().getEmployeeId();
//		String sysAreaId = employeeService.get(employeeId.toString()).getSysArea().getId();
//		contract.setBig_sysAreaId(sysAreaId);
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		Project project = projectService.getProjectDetail(projectId);
		
		model.addAttribute("project", project);
		
		model.addAttribute("page", page);
		return "modules/pro/projectContractDetailList";
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
		return "modules/pro/projectForm";
	}

	@RequiresPermissions("pro:project:edit")
	@RequestMapping(value = "save")
	public String save(Project project, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, project)){
			return form(project, model);
		}
		project.setExaminestatus("1");
		projectService.save(project);
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/pro/project/?repage";
	}
	
	@RequiresPermissions("pro:project:edit")
	@RequestMapping(value = "delete")
	public String delete(Project project, RedirectAttributes redirectAttributes) {
		try{
			projectService.delete(project);
		}catch(Exception e){
			addMessage(redirectAttributes, "项目已被关联，不能删除该项目");
			return "redirect:"+Global.getAdminPath()+"/pro/project/?repage";
		}
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/pro/project/?repage";
	}
	@RequiresPermissions("sys:customer:view")
	@RequestMapping("projectSelect")
	public String projectSelect(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		project.setExaminestatus("2");
		Page<Project> page = projectService.findPage(new Page<Project>(request, response), project); 
		String selecttype = request.getParameter("selecttype");
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		
		return "modules/pro/projectSelect";
	}
	
 
	@RequestMapping(value ="selectProject",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectProject(HttpServletRequest request, HttpServletResponse response, Model model) {		
		String projectid = request.getParameter("projectid");	 
		Project project = projectService.get(projectid);
		Map<String, String> map = new HashedMap();
		map.put("project_type", project.getProjectType().getName());
		map.put("bpm_employee", project.getBpmemployee().getName());
		map.put("pm_employee", project.getEmployee().getName());
		map.put("pm_employee_mobile", project.getEmployee().getMobile());
		map.put("iscostcontract", project.getIscostcontract());
		map.put("isframecontract", project.getIsframecontract());
		map.put("ispaycontract", project.getIspaycontract());
		map.put("sys_area", project.getSysArea().getName());
		map.put("project_id", project.getId());
		map.put("project_name", project.getName());
		map.put("companyName", project.getCustomer().getCompanyName());
		
		map.put("customerName", project.getCustomer().getName());
		map.put("customerBankType",DictUtils.getDictLabels(project.getCustomer().getBankType(), "bank", ""));
		map.put("customerBankAccount", project.getCustomer().getBankAccount());
		
	
		Integer contractcount = contractService.getCount(projectid) + 1;
		DecimalFormat d = new DecimalFormat("000");
		String contractCode = project.getProjectCode()+"-" + d.format(contractcount);
		map.put("contractCode", contractCode);
		return map;
	}
}