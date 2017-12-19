/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;
import com.thinkgem.jeesite.modules.bus.service.ContractInvoiceConfirmService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractConfirmService;
import com.thinkgem.jeesite.modules.pro.service.ContractPayService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 合同Controller
 * @author cdoublej
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/workConfirm")
public class WorkConfirmController extends BaseController {
	@Autowired
	private ContractInvoiceConfirmService contractInvoiceConfirmService;
	@Autowired 
	private ContractPayService contractPayService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractConfirmService confirmService;
	@Autowired
	private ProjectService projectService;
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
	
	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		List<String>  dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calender =  Calendar.getInstance();
		calender.setTime(date);
	//	dateList.add(0,sdf.format(date));	 
		for(int i = -1; i >= -10;i--){					
			calender.add(Calendar.MONTH,-1);
			if(i == -1){
				project.setTempleDate(sdf.parse(sdf.format(calender.getTime()) +"-01"));
			}
			dateList.add(0,sdf.format(calender.getTime()));
		
		}
		model.addAttribute("dateList", dateList);
		
		String userId = UserUtils.getUser().getId();
		if(userId.equals("1") || userId.equals("2")){
			//超级管理员，开发人员账号
		}else{
			//权限
			Integer employeeId = UserUtils.getUser().getEmployeeId();
		 
			project.setAuthorityEmployeeId(employeeId.toString());
		}
	
		Page<Project> page = projectService.findWorkerDetailPage(new Page<Project>(request, response), project); 
		List<Project> projectList = page.getList();
		for(int i = 0 ; i < projectList.size(); i++){
			Project templeProject = projectList.get(i);
			List<Map<String,String>> projectConfirmList = projectService.getProjectConfirmList(templeProject.getId());
			projectList.get(i).setProjectConfirmList(projectConfirmList);
		}
		
		page.setList(projectList);
		
//		Date date = new Date();
//		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.add(Calendar.MONTH,-1);
//		project.setTempleDate(s.parse(s.format(c.getTime()) +"-01"));
//		Page<Project> page = projectService.findWorkerDetailPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/pro/work_projectList";
	}
	
	
	
	
	
	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping("detailList")
	public String detailList(ContractConfirm contractConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		contractConfirm.setProjectId(projectId);
//		//权限
//		Integer employeeId = UserUtils.getUser().getEmployeeId();
//		String sysAreaId = employeeService.get(employeeId.toString()).getSysArea().getId();
//		contract.setBig_sysAreaId(sysAreaId);
		Page<ContractConfirm> page = confirmService.findAllProjectConfirmPage(new Page<ContractConfirm>(request, response), contractConfirm);	
		Project project = projectService.getProjectDetail(projectId);		
		model.addAttribute("project", project);
		
		model.addAttribute("page", page);
		return "modules/pro/workConfirmDetailList";
	}
	
	
	
	
	
	
	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(Contract contract, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String projectId =  contract.getProjectId().toString();
		Project project = projectService.getProjectDetail(projectId);
		
		model.addAttribute("project", project);
			
		model.addAttribute("projectId",projectId);
		List<String>  dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calender =  Calendar.getInstance();
		calender.setTime(date);
	//	dateList.add(0,sdf.format(date));	 
		for(int i = -1; i >= -10;i--){					
			calender.add(Calendar.MONTH,-1);
			dateList.add(0,sdf.format(calender.getTime()));
			if(i == -1){
				contract.setTempleDate(sdf.parse(sdf.format(calender.getTime())));
			}
		}
		Date startTime = sdf.parse(dateList.get(0));
//	 	contract.setStartTime(startTime);
//		contract.setEndTime((date));
		Page<Contract> page = contractService.findworkconfirmPage(new Page<Contract>(request, response), contract); 
		
		List<Contract> listContractList = page.getList(); //获取contractList
		
		page.setCount(listContractList.size());
		for(int j = 0 ; j < listContractList.size() ;j++){//遍历contractList
			List<ContractPay> contractPaylist = new ArrayList<ContractPay>();//装构造好的contractPay
			List<ContractConfirm>  contractConfirmList = new ArrayList<ContractConfirm>();//装构造好的contractConfirm
			Contract contractList = listContractList.get(j);
			ContractConfirm confirm = new ContractConfirm();
			confirm.setContractId(Integer.parseInt(contractList.getId()));
			List<ContractConfirm>  contractConfirmList2 = confirmService.findList(confirm);//获取contract_id 下的所有 ContractConfirm
			ContractPay contractPay2 = new ContractPay();
			contractPay2.setContractId(Integer.parseInt(contractList.getId()));
			List<ContractPay> contractPayList = contractPayService.findList(contractPay2); //获取contractPay
			calender.setTime(startTime);	//设置开始时间	总共10个月	
				for(int i = 1; i <= 10;i++){  //遍历10次
					if(contractList.getWorkconfirmid().equals("2")){//按月工作量确认
						//内部确认
						ContractPay contractPay =  new ContractPay();  
						for(int a = 0; a < contractPayList.size();a++){	//遍历contractPayList
						String s = sdf.format(contractPayList.get(a).getPayDate());//获取contractPay 约定付款时间
						String e = sdf.format(calender.getTime());
							if(s.equals(e))
								contractPay = contractPayList.get(a);
							else
								continue;
						}	
						contractPaylist.add(contractPay);
					}
			 				
						//外部确认
 						ContractConfirm contractConfirm = new ContractConfirm();
						for(int a = 0; a <contractConfirmList2.size(); a++){
							String s =  sdf.format(contractConfirmList2.get(a).getMonth());
							String e = sdf.format(calender.getTime());
							if(s.equals(e))
								contractConfirm = contractConfirmList2.get(a);
							else
								continue;
						}
						contractConfirmList.add(contractConfirm);
				 
					
					
					calender.add(Calendar.MONTH,1);
			}
				listContractList.get(j).setContractConfirmList(contractConfirmList);
				listContractList.get(j).setContractPayList(contractPaylist);
		}
		
		page.setList(listContractList);
		model.addAttribute("dateList", dateList);
	            
		model.addAttribute("page", page);
		return "modules/pro/workConfirmList";
	}

	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping(value = "form")
	public String form(Contract contract, Model model) {
		ContractPay contractPay = new ContractPay();
		List<ContractPay> contractPayList = new ArrayList<ContractPay>();
		if(contract.getId() != null && contract.getId() != ""){
			contractPay.setContractId(Integer.parseInt(contract.getId()));
			contractPayList = contractPayService.findList(contractPay);		
			model.addAttribute("contractPayList", contractPayList);
			model.addAttribute("workconfirmid", contract.getWorkconfirmid());
		}
		model.addAttribute("contract", contract);
		return "modules/pro/workConfirmForm";
	}

	@RequiresPermissions("pro:workConfirm:edit")
	@RequestMapping(value = "saveMonth")//按月确认
	public String saveMonth(ContractPay contractPay , HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws ParseException {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String projectid = request.getParameter("projectid");
		String contractid = request.getParameter("contractid");
		String workconfirmid = request.getParameter("workconfirmid");
		String contractpayid = request.getParameter("contractpayid");
		String confirmdate = request.getParameter("confirmdate");
//		ContractConfirm confirm = new ContractConfirm();
//		confirm.setCheckRemarks(contractConfirmcheckRemarks);
//		confirm.setPercent(contractConfirmpercent);
//		confirm.setMonth(sdf.parse(confirmdate));
//		confirm.setContractId(Integer.parseInt(contractid));
//		confirmService.deleteconfirm(confirm);
//		confirmService.save(confirm);
		contractPay.setInConfirmStatus("2");//已确认
		contractPayService.updateConfirm(contractPay);
	 
		addMessage(redirectAttributes, "内部确认成功");
		return "redirect:"+Global.getAdminPath()+"/pro/workConfirm/?projectId="+projectid; 
//		return "redirect:"+Global.getAdminPath()+"/pro/workConfirm/checkpannel?confirmdate="+confirmdate+"&contractid="+contractid+"&projectid="+projectid+"&workconfirmid="+workconfirmid+"&contractpayid="+contractpayid;
		//return "modules/pro/work_confirm/month_check";
	}
	
	@RequiresPermissions("pro:workConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(Contract contract, RedirectAttributes redirectAttributes) {
		contractService.delete(contract);
		addMessage(redirectAttributes, "删除合同成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contract/?repage";
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
		return "modules/pro/workConfirmSelect";
	}
	
	//内部确认
//	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping(value = "confirmpannel")
	public String confirmpannel(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String projectid = request.getParameter("projectid");
		String workconfirmid = request.getParameter("workconfirmid");
		String confirmdate = request.getParameter("confirmdate");
		String contractid = request.getParameter("contractid");
		String contractpayid = request.getParameter("contractpayid");
		
		
		Project  project = projectService.get(projectid);
		Contract contract1 = new Contract();
		contract1.setId(contractid);
		ContractConfirm confirm = new ContractConfirm();
		confirm.setMonth(sdf.parse(confirmdate));
		contract1.setContractConfirm(confirm);
		Contract contract = contractService.getconfirm(contract1);
		model.addAttribute("project", project);
		model.addAttribute("contract", contract);
		
		ContractPay contractPay = new ContractPay();
		contractPay.setPayDate(sdf.parse(confirmdate));
		contractPay.setContractId(Integer.parseInt(contractid));
		List<ContractPay> contractPayList = new ArrayList<ContractPay>();
		
		
		model.addAttribute("projectid", projectid);
		model.addAttribute("confirmdate", confirmdate);
		model.addAttribute("contractid", contractid);
		model.addAttribute("workconfirmid", workconfirmid);
		model.addAttribute("contractpayid", contractpayid);
		
		if(workconfirmid.equals("1")){//按次确认
			contractPayList = contractPayService.getconfirmdata(contractPay);
			
		}else if(workconfirmid.equals("2")){//按月确认
			ContractPay contractPay2 = contractPayService.get(contractPay);
			model.addAttribute("contractPay", contractPay2);
			
		}else if(workconfirmid.equals("3")){//按量确认
			model.addAttribute("contractPayList", contractPayList);
			
		} 
		return "modules/pro/work_confirm/month_confirm";
	}
	
	//外部确认
//	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping(value = "outconfirmpannel")
	public String outconfirmpannel(ContractConfirm contractConfirm, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String projectid = request.getParameter("projectid"); 
		String contractid = request.getParameter("contractid");
		String workconfirmid = request.getParameter("workconfirmid"); //工作量确认类型
		String contractpayid = request.getParameter("contractpayid"); //
		String confirmdate = request.getParameter("confirmdate"); // 点击的时间
		

		Project  project = projectService.get(projectid);  //获取项目信息
		Contract contract1 = new Contract();
		contract1.setId(contractid);
		//获取confirm
 
 		contractConfirm.setMonth(sdf.parse(confirmdate));
 		contractConfirm.setContractId(Integer.parseInt(contractid));
 		contractConfirm = confirmService.getconfirm(contractConfirm);
 		if(contractConfirm == null)
 			contractConfirm = new ContractConfirm();
 		model.addAttribute("contractConfirm", contractConfirm);
 		 
		Contract contract = contractService.getconfirm(contract1);
		model.addAttribute("project", project);
		model.addAttribute("contract", contract);
	
		ContractPay contractPay = new ContractPay();
 
		contractPay.setContractId(Integer.parseInt(contractid));
		List<ContractPay> contractPayList = new ArrayList<ContractPay>();
		
		model.addAttribute("confirmdate", confirmdate);
		model.addAttribute("contractid", contractid);
		model.addAttribute("projectid", projectid);
		model.addAttribute("workconfirmid", workconfirmid);
		model.addAttribute("contractpayid", contractpayid);
		model.addAttribute("contract", contract);
		
 
		 if(workconfirmid.equals("2")){//按月确认
			 Date date = new Date();
			 contractPay.setPayDate(sdf.parse(sdf.format(date)));		 
			  contractPayList = contractPayService.getconfirmdata(contractPay);
			  model.addAttribute("contractPayList", contractPayList);
			  
			  return "modules/pro/work_confirm/month_check";
		 }else  if(workconfirmid.equals("1")){//按次确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/pro/work_confirm/outconfirm";
		 }else  if(workconfirmid.equals("3")){//按量确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/pro/work_confirm/outconfirm3";
		 }else
			 return null;
	}
	
	//外部确认保存
	@RequiresPermissions("pro:workConfirm:edit")
	@RequestMapping(value = "saveOutConfirm")//按次确认
	public String saveOutConfirm(String invoiceIds, String  confirmpayids,ContractConfirm contractConfirm, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws ParseException {	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String projectid = request.getParameter("projectid");
		String contractid = request.getParameter("contractid");
		String workconfirmid = request.getParameter("workconfirmid");
		String contractpayid = request.getParameter("contractpayid");
		String confirmdate = request.getParameter("confirmdate");
		
		String  confirmpayids2="";
		if(contractConfirm.getId() != null && !contractConfirm.getId().equals("")){
			confirmpayids2 = confirmService.get(contractConfirm).getContractPayIds();
//			contractPayService.updateOutConfirmStatus(contractConfirmid2);
		}
		//更新付款信息状态。外部已确认或者未确认
		String [] confirmpayidsArray = confirmpayids.split(",");//奇数下标 contract id  偶数下标  outconfirmstatus 值。。是否外部确认
//		
//		
//		if(confirmpayidsArray.length < 2)
//			confirmpayids2=confirmService.get(contractConfirm).getContractPayIds();
		if(confirmpayidsArray.length >= 2){
			
			for(int i = 0;i < confirmpayidsArray.length;i+=2){			
				ContractPay contractPay = new ContractPay();
				contractPay.setId(confirmpayidsArray[i]);
				contractPay=  contractPayService.get(contractPay);
				if(confirmpayidsArray[i + 1].equals("true")){
					confirmpayids2 += confirmpayidsArray[i] +",";
					contractPay.setOutConfirmStatus("2");
				}
				else if(confirmpayidsArray[i + 1].equals("false")){
					confirmpayids2 = confirmpayids2.replaceAll(confirmpayidsArray[i], "");
					contractPay.setOutConfirmStatus("1");
				}
				contractPayService.save(contractPay);
			}
		}
		contractConfirm.setMonth(sdf.parse(confirmdate));
		if(!confirmpayids2.equals("")){		 
			contractConfirm.setContractPayIds(confirmpayids2);
			
		}
		contractConfirm.setContractId(Integer.parseInt(contractid));
		contractConfirm.setExamineStatus("1");
		
		if (contractConfirm.getIsNewRecord()){
			contractConfirm.preInsert();
			confirmService.insert(contractConfirm);
		}else{
			contractConfirm.preUpdate();
			confirmService.update(contractConfirm);
		}
		 //向contractfirm_invoice表中插入关联
		ContractInvoiceConfirm contractInvoiceConfirm = null;	
		if(contractConfirm.getIsExistInvoice().equals("1")){
			if(!invoiceIds.equals("")&&invoiceIds != null){
				 invoiceIds = ","+invoiceIds;		 //避免查询的时候出现 比如 6，%  匹配到 16，% 类似这种情况
			}
						
			if(contractConfirm.getContractInvoiceConfirm().getId()==null || contractConfirm.getContractInvoiceConfirm().getId().equals("")){
				contractInvoiceConfirm= new ContractInvoiceConfirm();
				
			}
			else		
				contractInvoiceConfirm = contractConfirm.getContractInvoiceConfirm();
			invoiceIds = invoiceIds + ",";
			contractInvoiceConfirm.setInvoiceId(invoiceIds);
			contractInvoiceConfirm.setCreateDate(new Date());
			contractInvoiceConfirm.setContractConfirmId(","+contractConfirm.getId()+",");
			contractInvoiceConfirmService.save(contractInvoiceConfirm);
		}else if(contractConfirm.getIsExistInvoice().equals("2")){
			contractInvoiceConfirm = contractConfirm.getContractInvoiceConfirm();
			contractInvoiceConfirmService.delete(contractInvoiceConfirm);
			
		}
		addMessage(redirectAttributes, "外部确认成功");
		return "redirect:"+Global.getAdminPath()+"/pro/workConfirm/?projectId="+projectid; 
 
	}
	
 

	//所有记录
	@RequiresPermissions("pro:workConfirm:view")
	@RequestMapping(value = "allconfirmpannel")
	public String allconfirmpannel(ContractConfirm contractConfirm, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String projectid = request.getParameter("projectid"); 
		String contractid = request.getParameter("contractid");
		String workconfirmid = request.getParameter("workconfirmid"); //工作量确认类型
	 

		Project  project = projectService.get(projectid);  //获取项目信息
		Contract contract1 = new Contract();
		contract1.setId(contractid);
		//获取confirm

 		contractConfirm.setContractId(Integer.parseInt(contractid));
 		List<ContractConfirm> contractConfirmlist = (ArrayList<ContractConfirm>)confirmService.getAllconfirm(contractConfirm);
 	 
 		model.addAttribute("contractConfirm", contractConfirmlist);
 		 
		Contract contract = contractService.getconfirm(contract1);
		model.addAttribute("project", project);
		model.addAttribute("contract", contract);
	
		ContractPay contractPay = new ContractPay();
 
		contractPay.setContractId(Integer.parseInt(contractid));
		List<ContractPay> contractPayList = new ArrayList<ContractPay>();
		
		model.addAttribute("contractid", contractid);
		model.addAttribute("projectid", projectid);
		model.addAttribute("workconfirmid", workconfirmid);
		model.addAttribute("contract", contract);
		
 
		 if(workconfirmid.equals("2")){//按月确认
			 Date date = new Date();
	//		 contractPay.setPayDate(sdf.parse(sdf.format(date)));		 
			  contractPayList = contractPayService.getconfirmdata(contractPay);
			  model.addAttribute("contractPayList", contractPayList);
		 
			 return "modules/pro/work_confirm/detail_month_check";
		 }else  if(workconfirmid.equals("1")){//按次确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/pro/work_confirm/detail_time_check";
			 
		 }else  if(workconfirmid.equals("3")){//按量确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/pro/work_confirm/detail_count_check";
		 }else
			 return null;
	}
}