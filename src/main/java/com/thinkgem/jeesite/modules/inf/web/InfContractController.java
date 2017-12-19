/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.inf.web;

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
@RequestMapping(value = "${adminPath}/inf/contract")
public class InfContractController extends BaseController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractPayService contractPayService;
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
	
	@RequiresPermissions("inf:contract:view")
	@RequestMapping(value = {"list", ""})
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract);
		model.addAttribute("page", page);
		return "modules/inf/contractDetailList";
	}
	
	 
	
	@RequiresPermissions("inf:contract:view")
	@RequestMapping(value = "form")
	public String form(Contract contract, Model model, HttpServletRequest request, HttpServletResponse response) {
		ContractPay contractPay = new ContractPay();
		List<ContractPay> contractPayList = new ArrayList<ContractPay>();
		String contractId = "";
		try{
			contractId = contract.getId();
		}catch(Exception e){
			contractId = "";
		}
		if(contractId != null && !contractId.equals("")){
			contractPay.setContractId(Integer.parseInt(contractId));
			contractPayList = contractPayService.findList(contractPay);		
			model.addAttribute("contractPayList", contractPayList);
			model.addAttribute("workconfirmid", contract.getWorkconfirmid());
		}
		model.addAttribute("contract", contract);
		return "modules/inf/contractForm";
	}

 
}