/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractCheck;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPayRegister;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.service.OutcontractCheckService;
import com.thinkgem.jeesite.modules.out.service.OutcontractPayService;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import com.thinkgem.jeesite.modules.out.service.OutsourcerService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 外包验收Controller
 * 
 * @author LKY
 * @version 2016-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractCheck")
public class OutcontractCheckController extends BaseController {
	@Autowired
	private OutcontractPayService outcontractPayService;
	@Autowired
	private OutcontractCheckService outcontractCheckService;
	@Autowired
	private OutcontractService outcontractService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private OutsourcerService outsourcerService;

	@ModelAttribute
	public OutcontractCheck get(@RequestParam(required = false) String id) {
		OutcontractCheck entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = outcontractCheckService.get(id);
		}
		if (entity == null) {
			entity = new OutcontractCheck();
		}
		return entity;
	}

	@RequiresPermissions("out:outcontractCheck:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {

		Page<Project> page = projectService.findOutDetailPage(
				new Page<Project>(request, response), project);
		model.addAttribute("page", page);
		return "modules/out/checkProjectDetailList";
	}

	@RequiresPermissions("out:outcontractCheck:view")
	@RequestMapping(value = { "list", "" })
	public String list(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);

		model.addAttribute("project", project);

		outcontract.setProjectId(Integer.parseInt(projectId));
		Page<Outcontract> page = outcontractService.findCheckListPage(new Page<Outcontract>(request, response), outcontract);
		model.addAttribute("page", page);
		return "modules/out/outcontractCheckList";
	}

	@RequiresPermissions("out:outcontractCheck:view")
	@RequestMapping("checkList")
	public String checkList(OutcontractCheck outcontractCheck,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		String outcontractId = request.getParameter("outcontractId");
		Outcontract outcontract = outcontractService.get(outcontractId);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("project", project);

		outcontractCheck.setOutcontractId(Integer.parseInt(outcontractId));
		Page<OutcontractCheck> page = outcontractCheckService.findPage(new Page<OutcontractCheck>(request, response),outcontractCheck);
		model.addAttribute("page", page);
		return "modules/out/outcontractCheckDetailList";
	}

	@RequiresPermissions("out:outcontractCheck:view")
	@RequestMapping(value = "form")
	public String form(OutcontractCheck outcontractCheck, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		model.addAttribute("outcontractCheck", outcontractCheck);
		model.addAttribute("outcontractId", outcontractId);
		model.addAttribute("projectId", projectId);
		return "modules/out/outcontractCheckForm";
	}

	@RequiresPermissions("out:outcontractCheck:view")
	@RequestMapping("outcontractPaySelect")
	public String outcontractPaySelect(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String selecttype = request.getParameter("selecttype");
		String outcontractId = request.getParameter("outcontractId");
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOutcontractId(Integer.parseInt(outcontractId));
		Page<OutcontractPay> page = outcontractPayService.findSelectPage(
				new Page<OutcontractPay>(request, response), outcontractPay);
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/out/outcontractPaySelect";
	}
	@RequestMapping(value ="selectOutcontractPay",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectContract(HttpServletRequest request, HttpServletResponse response, Model model) {		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat d = new DecimalFormat("###,##0.00");
		Map<String,String> map = new HashMap<String,String>();
		String outcontractPayId = request.getParameter("outcontractPayId");
		String id[] = outcontractPayId.split(",");
		String outcontractCode = "";
		String predictPay = "";
		String totalcheckamount = "";
		String payDate = "";
		String startTime = "";
		String endTime = "";
		String signTime = "";
		String checkContent = "";
		Double surplusamount = 0.0;
		for(int i = 0;i < id.length;i++){
			OutcontractPay outcontractPay = outcontractPayService.getAjaxDate(id[i]);
			outcontractCode += outcontractPay.getOutcontractCode() + ",";
			predictPay += d.format(outcontractPay.getPredictPay()) + ",";
			totalcheckamount += d.format(Double.parseDouble(outcontractPay.getTotalcheckamount())) + ",";
			try{
				payDate += s.format(outcontractPay.getPayDate()) + ",";
			}catch(Exception e){
				payDate += "--,";
			}
			try{
				startTime += s.format(outcontractPay.getStartTime()) + ",";
			}catch (Exception e){
				startTime += "--,";
			}
			try{
				endTime += s.format(outcontractPay.getEndTime()) + ",";
			}catch (Exception e){
				endTime += "--,";
			}
			try{
				signTime += s.format(outcontractPay.getSignTime()) + ",";
			}catch (Exception e){
				signTime += "--,";
			}
			checkContent += outcontractPay.getCheckContent() + ",";
			surplusamount += Double.parseDouble(outcontractPay.getSurplusamount());
		}
		map.put("outcontractCode",outcontractCode);
		map.put("predictPay", predictPay);
		map.put("totalcheckamount",totalcheckamount);
		map.put("payDate",payDate);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("signTime",signTime);
		map.put("checkContent",checkContent);
		map.put("surplusamount",surplusamount+"");
//		map.put("checkContent",DictUtils.getDictLabels(contract.getInvoiceRevenue(), "tax_percent", ""));
		//map.put("contract_customerId", contract.getCustomer().getName());
		return map;
	}
	
	@RequestMapping(value = "form2")
	public String form2(OutcontractCheck outcontractCheck, Model model,
			HttpServletRequest request) {

		System.out.println("success");
		String outcontractId = request.getParameter("id1");
		System.out.println("outcontract=" + outcontractId);

		OutcontractCheck a = new OutcontractCheck();
		a.setOutcontractId(Integer.parseInt(outcontractId));
		List<OutcontractCheck> list = outcontractCheckService.findList(a);
		System.out.println("changdu wei " + list.size());
		OutcontractCheck a2 = null;
		if (list.size() != 0) {
			a2 = (OutcontractCheck) list.get(0);
		} else {
			a2 = new OutcontractCheck();
		}
		model.addAttribute("outcontractCheck", a2);
		model.addAttribute("a", a);
		Outcontract outcontract1 = outcontractService.get(outcontractId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = formatter.format(outcontract1.getStartTime());
		String endTime = formatter.format(outcontract1.getEndTime());

		model.addAttribute("b", outcontractService.get(outcontractId));
		model.addAttribute("start", startTime);
		model.addAttribute("end", endTime);
		return "modules/out/checkexamineform";

	}



	@RequestMapping(value = "form1")
	public String form1(OutcontractCheck outcontractCheck, Model model,
			HttpServletRequest request) {

		System.out.println("success");
		String outcontractId = request.getParameter("id1");
		System.out.println("outcontract=" + outcontractId);

		OutcontractCheck a = new OutcontractCheck();
		a.setOutcontractId(Integer.parseInt(outcontractId));
		List<OutcontractCheck> list = outcontractCheckService.findList(a);
		System.out.println("changdu wei " + list.size());
		OutcontractCheck a2 = null;
		if (list.size() != 0) {
			a2 = (OutcontractCheck) list.get(0);
		} else {
			a2 = new OutcontractCheck();
		}
		model.addAttribute("outcontractCheck", a2);
		model.addAttribute("a", a);
		Outcontract outcontract1 = outcontractService.get(outcontractId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = formatter.format(outcontract1.getStartTime());
		String endTime = formatter.format(outcontract1.getEndTime());

		model.addAttribute("b", outcontractService.get(outcontractId));
		model.addAttribute("start", startTime);
		model.addAttribute("end", endTime);
		return "modules/out/outcontractCheckForm";

	}
	
	@RequiresPermissions("out:outcontractCheck:edit")
	@RequestMapping(value = "saveCheck")
	public String saveCheck( Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		String outcontractPayId = request.getParameter("outcontractPayId");
		String checkAmount = request.getParameter("checkAmount");
		String checkRemarks = request.getParameter("checkRemarks");
		String files = request.getParameter("files");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try{
			d=df.parse(request.getParameter("checkDate"));
		}catch (Exception e){

		}
		String id[] = outcontractPayId.split(",");
		OutcontractCheck outcontractCheck[] = new OutcontractCheck[id.length];
		for(int i = 0;i < id.length;i++){
			OutcontractPay outcontractPay = outcontractPayService.get(id[i]);
			double predictPay = outcontractPay.getPredictPay();
			outcontractCheck[i] = new OutcontractCheck();
			outcontractCheck[i].setOutcontractId(Integer.parseInt(outcontractId));
			outcontractCheck[i].setCheckAmount(predictPay);
			outcontractCheck[i].setCheckRemarks(checkRemarks);
			outcontractCheck[i].setCheckDate(d);
			outcontractCheck[i].setOutcontractPayId(id[i]);
			outcontractCheck[i].setFiles(files);
			outcontractCheckService.save(outcontractCheck[i]);
		}
		addMessage(redirectAttributes, "外包合同验收保存成功");	 
		return "redirect:"+Global.getAdminPath()+"/out/outcontractCheck/checkList?outcontractId="+outcontractId+"&projectId="+projectId;
	 
	}


	
	
	@RequiresPermissions("out:outcontractCheck:edit")
	@RequestMapping(value = "save")
	public String save(OutcontractCheck outcontractCheck, Model model,
			RedirectAttributes redirectAttributes) {

		outcontractCheckService.save(outcontractCheck);
		Outcontract outcontract = outcontractService.get(outcontractCheck
				.getOutcontractId() + "");
		outcontract.setCheckExamineStatus("2");
		outcontractService.updatestatus1(outcontract);
		Outcontract outcontract1 = outcontractService.get(""
				+ outcontractCheck.getOutcontractId());
		String parentid = "";
		if (outcontract1.getParentId().equals("0")) {
			parentid = outcontract1.getId();
		} else {
			parentid = outcontract1.getParentId();
		}
		Outcontract outcontract2 = outcontractService.get(parentid);
		Outcontract aa = new Outcontract();
		aa.setId(outcontract2.getId());
		DecimalFormat df = new DecimalFormat("#,##0.00");
		List<Outcontract> list = outcontractService.find111List(aa);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCheckamounts(
					outcontractCheckService
							.getnumber(list.get(i).getId() + ","));
			list.get(i).setAmountcopy(df.format(list.get(i).getAmount()));
			list.get(i).setCheckamountcopy(
					df.format(list.get(i).getCheckamounts()));
			if (list.get(i).getProjectId() != null
					&& list.get(i).getProjectId() != 0) {

				list.get(i).setProjectName(
						projectService.get(list.get(i).getProjectId() + "")
								.getName());
			}

			if (list.get(i).getOutcontractTypeId() != null
					&& list.get(i).getOutcontractTypeId() != 0) {
				if (list.get(i).getOutcontractTypeId() == 1) {
					list.get(i).setOutcontractTypeName("服务合同");
				} else if (list.get(i).getOutcontractTypeId() == 2) {
					list.get(i).setOutcontractTypeName("采购合同");
				} else {
					list.get(i).setOutcontractTypeName("分包合同");
				}
			}

			if (list.get(i).getOutsourcerId() != null
					&& list.get(i).getOutsourcerId() != 0) {

				list.get(i).setOutsourcerName(
						outsourcerService.get(
								list.get(i).getOutsourcerId() + "")
								.getCompanyName());
			}

			if (list.get(i).getCheckExamineStatus().equals("2")) {
				list.get(i).setCheckStatus("验收未审核");
			} else if (list.get(i).getCheckExamineStatus().equals("3")) {
				list.get(i).setCheckStatus("审核通过");
			} else if (list.get(i).getCheckExamineStatus().equals("4")) {
				list.get(i).setCheckStatus("审核不通过");
			} else {
				list.get(i).setCheckStatus("未验收");
			}

		}
		model.addAttribute("list", list);
		Outsourcer outsourcer = new Outsourcer();
		model.addAttribute("outsourcersList",
				outsourcerService.findList(outsourcer));
		model.addAttribute("projectlist",
				projectService.findList(new Project()));
		model.addAttribute("outcontract", new Outcontract());
		model.addAttribute("message", "保存成功");
		return "modules/out/outcontractListAccessory2";

	}

	@RequestMapping(value = "save1")
	public String save1(OutcontractCheck outcontractCheck, Model model,
			RedirectAttributes redirectAttributes) {

		outcontractCheckService.save(outcontractCheck);
		Outcontract outcontract = outcontractService.get(outcontractCheck
				.getOutcontractId() + "");
		if (outcontractCheck.getCheckStatus().equals("2")) {

			outcontract.setCheckExamineStatus("3");
		} else {
			outcontract.setCheckExamineStatus("4");
		}
		outcontractService.updatestatus1(outcontract);
		Outcontract outcontract1 = outcontractService.get(""
				+ outcontractCheck.getOutcontractId());
		String parentid = "";
		if (outcontract1.getParentId().equals("0")) {
			parentid = outcontract1.getId();
		} else {
			parentid = outcontract1.getParentId();
		}
		Outcontract outcontract2 = outcontractService.get(parentid);
		Outcontract aa = new Outcontract();
		aa.setId(outcontract2.getId());
		DecimalFormat df = new DecimalFormat("#,##0.00");
		List<Outcontract> list = outcontractService.find111List(aa);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCheckamounts(
					outcontractCheckService
							.getnumber(list.get(i).getId() + ","));
			list.get(i).setAmountcopy(df.format(list.get(i).getAmount()));
			list.get(i).setCheckamountcopy(
					df.format(list.get(i).getCheckamounts()));
			if (list.get(i).getProjectId() != null
					&& list.get(i).getProjectId() != 0) {

				list.get(i).setProjectName(
						projectService.get(list.get(i).getProjectId() + "")
								.getName());
			}

			if (list.get(i).getOutcontractTypeId() != null
					&& list.get(i).getOutcontractTypeId() != 0) {
				if (list.get(i).getOutcontractTypeId() == 1) {
					list.get(i).setOutcontractTypeName("服务合同");
				} else if (list.get(i).getOutcontractTypeId() == 2) {
					list.get(i).setOutcontractTypeName("采购合同");
				} else {
					list.get(i).setOutcontractTypeName("分包合同");
				}
			}

			if (list.get(i).getOutsourcerId() != null
					&& list.get(i).getOutsourcerId() != 0) {

				list.get(i).setOutsourcerName(
						outsourcerService.get(
								list.get(i).getOutsourcerId() + "")
								.getCompanyName());
			}

			if (list.get(i).getCheckExamineStatus().equals("2")) {
				list.get(i).setCheckStatus("验收未审核");
			} else if (list.get(i).getCheckExamineStatus().equals("3")) {
				list.get(i).setCheckStatus("审核通过");
			} else if (list.get(i).getCheckExamineStatus().equals("4")) {
				list.get(i).setCheckStatus("审核不通过");
			} else {
				list.get(i).setCheckStatus("未验收");
			}

		}
		model.addAttribute("list", list);
		Outsourcer outsourcer = new Outsourcer();
		model.addAttribute("outsourcersList",
				outsourcerService.findList(outsourcer));
		model.addAttribute("projectlist",
				projectService.findList(new Project()));
		model.addAttribute("outcontract", new Outcontract());
		model.addAttribute("message", "审核成功");
		return "modules/out/examinecheck1";

	}

	@RequiresPermissions("out:outcontractCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractCheck outcontractCheck,RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		try{
			outcontractCheckService.delete(outcontractCheck);
		}catch(Exception e){
			addMessage(redirectAttributes, "该验收记录已被关联，不能删除该记录");
			return "redirect:" + Global.getAdminPath() + "/out/outcontractCheck/checkList?outcontractId="+outcontractId+"&projectId="+projectId;
		}
		addMessage(redirectAttributes, "删除验收信息成功");
		return "redirect:" + Global.getAdminPath() + "/out/outcontractCheck/checkList?outcontractId="+outcontractId+"&projectId="+projectId;
	}

	@RequiresPermissions("out:outcontractCheck:examine")
	@RequestMapping(value = "examine")
	public void examine(OutcontractCheck outcontractCheck,
			RedirectAttributes redirectAttributes) {
		System.out.println("11111");
	}

}