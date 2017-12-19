/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.web;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
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
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.bus.service.ContractInvoiceConfirmService;
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractBudget;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 发票Controller
 * @author fy
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/bus/invoice")
public class InvoiceController extends BaseController {
	@Autowired
	private ContractService contractService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ContractInvoiceConfirmService invoiceConfirmService;
	@Autowired
	private InvoiceService invoiceService;
	
	@ModelAttribute
	public Invoice get(@RequestParam(required=false) String id) {
		Invoice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = invoiceService.get(id);
		}
		if (entity == null){
			entity = new Invoice();
		}
		return entity;
	}
	
	@RequiresPermissions("bus:invoice:view")
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
		return "modules/bus/projectDetailList";
	}
	
	@RequiresPermissions("bus:invoice:view")
	@RequestMapping("invoiceDetaillist")
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
		return "modules/bus/invoiceDetailList";
	}
	
	
	
	@RequiresPermissions("bus:invoice:view")
	@RequestMapping(value = {"list", ""})
	public String list(Invoice invoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		String contractId = request.getParameter("contractId");	
		Contract contract = contractService.get(contractId);
		model.addAttribute("contract", contract);
	 
		String projectId = request.getParameter("projectId");		
		model.addAttribute("projectId", projectId);
		
		Page<Invoice> page = invoiceService.findPage(new Page<Invoice>(request, response), invoice); 
		model.addAttribute("page", page);
		return "modules/bus/invoiceList";
	}

	@RequiresPermissions("bus:invoice:view")
	@RequestMapping(value = "form")
	public String form(Invoice invoice, Model model,HttpServletRequest request, HttpServletResponse response) {
		String contractId = request.getParameter("contractId")==null?"": request.getParameter("contractId");	
		String projectId = request.getParameter("projectId")==null?"": request.getParameter("projectId");
		if(!projectId.equals("") && !contractId.equals("")){
			Project project = projectService.get(projectId);
			Contract contract = contractService.get(contractId);
			invoice.setProject(project);
			invoice.setContract(contract);
		}
		model.addAttribute("invoice", invoice);
		return "modules/bus/invoiceForm";
	}
	 
	@RequiresPermissions("bus:invoice:edit")
	@RequestMapping(value = "save")
	public String save(Invoice invoice, Model model, RedirectAttributes redirectAttributes,@Param("contractconfirm")String contractconfirm,HttpServletRequest request, HttpServletResponse response) {
				
		if (!beanValidator(model, invoice)){
			return form(invoice, model,null,null);
		}
		invoice.setStatus("1");
		invoiceService.save(invoice);
		String confirmId = "";
		String invoiceId = "," + invoice.getId() + ",";
		invoiceConfirmService.deleteAll(invoiceId);
		
		if(invoice.getIsAdvanceCharge().equals("1")){// no
			ContractInvoiceConfirm contractInvoiceConfirm = new ContractInvoiceConfirm();
		
		    confirmId = ","+contractconfirm +",";	
		    contractInvoiceConfirm.setInvoiceId(invoiceId);
		    contractInvoiceConfirm.setContractConfirmId(confirmId);
		    invoiceConfirmService.save(contractInvoiceConfirm);
		    
		}	
		String contractId = request.getParameter("contractId");	
		String projectId = request.getParameter("projectId");		
		addMessage(redirectAttributes, "保存发票成功");
		return "redirect:"+Global.getAdminPath()+"/bus/invoice/?projectId="+projectId+"&contractId="+contractId;
	}
	 
	@RequiresPermissions("bus:invoice:edit")
	@RequestMapping(value = "delete")
	public String delete(Invoice invoice, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		
		String contractId = request.getParameter("contractId");	
		String projectId = request.getParameter("projectId");	
		try{
			invoiceService.delete(invoice);
		}catch(Exception e){
			addMessage(redirectAttributes, "该发票已经被关联，不能删除该发票");
			return "redirect:"+Global.getAdminPath()+"/bus/invoice/?projectId="+projectId+"&contractId="+contractId;
		}
		addMessage(redirectAttributes, "删除发票成功");
		return "redirect:"+Global.getAdminPath()+"/bus/invoice/?projectId="+projectId+"&contractId="+contractId;
	}
 
	
	
	@RequiresPermissions("sys:customer:view")
	@RequestMapping("selectInvoice")
	public String selectInvoice(Invoice invoice,  HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Invoice> page = invoiceService.findSelectPage(new Page<Invoice>(request, response), invoice); 
		String selecttype = request.getParameter("selecttype");
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/bus/invoiceSelect";
	}
	
}