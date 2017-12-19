/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractBudget;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractBudgetService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

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
import com.thinkgem.jeesite.modules.pro.entity.ContractCost;
import com.thinkgem.jeesite.modules.pro.service.ContractCostService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 成本录入Controller
 * @author ThinkGem
 * @version 2016-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractCost")
public class ContractCostController extends BaseController {

	@Autowired
	private ContractCostService contractCostService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ContractBudgetService contractBudgetService;

	@ModelAttribute
	public ContractCost get(@RequestParam(required=false) String id) {
		ContractCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractCostService.get(id);
		}
		if (entity == null){
			entity = new ContractCost();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractCost:view")
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
		return "modules/pro/projectDetailList2";
	}
	
	@RequiresPermissions("pro:contractCost:view")
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
		return "modules/pro/contractCostList";
	}
	
//	@RequiresPermissions("pro:contractCost:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<Contract> orderPage = new  Page<Contract>(request, response);
//		orderPage.setOrderBy("project_id asc");
//		Page<Contract> page = contractService.findPage(orderPage, contract);
//
//		ArrayList<Integer> allCost = new ArrayList<Integer>();
//		ArrayList<Double> allBurget = new ArrayList<Double>();
//		for(Contract c:page.getList()){
//			int allCostT = 0;
//			double allBurT = 0;
//			////////////////////////////////
//			ContractCost cctemp = new ContractCost();
//			cctemp.setProjectId(c.getProjectId().toString());
//			if(projectService.get(c.getProjectId().toString()).getIscostcontract().equals("2")){
//				cctemp.setContractId(c.getId());
//			}
//			for(ContractCost temp : contractCostService.findList(cctemp)){
//				allCostT += temp.getCost();
//			}
//			allCost.add(allCostT);
//			//////////////////////////////
//			ContractBudget cbt = new ContractBudget();
//			cbt.setContractId(c.getId());
//			for (ContractBudget cb:contractBudgetService.findList(cbt)){
//				allBurT+=cb.getAmount();
//			}
//			allBurget.add(allBurT);
//		}
//		model.addAttribute("page", page);
//		model.addAttribute("allCost",allCost);
//		model.addAttribute("allBurget",allBurget);
//		return "modules/pro/contractCostList";
//	}

	@RequiresPermissions("pro:contractCost:view")
	@RequestMapping(value = "form")
	public String form(ContractCost contractCost, Model model,HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("contractCost", contractCost);
		Contract contract = contractService.get(contractCost.getContractId());
		model.addAttribute("contract", contract);
		ContractCost cc = new ContractCost();
		if(contract.getProject().getIscostcontract().equals("2")){

			cc.setContractId(contract.getId());
			cc.setProjectId(contract.getProjectId().toString());
		}else{
			cc.setProjectId(contract.getProjectId().toString());
		}
		String projectId = request.getParameter("projectId");
		model.addAttribute("projectId", projectId);
		model.addAttribute("costList", contractCostService.findList(cc));
		if(model.containsAttribute("message")){
			model.addAttribute("message",model.asMap().get("message"));
		}
		return "modules/pro/contractCostForm";
	}


	@RequiresPermissions("pro:contractCost:edit")
	@RequestMapping(value = "save")
	public String save(ContractCost contractCost, Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, contractCost)){
			return form(contractCost, model,null,null);
		}
		contractCostService.save(contractCost);
		addMessage(redirectAttributes, "保存成本成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractCost/?repage";
	}

//	@RequiresPermissions("pro:contractCost:edit")
//	@RequestMapping(value = "saveAll")
//	public String saveAll(HttpServletRequest request,Model model,@RequestParam(value = "projectId") String projectId,@RequestParam(value = "contractId") String contractId,@RequestParam(value = "newCount")int newCount) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		ContractCost temp = new ContractCost();
//		temp.setProjectId(projectId);
//		temp.setContractId(contractId);
//		for(int i=0;i<newCount;i++){
//			temp = new ContractCost();
//			temp.setProjectId(projectId);
//			temp.setContractId(contractId);
//			try {
//				temp.setCostMonth(sdf.parse(request.getParameter("costMonth[" + i +"]")));
//			} catch (ParseException e) {
//				model.addAttribute("message","数据验证失败：月份格式错误<br/>");
//				return form(temp,model,null,null);
//			}
//			temp.setProjectId(projectId);
//			temp.setContractId(contractId);
//			temp.setCost(Double.parseDouble(request.getParameter("cost[" + i +"]")));
//			temp.setRemarks(request.getParameter("remarks[" + i +"]"));
//			if (beanValidator(model, temp)){
//				contractCostService.save(temp);
//			}
//		}
//		return form(temp,model,null,null);
//	}

	
	@RequiresPermissions("pro:contractCost:edit")
	@RequestMapping(value = "saveAll")
	public String saveAll(String iscostcontract,String budgedate,RedirectAttributes redirectAttributes,HttpServletRequest request,Model model,@RequestParam(value = "projectId") String projectId,@RequestParam(value = "contractId") String contractId) throws ParseException {
		String [] date = budgedate.split(",",-1);
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
		ContractCost contractCost = new ContractCost();
		if(date.length > 0){
			if(iscostcontract.equals("2"))
				contractCostService.deleteAll("contract",contractId);
			else if(iscostcontract.equals("1"))
				contractCostService.deleteAll("project",projectId);
			for(int i = 0 ; i<date.length - 1; i+=4){
				 if(iscostcontract.equals("2"))//2分成本，1不分成本
					 contractCost.setContractId(contractId);
				 contractCost.setProjectId(projectId);
				 contractCost.setCostMonth(s.parse(date[i]));
				 contractCost.setCost(Double.parseDouble(date[i + 1]));
				 contractCost.setRemarks(date[i + 2]);
				 contractCost.setFiles(date[i + 3]);
				 contractCostService.insert(contractCost);
			}
		}
		 
		addMessage(redirectAttributes, "添加成本成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractCost/list?projectId="+projectId;
	}
	
	
	
	@RequiresPermissions("pro:contractCost:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractCost contractCost, RedirectAttributes redirectAttributes) {
		contractCostService.delete(contractCost);
		addMessage(redirectAttributes, "删除成本成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractCost/?repage";
	}


}