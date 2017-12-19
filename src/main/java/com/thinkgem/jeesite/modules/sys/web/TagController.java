/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;
import com.thinkgem.jeesite.modules.out.entity.OutcontractCheck;
import com.thinkgem.jeesite.modules.out.entity.OutcontractInvoice;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPayRegister;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.service.OutcontractBillService;
import com.thinkgem.jeesite.modules.out.service.OutcontractCheckService;
import com.thinkgem.jeesite.modules.out.service.OutcontractInvoiceService;
import com.thinkgem.jeesite.modules.out.service.OutcontractPayRegisterService;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import com.thinkgem.jeesite.modules.out.service.OutsourcerService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.pro.service.ContractConfirmService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.pro.service.ReturnRegisterService;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;
import com.thinkgem.jeesite.modules.sys.service.CustomerService;
import com.thinkgem.jeesite.modules.sys.service.CustomerUserService;

/**
 * 标签Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {
	@Autowired
	OutcontractPayRegisterService outcontractPayRegisterService;
	@Autowired
	ContractConfirmService contractConfirmService;
	@Autowired
	OutcontractCheckService outcontractCheckService;
	@Autowired
	OutcontractService outcontractService;
	@Autowired
	CustomerUserService customerUserService;
	@Autowired
	ReturnRegisterService registerService;
	@Autowired
	EmployeeService employeeService;	
	@Autowired
	ContractService contractService;
	@Autowired
	AttendanceListService attendanceListService;
	@Autowired
	OutsourcerService outsourcerService;
	@Autowired
	CustomerService customerService;
	@Autowired
	ProjectService projectService;
	@Autowired
	OutcontractBillService outcontractBillService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	OutcontractInvoiceService outcontractInvoiceService;
	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("module", request.getParameter("module"));	// 过滤栏目模型（仅针对CMS的Category树）
		return "modules/sys/tagTreeselect";
	}
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagIconselect";
	}
	/**
	 * 员工选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "employeeSelect")//通用员工选择框，不排除
	public String employeeSelect(Employee employee, HttpServletResponse response,HttpServletRequest request, Model model) {
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型	
		
		
		if(request.getParameter("positiontype")!=null && !request.getParameter("positiontype").equals("")){
			employee.setQuarters(request.getParameter("positiontype"));
			model.addAttribute("positiontype", request.getParameter("positiontype")); 
		}	
		Page<Employee> page = employeeService.findEmployeeTemple(new Page<Employee>(request, response),employee); 		
		model.addAttribute("page", page);
		return "modules/per/employeeSelect";
	}
	
	//添加考勤人员。排除本项目下的员工
	@RequiresPermissions("user")
	@RequestMapping(value = "addAttendance")//添加考勤员工
	public String addAttendance(Employee employee, HttpServletResponse response,HttpServletRequest request, Model model) {
		String projectId = request.getParameter("projectId").toString();
		AttendanceList attendanceList = new AttendanceList();
		attendanceList.setProjectId(projectId);
		ArrayList<AttendanceList> list = (ArrayList<AttendanceList>) attendanceListService.findList(attendanceList);
		ArrayList employeeid = new ArrayList();
		for(int i = 0; i < list.size() ; i++){
			AttendanceList attendanceList2 = list.get(i);
			employeeid.add(attendanceList2.getEmployeeId().toString());
		}
		employee.setReducelist(employeeid);
		Page<Employee> page = employeeService.findEmployeeTemple(new Page<Employee>(request, response),employee); 		
		model.addAttribute("page", page);
		model.addAttribute("selecttype","checkboxselect"); 	// 复选框		
		return "modules/per/employeeSelect";
	}
	
	/**
	 * 外包商选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "outsourcerSelect")//通用外包商选择框
	public String outsourcerSelect(Outsourcer outsourcer, HttpServletResponse response,HttpServletRequest request, Model model) {
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<Outsourcer> page = outsourcerService.findPage(new Page<Outsourcer>(request, response),outsourcer); 	
		model.addAttribute("page", page);
		return "modules/out/outsourcerSelect";
	}
	
	
	/**
	 * 发票选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "invoiceSelect")//通用外包商选择框
	public String invoiceSelect(Invoice invoice, HttpServletResponse response,HttpServletRequest request, Model model) {
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<Invoice> page = invoiceService.findSelectPage(new Page<Invoice>(request, response),invoice); 	
		model.addAttribute("page", page);
		return "modules/bus/invoiceSelect";
	}
	
	/**
	 * 客户选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "customerSelect")//通用外包商选择框
	public String customerSelect(Customer customer, HttpServletResponse response,HttpServletRequest request, Model model) {
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<Customer> page = customerService.findPage(new Page<Customer>(request, response),customer); 	
		model.addAttribute("page", page);
		return "modules/sys/customerSelect";
	}
	/**
	 * 项目选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "projectSelect")//通用外包商选择框
	public String projectSelect(Project project, HttpServletResponse response,HttpServletRequest request, Model model) {
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		project.setExaminestatus("2");
		Page<Project> page = projectService.findPage(new Page<Project>(request, response),project); 	
		model.addAttribute("page", page);
		return "modules/pro/projectSelect";
	}
	
	/**
	 * 合同选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "contractSelect")//通用外包商选择框
	public String contractSelect(Contract contract, HttpServletResponse response,HttpServletRequest request, Model model) {
		if( request.getParameter("projectid") != null &&  request.getParameter("projectid") != ""){
			contract.setProjectId(Integer.parseInt(request.getParameter("projectid")));
			model.addAttribute("projectid",  request.getParameter("projectid"));
		}
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<Contract> page = contractService.findPage(new Page<Contract>(request, response),contract); 	
		model.addAttribute("page", page);
		return "modules/pro/contractSelect";
	}
	
	/**
	 * 附件上传标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "accessorySelect")//通用 附件上传选择框
	public String accessorySelect(HttpServletResponse response,HttpServletRequest request, Model model) {
		String filesdate = request.getParameter("filesdate");
		String filesurl = request.getParameter("filesurl");
		model.addAttribute("filesdate", filesdate);
		model.addAttribute("filesurl", filesurl);
		return "modules/sys/accessorySelect";
	}
	
	/**
	 *  附件上传选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "accessorySelectUpdate")//通用 附件上传选择框
	public String accessorySelectUpdate(String foreignId,String reurnUrl,String entityId,String filesType,RedirectAttributes redirectAttributes,String filesdate,HttpServletResponse response,HttpServletRequest request, Model model) {
		if(filesType.equals("Project")){
			Project entity = projectService.get(entityId);
			entity.setFiles(filesdate);
			projectService.save(entity);
		}else if(filesType.equals("Contract")){
			Contract entity = contractService.get(entityId);
			entity.setFiles(filesdate);
			contractService.save(entity);
		}else if(filesType.equals("outContract")){
			Outcontract entity = outcontractService.get(entityId);
			entity.setFiles(filesdate);
			outcontractService.save(entity);
		}else if(filesType.equals("OutcontractBill")){//foreignId
			OutcontractBill  entity = outcontractBillService.get(entityId);
			entity.setFiles(filesdate);
			outcontractBillService.save(entity);
			reurnUrl = reurnUrl.replace("&amp;", "&");
		}else if(filesType.equals("outCheckContract")){
			OutcontractCheck entity = outcontractCheckService.get(entityId);
			entity.setFiles(filesdate);
			outcontractCheckService.save(entity);
			reurnUrl = reurnUrl.replace("&amp;", "&");
		}else if(filesType.equals("Invoice")){
			Invoice entity = invoiceService.get(entityId);
			entity.setFiles(filesdate);
			invoiceService.save(entity);
		}else if(filesType.equals("Invoice2")){
			Invoice entity = invoiceService.get(entityId);
			entity.setFiles2(filesdate);
			invoiceService.save(entity);
		}else if(filesType.equals("OutcontractInvoice")){
			OutcontractInvoice entity = null;
			if(entityId != null && !entityId.equals(""))
				 entity = outcontractInvoiceService.get(entityId);
			else{
				entity = new OutcontractInvoice();
				entity.setOutcontractId(foreignId);
			}
			entity.setFiles(filesdate);
			outcontractInvoiceService.save(entity);
		}else if(filesType.equals("outcontractPayRegister")){
			OutcontractPayRegister entity = null;
			if(entityId != null && !entityId.equals(""))
				entity = outcontractPayRegisterService.get(entityId);
			else{
				entity = new OutcontractPayRegister();
				entity.setOutcontractCheckId(foreignId);
			}
			entity.setFiles(filesdate);
			outcontractPayRegisterService.save(entity);
			reurnUrl = reurnUrl.replace("&amp;", "&");
		}  
	 
		addMessage(redirectAttributes, "修改附件成功");
		return "redirect:"+Global.getAdminPath()+reurnUrl;
	 
	}
	/**
	 * 回款单选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "returnregisterSelect")//通用外包商选择框
	public String returnregisterSelect(ReturnRegister returnRegister, HttpServletResponse response,HttpServletRequest request, Model model) {
		returnRegister.setStatus("2");//只显示可用的
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<ReturnRegister> page = registerService.findPage(new Page<ReturnRegister>(request, response),returnRegister); 	
		model.addAttribute("page", page);
		return "modules/pro/returnRegisterSelect";
	}
	
	
	
	/**
	 * 客户选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "customerUserSelect")//通用外包商选择框
	public String customerUserSelect(CustomerUser customeruser, HttpServletResponse response,HttpServletRequest request, Model model) {
		if( request.getParameter("projectid") != null &&  request.getParameter("projectid") != ""){
			String customerId = "-1";
			try{
			  customerId = projectService.get(request.getParameter("projectid")).getCustomer().getId();
			}catch(Exception e){
				customerId = "-1";
			}
			customeruser.setCustomerId(Integer.parseInt(customerId));
			model.addAttribute("projectid",  request.getParameter("projectid"));
		}
		
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		Page<CustomerUser> page = customerUserService.findPage(new Page<CustomerUser>(request, response),customeruser); 	
		model.addAttribute("page", page);
		return "modules/sys/customerUserSelect";
	}
	
	
	/**
	 * 合同验收、确认选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "contractConfirmSelect")//通用外包商选择框
	public String contractConfirmSelect(ContractConfirm contractConfirm, HttpServletResponse response,HttpServletRequest request, Model model) {
//		if( request.getParameter("projectid") != null &&  request.getParameter("projectid") != ""){
//			contractConfirm.setProjectId(Integer.parseInt(request.getParameter("projectid")));
//			model.addAttribute("projectid",  request.getParameter("projectid"));
//		}
		model.addAttribute("selecttype", request.getParameter("selecttype")); 	// 单选或复选类型		 
		List<ContractConfirm> contractConfirmList = contractConfirmService.getAllconfirm(contractConfirm); 	
		model.addAttribute("contractConfirm", contractConfirmList);
		return "modules/pro/contractConfirmSelect";
	}
}
