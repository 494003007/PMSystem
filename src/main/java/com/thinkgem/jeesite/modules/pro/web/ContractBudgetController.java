/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractPayService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.pro.entity.ContractBudget;
import com.thinkgem.jeesite.modules.pro.service.ContractBudgetService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 预算信息生成Controller
 * @author ThinkGem
 * @version 2016-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractBudget")
public class ContractBudgetController extends BaseController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractBudgetService contractBudgetService;
	
	@ModelAttribute
	public ContractBudget get(@RequestParam(required=false) String id) {
		ContractBudget entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractBudgetService.get(id);
		}
		if (entity == null){
			entity = new ContractBudget();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractBudget:view")
	@RequestMapping(value = {"list", ""})
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
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
		return "modules/pro/contractBudgetList";
	}
	
	@RequiresPermissions("pro:contractBudget:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String userId = UserUtils.getUser().getId();
		if(userId.equals("1") || userId.equals("2")){
			//超级管理员，开发人员账号
		}else{
			//权限
			Integer employeeId = UserUtils.getUser().getEmployeeId();
		 
			project.setAuthorityEmployeeId(employeeId.toString());
		}
		Page<Project> page = projectService.findDetailPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/pro/projectDetailList";
	}
	
	@RequiresPermissions("pro:contractBudget:view")
	@RequestMapping(value = "form")
	public String form(ContractBudget contractBudget, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("contractBudget", contractBudget);
		Contract contract = contractService.get(contractBudget.getContractId());
		model.addAttribute("contract", contract);
		List<ContractBudget> cbList  = contractBudgetService.findList(contractBudget);
		String projectId = request.getParameter("projectId");
		model.addAttribute("cbList", cbList);
		
		model.addAttribute("projectId", projectId);
		return "modules/pro/contractBudgetForm";
	}


	@RequiresPermissions("pro:contractBudget:edit")
	@RequestMapping(value = "save")
	public String save(ContractBudget contractBudget, Model model, RedirectAttributes redirectAttributes,Contract contract) {
		if (!beanValidator(model, contractBudget)){
			return form(contractBudget, model,null,null);
		}
		contractBudgetService.save(contractBudget);
		addMessage(redirectAttributes, "保存预算信息成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractBudget/?repage";
	}

//	@RequiresPermissions("pro:contractBudget:edit")
//	@RequestMapping(value = "saveAll",method = RequestMethod.POST)
//	public String saveAll(HttpServletRequest request,Model model,@RequestParam(value = "contractId") String contractId,@RequestParam(value = "newCount")int newCount,RedirectAttributes redirectAttributes) {
//		ContractBudget temp = new ContractBudget();
//		temp.setContractId(contractId);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		for(int i = 0;i<newCount;i++){
//			temp = new ContractBudget();
//			try {
//				temp.setStartDate(sdf.parse(request.getParameter("startDate[" + i +"]")));
//				temp.setEndDate(sdf.parse(request.getParameter("endDate[" + i +"]")));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//			temp.setAmount(Double.parseDouble(request.getParameter("amount[" + i +"]")));
//			temp.setRemarks(request.getParameter("remarks[" + i +"]"));
//			temp.setFiles(request.getParameter("files[" + i +"]"));
//			temp.setContractId(contractId);
//			if (beanValidator(model, temp)){
//				contractBudgetService.save(temp);
//			}else{
//
//			}
//		}
//		String projectId = request.getParameter("projectId");
//		addMessage(redirectAttributes, "添加预算成功");
//		return "redirect:"+Global.getAdminPath()+"/pro/contractBudget/list?projectId="+projectId;
//	}

	
	@RequiresPermissions("pro:contractBudget:edit")
	@RequestMapping(value = "saveAll",method = RequestMethod.POST)
	public String saveAll(HttpServletRequest request,Model model,@RequestParam(value = "contractId") String contractId,String budgedate,RedirectAttributes redirectAttributes) throws ParseException {
		String [] date = budgedate.split(",",-1);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		ContractBudget budget = new ContractBudget();
		if(date.length > 0){
			contractBudgetService.deleteAll(contractId);
			for(int i = 0 ; i<date.length - 1; i+=6){
				budget.setContractId(contractId);
				budget.setCreateDate(s.parse(date[i]));
				budget.setStartDate(s.parse(date[i + 1]));
				budget.setEndDate(s.parse(date[i + 2]));
				budget.setAmount(Double.parseDouble(date[i + 3]));
				budget.setRemarks(date[i + 4]);
				budget.setFiles(date[i + 5]);
				contractBudgetService.insert(budget);
			}
		}
		String projectId = request.getParameter("projectId");
		addMessage(redirectAttributes, "添加预算成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractBudget/list?projectId="+projectId;
	}
	
	
	@RequiresPermissions("pro:contractBudget:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractBudget contractBudget, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		contractBudgetService.delete(contractBudget);
		addMessage(redirectAttributes, "删除预算成功");
		String projectId = request.getParameter("projectId");
		return "redirect:"+Global.getAdminPath()+"/pro/contractBudget/form?projectId="+projectId;
	}
}