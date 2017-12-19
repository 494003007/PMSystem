/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

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
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractConfirmService;
import com.thinkgem.jeesite.modules.pro.service.ContractPayService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

/**
 * 合同Controller
 * @author cdoublej
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/workConfirm")
public class ExamineWorkConfirmController extends BaseController {
 
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
	
	
	@RequiresPermissions("gmo:workConfirm:view")
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
		return "modules/gmo/workConfirmDetailList";
	}
	
	
	
	
	
	@RequiresPermissions("gmo:workConfirm:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String>  dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar calender =  Calendar.getInstance();
		calender.setTime(date);
	//	dateList.add(0,sdf.format(date));	 
		for(int i = -1; i >= -10;i--){					
			calender.add(Calendar.MONTH,-1);
			dateList.add(0,sdf.format(calender.getTime()));
		
		}
		model.addAttribute("dateList", dateList);
		
		Page<Project> page = projectService.findExamineDetailPage(new Page<Project>(request, response), project); 
		
		List<Project> projectList = page.getList();
		for(int i = 0 ; i < projectList.size(); i++){
			Project templeProject = projectList.get(i);
			List<Map<String,String>> projectConfirmList = projectService.getProjectConfirmList(templeProject.getId());
			projectList.get(i).setProjectConfirmList(projectConfirmList);
		}
		
		page.setList(projectList);
		model.addAttribute("page", page);
		
 	
		return "modules/gmo/work_projectList";
	}
	@RequiresPermissions("gmo:workConfirm:view")
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
		 
		}
		Date startTime = sdf.parse(dateList.get(0));
//	 	contract.setStartTime(startTime);
//		contract.setEndTime((date));
		Page<Contract> page = contractService.findexamineworkconfirmPage(new Page<Contract>(request, response), contract); 
		
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
		return "modules/gmo/workConfirmList";
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
		return "modules/gmo/workConfirmSelect";
	}
	 
	
	//外部确认
	@RequiresPermissions("gmo:workConfirm:view")
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
		 
			 return "modules/gmo/work_confirm/month_check";
		 }else  if(workconfirmid.equals("1")){//按次确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/gmo/work_confirm/outconfirm";
		 }else  if(workconfirmid.equals("3")){//按量确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/gmo/work_confirm/outconfirm3";
		 }else
			 return null;
	}
	
	//外部确认保存
	@RequiresPermissions("gmo:workConfirm:edit")
	@RequestMapping(value = "saveOutConfirm")//按次确认
	public String saveOutConfirm(String examineRemarks,String contractConfirmId,HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws ParseException {	
		ContractConfirm contractConfirm = new ContractConfirm();
		String projectid = request.getParameter("projectid");
	    String examineStatus = request.getParameter("type");
		contractConfirm.setId(contractConfirmId);
		contractConfirm.setExamineRemarks(examineRemarks);
		contractConfirm.setExamineStatus(examineStatus);
		confirmService.updateExamine(contractConfirm);
		addMessage(redirectAttributes, "外部确认审核成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/workConfirm/?projectId="+projectid; 
 
	}
	
 

	//所有记录
	@RequiresPermissions("gmo:workConfirm:view")
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
			 contractPay.setPayDate(sdf.parse(sdf.format(date)));		 
			  contractPayList = contractPayService.getconfirmdata(contractPay);
			  model.addAttribute("contractPayList", contractPayList);
		 
			 return "modules/gmo/work_confirm/detail_month_check";
		 }else  if(workconfirmid.equals("1")){//按次确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/gmo/work_confirm/detail_time_check";
			 
		 }else  if(workconfirmid.equals("3")){//按量确认{			 
		     contractPayList = contractPayService.getconfirmdata(contractPay);
		     model.addAttribute("contractPayList", contractPayList);
			 return "modules/gmo/work_confirm/detail_count_check";
		 }else
			 return null;
	}
}