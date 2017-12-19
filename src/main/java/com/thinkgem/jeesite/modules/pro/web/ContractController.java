/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.runner.Request;
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
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractPayService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同Controller
 * @author cdoublej
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contract")
public class ContractController extends BaseController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ProjectService projectService;
	@Autowired 
	private ContractPayService contractPayService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private InvoiceService invoiceService;
	@ModelAttribute
	public Contract get(@RequestParam(required=false) String id) {
		Contract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractService.get(id);
		}
		if (entity == null){
			entity = new Contract();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contract:view")
	@RequestMapping(value = {"list", ""})
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userId = UserUtils.getUser().getId();
		if(userId.equals("1") || userId.equals("2")){
			//超级管理员，开发人员账号
		}else{
			//权限
			Integer employeeId = UserUtils.getUser().getEmployeeId();
		 
			contract.setAuthorityEmployeeId(employeeId.toString());
		}
		//权限
//		String sysAreaId = null;
//		try{
//			Integer employeeId = UserUtils.getUser().getEmployeeId();
//			sysAreaId = employeeService.get(employeeId.toString()).getSysArea().getId();
//		}catch(Exception e){
//			sysAreaId = "0";
//		}
//		contract.setBig_sysAreaId(sysAreaId);
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		
		model.addAttribute("project", project);
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract); 
		model.addAttribute("page", page);
		return "modules/pro/contractList";
	}
	
	
	@RequiresPermissions("pro:contract:view")
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
		return "modules/pro/projectDetailList3";
	}
	
	
//	@RequiresPermissions("pro:contract:view")
	@RequestMapping(value = "form")
	public String form(Contract contract, Model model,HttpServletRequest request, HttpServletResponse response) {
		String projectId = request.getParameter("projectId");
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
		model.addAttribute("projectId", projectId);
		model.addAttribute("contract", contract);
		return "modules/pro/contractForm";
	}

	@RequiresPermissions("pro:contract:edit")
	@RequestMapping(value = "save")
	public String save(String paytype,String [] paydate,Contract contract, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		if (!beanValidator(model, contract)){
			return form(contract, model,null,null);
		}
		String conde = request.getParameter("contractCode");
		contractService.save(contract);
        int contractId =Integer.parseInt( contract.getId());
		ContractPay contractPay = new ContractPay();
		contractPayService.deleteAll(contract.getId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
		 //按月确认
		if(paytype.equals("2") && paydate.length > 0){
			for(int i = 0; paydate.length > i;i+=5){
				contractPay.setContractId(contractId);
				contractPay.setPayDate(date.parse(paydate[i]));
				contractPay.setPayPercent(Double.parseDouble(paydate[i + 1]));
				contractPay.setPayAmount(Double.parseDouble(paydate[i + 2]));
				contractPay.setAppointePayDate(simpleDateFormat.parse(paydate[i+3]));
				contractPay.setRemarks(paydate[i+4]);
				contractPayService.insert(contractPay);
			}
		}
		//按次确认 
		else if(paytype.equals("1") && paydate.length > 0){
					for(int i = 0; paydate.length > i;i+=4){
						contractPay.setContractId(contractId);
					 
						contractPay.setPayPercent(Double.parseDouble(paydate[i]));
						contractPay.setPayAmount(Double.parseDouble(paydate[i + 1]));
						contractPay.setAppointePayDate(simpleDateFormat.parse(paydate[i+2]));
						contractPay.setRemarks(paydate[i+3]);
						contractPayService.insert(contractPay);
					}
			 }
		//按量确认
		else if(paytype.equals("3") && paydate.length > 0){
			for(int i = 0; paydate.length > i;i+=5){
				contractPay.setContractId(contractId);			
				contractPay.setPayUnit(paydate[i]);
				contractPay.setPayAmount(Double.parseDouble(paydate[i + 1]));	
				contractPay.setCount(Integer.parseInt(paydate[i + 2]));
				contractPay.setAppointePayDate(simpleDateFormat.parse(paydate[i + 3]));
				contractPay.setRemarks(paydate[i + 4]);
				contractPayService.insert(contractPay);
			}
		}
		//按订单确认
		else if(paytype.equals("4") && paydate.length > 0){
			contractPay.setContractId(contractId);
			contractPay.setAppointePayDate(simpleDateFormat.parse(paydate[0]));
			contractPay.setRemarks(paydate[1]);
			contractPayService.insert(contractPay);
		}
		 
		addMessage(redirectAttributes, "保存合同成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contract/list?projectId="+contract.getProjectId();
	}
	
	@RequiresPermissions("pro:contract:edit")
	@RequestMapping(value = "delete")
	public String delete( Contract contract, RedirectAttributes redirectAttributes) {
		try{
			contractService.delete(contract);
		}catch(Exception e){
			addMessage(redirectAttributes, "合同已被关联，不能删除改合同");
			return "redirect:"+Global.getAdminPath()+"/pro/contract/?repage";
		}
		
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contract/list?projectId="+contract.getProjectId();
	}
	@RequiresPermissions("sys:customer:view")
	@RequestMapping("contractSelect")
	public String contractSelect(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String selecttype = request.getParameter("selecttype");
		if( request.getParameter("projectid") != null &&  request.getParameter("projectid") != ""){
			contract.setProjectId(Integer.parseInt(request.getParameter("projectid")));
			model.addAttribute("projectid",request.getParameter("projectid"));
		}
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response), contract); 
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/pro/contractSelect";
	}
	
	@RequestMapping(value ="selectContract",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectContract(HttpServletRequest request, HttpServletResponse response, Model model) {		
		String contractid = request.getParameter("projectid");	 
		Contract contract = invoiceService.getajaxinformatin(contractid);
		Map<String, String> map = new HashedMap();
		map.put("contract_contractCode", contract.getContractCode());
		map.put("contractAmount", contract.getAmount().toString());
		map.put("invoice_content", contract.getInvoiceContent().getName());
		map.put("invoiceTypeId", contract.getInvoiceType().getName());
		map.put("invoiceRevenue",DictUtils.getDictLabels(contract.getInvoiceRevenue(), "tax_percent", ""));
		//map.put("contract_customerId", contract.getCustomer().getName());
		return map;
	}
}