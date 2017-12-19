/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.out.entity.*;
import com.thinkgem.jeesite.modules.out.service.*;

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
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * outcontract_billController
 * @author czy
 * @version 2016-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractPayRegister")
public class OutcontractPayRegisterController extends BaseController {
	@Autowired
	private OutcontractCheckService outcontractCheckService;
	@Autowired
	private OutcontractPayRegisterService outcontractPayRegisterService;

	@Autowired
	private OutcontractService outcontractService;

	@Autowired
	private OutcontractPayService outcontractPayService;

	@Autowired
	private OutsourcerService outsourcerService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	OutcontractBillService outcontractBillService;
	@ModelAttribute
	public OutcontractPayRegister get(@RequestParam(required = false) String id) {
		OutcontractPayRegister entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = outcontractPayRegisterService.get(id);
		}
		if (entity == null) {
			entity = new OutcontractPayRegister();
		}
		return entity;
	}
	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		 
		Page<Project> page = projectService.findOutDetailPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/out/payProjectDetailList";
	}
	
	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);

		model.addAttribute("project", project);
		outcontract.setProjectId(Integer.parseInt(projectId));
		Page<Outcontract> page = outcontractService.findCheckListPage(new Page<Outcontract>(request, response), outcontract);
		model.addAttribute("page", page);

		return "modules/out/outcontractPayRegisterList";
	}

	
	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping("payList")
	public String payList(OutcontractPayRegister outcontractPayRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		String outcontractId = request.getParameter("outcontractId");

		Outcontract outcontract = outcontractService.get(outcontractId);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("project", project);

		outcontractPayRegister.setOutcontractId(outcontractId);
		Page<OutcontractPayRegister> page = outcontractPayRegisterService.findBillPayPage(new Page<OutcontractPayRegister>(request, response),outcontractPayRegister);
		model.addAttribute("page", page);
		return "modules/out/outcontractPayDetailList";
	}
	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping("outcontractPayRegisterSelect")
	public String outcontractPayRegisterSelect(HttpServletRequest request, HttpServletResponse response, Model model) {
		String selecttype = request.getParameter("selecttype");
		String outcontractId = request.getParameter("outcontractId");
		OutcontractCheck outcontractCheck = new OutcontractCheck();
		outcontractCheck.setOutcontractId(Integer.parseInt(outcontractId));
		Page<OutcontractCheck> page = outcontractCheckService.findSelectPage(new Page<OutcontractCheck>(request, response), outcontractCheck);
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/out/outcontractCheckSelect";
	}
	@RequestMapping(value ="selectOutcontractCheck",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectContract(HttpServletRequest request, HttpServletResponse response, Model model) {		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat d = new DecimalFormat("###,##0.00");
		Map<String,String> map = new HashMap<String,String>();
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		String id[] = outcontractCheckId.split(",");
		String outcontractCode = "";
		double predictPay = 0.0;
		double totalcheckamount = 0.0;
		String payDate = "";
		String startTime = "";
		String endTime = "";
		String signTime = "";
		String checkContent = "";
		double surplusamount = 0.0;
		double totalpayamount = 0.0;
		double checkAmount = 0.0;
		double totalbillamount = 0.0;
		String remarks = "";
		double billPayAmount = 0.0;
		String outcontractBillId = "";
		String checkDate = "";
		String outcontractContent = "";
		for(int i = 0;i < id.length;i++){
			OutcontractCheck outcontractCheck = outcontractCheckService.getAjaxDate(id[i]);
			outcontractCode += outcontractCheck.getOutcontractCode();
			predictPay += Double.parseDouble(outcontractCheck.getOutcontractPredictPay());
			totalcheckamount += Double.parseDouble(outcontractCheck.getTotalcheckamount());
			try{
				payDate = s.format(outcontractCheck.getOutcontractPayDate()) + ",";
			}catch(Exception e){
				payDate = "--,";
			}
			try{
				startTime += s.format(outcontractCheck.getStartTime());
			}catch(Exception e){
				startTime += "--,";
			}
			try{
				endTime += s.format(outcontractCheck.getEndTime());
			}catch(Exception e){
				endTime += "--,";
			}
			try{
				signTime += s.format(outcontractCheck.getSignTime());
			}catch(Exception e){
				signTime += "--,";
			}
			try{
				checkDate += s.format(outcontractCheck.getCheckDate());
			}catch(Exception e){
				checkDate += "--,";
			}
			totalcheckamount += Double.parseDouble(outcontractCheck.getTotalcheckamount());
			outcontractContent += outcontractCheck.getOutcontractCode() + ",";
			checkContent += outcontractCheck.getCheckContent() + ",";
			surplusamount += Double.parseDouble(outcontractCheck.getSurplusamount());
			totalpayamount += Double.parseDouble(outcontractCheck.getTotalpayamount());
			totalbillamount += Double.parseDouble(outcontractCheck.getTotalbillamount());
			checkAmount += outcontractCheck.getCheckAmount();
			remarks += outcontractCheck.getRemarks() + ",";
			billPayAmount += Double.parseDouble(outcontractCheck.getBillPayAmount());
			outcontractBillId += outcontractCheck.getOutcontractBillId() + ",";
			outcontractCheckId += outcontractCheck.getId() + ",";
		}
		map.put("outcontractCode",outcontractCode);
		map.put("predictPay", d.format(predictPay));
		map.put("totalcheckamount",d.format(totalcheckamount));
		map.put("payDate",payDate);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("signTime",signTime);
		map.put("checkDate",checkDate);
		map.put("outcontractContent",outcontractContent);
		map.put("checkContent",checkContent);
		map.put("surplusamount", d.format(surplusamount));
		map.put("totalcheckamount", d.format(totalcheckamount));
		map.put("totalpayamount", d.format(totalpayamount));
		map.put("totalbillamount", d.format(totalbillamount));
		map.put("checkAmount", d.format(checkAmount));
		map.put("remarks",remarks);
		map.put("billPayAmount",d.format(billPayAmount));
		map.put("outcontractBillId",outcontractBillId);
		map.put("outcontractCheckId",outcontractCheckId);
//		map.put("checkContent",DictUtils.getDictLabels(contract.getInvoiceRevenue(), "tax_percent", ""));
		//map.put("contract_customerId", contract.getCustomer().getName());

		return map;
	}
	
	@RequestMapping(value="examineList")
	public String examineList(OutcontractPayRegister outcontractPayRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OutcontractPayRegister> page = outcontractPayRegisterService.findExaminePage(new Page<OutcontractPayRegister>(request, response), outcontractPayRegister); 
		model.addAttribute("page", page);
		return "modules/out/outcontractExaminePayRegisterList";
	}
	
	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping(value = "info")
	public String info(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		
		 Map<String,String> outcontractMap = outcontractPayRegisterService.getInfo(outcontract);
		 List<OutcontractPayRegister> subInvoice_list = outcontractPayRegisterService.findListBysubIds(outcontract.getId());
		 model.addAttribute("outcontractMap", outcontractMap);
		 model.addAttribute("subInvoice_list", subInvoice_list);
		 return "modules/out/outcontractPayRegisterInfo";
	}
	@RequiresPermissions("out:outcontractPayRegister:edit")
	@RequestMapping(value = "form")
	public String form(OutcontractPayRegister outcontractPayRegister, Model model, HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		String outcontractCheckId;
		String outcontractPayRegisterId;
		outcontractPayRegisterId = outcontractPayRegister.getId();
		outcontractCheckId = outcontractPayRegister.getOutcontractCheckId();
		model.addAttribute("outcontractPayRegister", outcontractPayRegister);
		model.addAttribute("outcontractPayRegisterId",outcontractPayRegisterId);
		model.addAttribute("outcontractCheckId",outcontractCheckId);
		model.addAttribute("outcontractId", outcontractId);
		model.addAttribute("projectId", projectId);
		return "modules/out/outcontractPayRegisterForm";
	}

	@RequiresPermissions("out:outcontractPayRegister:view")
	@RequestMapping(value = "examineForm")
	public String examineForm( Model model, HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		OutcontractPayRegister outcontractPayRegister = null;
		Outcontract outcontract = outcontractService.get(outcontractId);
		String outcontractPayRegisterId = request.getParameter("outcontractPayRegisterId");
//		if(outcontractPayRegisterId != null && !outcontractPayRegisterId.equals(""))
			outcontractPayRegister = outcontractPayRegisterService.get(outcontractId);
//		else{
//			outcontractPayRegister = new OutcontractPayRegister();
//			outcontractPayRegister.setOutcontractId(outcontractId);
//		}
//		
	
		model.addAttribute("outcontract", outcontract);
 
		model.addAttribute("outcontractInvoice", outcontractPayRegister);
		return "modules/out/outcontractExaminePayRegisterForm";
	}


	
	
	@RequiresPermissions("out:outcontractPayRegister:edit")
	@RequestMapping(value = "save")
	public String save(OutcontractPayRegister outcontractPayRegister,String paytype, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		System.out.println(id);
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		if(id == null || id == ""){
			String outcontractCheckId = request.getParameter("outcontractCheckId");
			String outcontractBillId = request.getParameter("outcontractBillId");
			String payAmount = request.getParameter("payAmount");
			String files = request.getParameter("files");
			String remarks = request.getParameter("remarks");
			String printName = request.getParameter("printName");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			try{
				d=df.parse(request.getParameter("payDate"));
			}catch (Exception e){

			}
			String checkId[] = outcontractCheckId.split(",");
			String billId[] = outcontractBillId.split(",");
			OutcontractPayRegister outcontractPayRegister1[] = new OutcontractPayRegister[checkId.length];
			for(int i = 0;i < checkId.length;i++){
				OutcontractBill outcontractBill = outcontractBillService.get(billId[i]);
				outcontractPayRegister1[i] = new OutcontractPayRegister();
				outcontractPayRegister1[i].setOutcontractCheckId(checkId[i]);
				outcontractPayRegister1[i].setOutcontractBillId(billId[i]);
				outcontractPayRegister1[i].setPayAmount(Double.parseDouble(outcontractBill.getBillAmount()));
				outcontractPayRegister1[i].setPayDate(d);
				outcontractPayRegister1[i].setFiles(files);
				outcontractPayRegister1[i].setRemarks(remarks);
				outcontractPayRegister1[i].setPrintName(printName);
				outcontractPayRegister1[i].setPayType(2);
				outcontractPayRegister1[i].setOutcontractId(outcontractId);
				outcontractPayRegisterService.save(outcontractPayRegister1[i]);
			}
		}
		else{
			outcontractPayRegister = outcontractPayRegisterService.get(id);
			outcontractPayRegister.setOutcontractId(outcontractId);
			outcontractPayRegisterService.save(outcontractPayRegister);
		}
		/*if(paytype.equals("2")){//计提付款
			String outcontractBillId = outcontractPayRegister.getOutcontractBillId();
			Double payAmount = outcontractPayRegister.getPayAmount();
			outcontractBillService.updateBillAmount(outcontractBillId,payAmount);
		}*/
		addMessage(redirectAttributes, "外包合同付款登记保存成功");	 
		return "redirect:"+Global.getAdminPath()+"/out/outcontractPayRegister/payList?outcontractId="+outcontractId+"&projectId="+projectId;
	 
	}
	
	@RequiresPermissions("out:outcontractPayRegister:edit")
	@RequestMapping(value = "examineSave")
	public String examineSave(OutcontractPayRegister outcontractPayRegister, Model model, RedirectAttributes redirectAttributes) {
		outcontractPayRegister.setPayStatus("3");
		outcontractPayRegisterService.updateRegister(outcontractPayRegister);
		addMessage(redirectAttributes, "付款登记成功");
	 
		return "redirect:"+Global.getAdminPath()+"/out/outcontractPayRegister/examineList?repage";
	 
	}
	
	
	
	@RequiresPermissions("out:outcontractPayRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractPayRegister outcontractPayRegister, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		String type = request.getParameter("type");
		System.out.println(type);
		try{
			outcontractPayRegisterService.delete(outcontractPayRegister);
		}catch(Exception e){
			addMessage(redirectAttributes, "付款登记已被关联，不能删除该记录");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractPayRegister/payList?outcontractId="+outcontractId+"&projectId="+projectId;

		}
		if(type.equals("2")){
			addMessage(redirectAttributes, "外包合同付款登记删除成功");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractPayRegister/payList?outcontractId="+outcontractId+"&projectId="+projectId;
		}
		else{
			addMessage(redirectAttributes, "外包合同付款登记删除成功");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
		}
	}

}