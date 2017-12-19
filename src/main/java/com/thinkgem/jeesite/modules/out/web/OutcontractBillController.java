/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.bus.web.InvoiceController;
import com.thinkgem.jeesite.modules.bus.web.InvoiceRegisterController;
import com.thinkgem.jeesite.modules.out.entity.*;
import com.thinkgem.jeesite.modules.out.service.*;

import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thoughtworks.xstream.mapper.Mapper;
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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * outcontract_billController
 * @author czy
 * @version 2016-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractBill")
public class OutcontractBillController extends BaseController {
	@Autowired	
	private OutcontractCheckService outcontractCheckService;
	@Autowired
	private OutcontractBillService outcontractBillService;
	@Autowired
	OutcontractPayRegisterService outcontractPayRegisterService;
	@Autowired
	OutcontractPayService outcontractPayService;
	@Autowired
	private OutcontractService outcontractService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private OutcontractInvoiceService outcontractInvoiceService;
	@Autowired
	private ContractService contractService;

	private static Date lastDate = new Date("Thu Mar 23 16:00:09 CST 2017");

	private static int times = 1;


 	@ModelAttribute
	public OutcontractBill get(@RequestParam(required=false,value="id") String id) { 
		OutcontractBill entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = outcontractBillService.get(id);
		}
		if (entity == null){
			entity = new OutcontractBill();
		}
		return entity;
	}
	public static String changeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100+0.5); // 转化成整形,四舍五入
		String valStr = String.valueOf(midVal); // 转化成字符串
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		boolean preZero = false; // 标志当前位的上一位是否为有效0位（如万位的0对千位无效）
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置

			if (chDig[i] == '0') { // 如果当前字符是0
				preZero = true;
				zeroSerNum++; // 连续0次数递增
				if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					preZero = false; // 不管上一位是否为0，置为无效0位
				}
			}
			else {
				zeroSerNum = 0; // 连续0次数清零
				if (preZero) { // 上一位为有效0位
					prefix += digit[0]; // 只有在这地方用到'零'
					preZero = false;
				}
				prefix += digit[chDig[i] - '0']; // 转化该数字表示
				if (idx > 0)
					prefix += hunit[idx - 1];
				if (idx == 0 && vidx > 0) {
					prefix+=vunit[(vidx+1)%2];
					for(int j=0;j<(vidx+1)/2-1;j++){
						prefix+=vunit[1];
					}
				}
			}
		}
		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	public static boolean sameDate(Date d1, Date d2){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(d1).equals(fmt.format(d2));
	}

	public static Map<String,String> createBillNumber(Date theDate){
		Map<String,String> b = new HashMap<String,String>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String date1 = fmt.format(theDate);
		String date2 = fmt.format(lastDate);
		Iterator keys = b.keySet().iterator();
		if(!fmt.format(theDate).equals(fmt.format(lastDate))){
			times = 1;
			while(keys.hasNext()){
				String key = (String)keys.next();
				if(key.equals(date2)){
					b.remove(date2);
				}
			}
			lastDate = new Date();
		}
		else{
			times = times + 1;
		}
		String a = times + "";
		b.put(date1,a);
		return b;
	}
	/*@ModelAttribute
	public Outcontract get(@RequestParam(required = false) String id) {
		Outcontract entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = outcontractService.get(id);
		}
		if (entity == null) {
			entity = new Outcontract();
		}
		return entity;
	}
	*/


// 	@RequiresPermissions("out:outcontractBill:view")
//	@RequestMapping("print")
//	public String print(HttpServletRequest request, HttpServletResponse response, Model model){
// 		Map<String,String> printMap = new HashMap<String,String>();
// 		String url = "";
// 		String id = request.getParameter("id");
// 		String printType = request.getParameter("printType");
// 		String projectId = request.getParameter("projectId");
// 		String outcontractId = request.getParameter("outcontractId");
// 		String outcontractCheckId = request.getParameter("outcontractCheckId");
// 		String outcontractPayId = request.getParameter("outcontractPayId");
// 		Date date = new Date();
// 		if(printType.equals("printbill")){
// 			printMap = outcontractBillService.getprintBill(projectId,outcontractId);
//
// 			url = "outcontractPrintBill";
// 		}else if(printType.equals("printpay")){
//
//			printMap = outcontractBillService.getprintPay(projectId,outcontractId);
//
// 			url = "outcontractPrintPay";
// 		}
//			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//			printMap.put("thisdate", s.format(date));
//	 		model.addAttribute("printMap",printMap);
//			return "modules/out/"+url;
//	}

	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("billprint")
	public String billprint(OutcontractBill outcontractBill,HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,String> printMap = new HashMap<String,String>();
		String id = request.getParameter("id");
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		String billNumber;
		if(id.equals("") || id == null){
			outcontractBill = new OutcontractBill();
			outcontractBill.setOutcontractCheckId(outcontractCheckId);
			outcontractBill = outcontractBillService.get2(outcontractBill);
		}
		else{
			outcontractBill = outcontractBillService.get(id);
		}
		if(outcontractBill.getBillNumber() == null){
			Date date = new Date();
			Map<String,String> a = createBillNumber(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String num = a.get(dateFormat.format(date));
			billNumber = dateFormat.format(date) + "0" + num;
			outcontractBill.setBillNumber(billNumber);
		}
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		printMap = outcontractBillService.getprintPay(projectId,outcontractId);
		Project project = projectService.getProjectDetail(projectId);
		Outcontract outcontract = outcontractService.get(outcontractId);
		//OutcontractBill outcontractBill1 = new OutcontractBill();
		//OutcontractPayRegister outcontractPayRegister = new OutcontractPayRegister();
		List<OutcontractBill> list = outcontractBillService.findBillList(outcontractId);
		model.addAttribute("list",list);
		model.addAttribute("outcontractBill", outcontractBill);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("outcontractCheckId",outcontractCheckId);
		model.addAttribute("project", project);
		model.addAttribute("printMap",printMap);
		return "modules/out/outcontractPrintCount";
	}


	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping(value = "applyprint")
	public String applyprint(OutcontractBill outcontractBill, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> printMap = new HashMap<String,String>();
		String id = request.getParameter("id");
		System.out.println(id);
		OutcontractPayRegister outcontractPayRegister;
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		if(id == null || id.equals("")){
			outcontractPayRegister = new OutcontractPayRegister();
			outcontractPayRegister = outcontractPayRegisterService.getPrint(outcontractCheckId);
		}
		else{
			outcontractPayRegister = outcontractPayRegisterService.get(id);
		}

		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		Outcontract outcontract = outcontractService.get(outcontractId);
		printMap = outcontractBillService.getprintPay(projectId,outcontractId);
		OutcontractBill outcontractBill1 = new OutcontractBill();
		outcontractBill1.setOutcontractCheckId(outcontractCheckId);
		outcontractBill1 = outcontractBillService.get2(outcontractBill1);
		List<OutcontractBill> list = outcontractBillService.findBillList(outcontractId);
		model.addAttribute("list",list);
		model.addAttribute("printMap",printMap);
		model.addAttribute("project",project);
		model.addAttribute("outcontract",outcontract);
		model.addAttribute("outcontractPayRegister", outcontractPayRegister);
		model.addAttribute("outcontractId", outcontractId);
		model.addAttribute("outcontractBill",outcontractBill);
		model.addAttribute("outcontractBill1",outcontractBill1);
		model.addAttribute("projectId", projectId);
		return "modules/out/outcontractPrintApply";
	}

	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("addprint")
	public String addprint(OutcontractPayRegister outcontractPayRegister,HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,String> printMap = new HashMap<String,String>();
		String id = request.getParameter("id");
		outcontractPayRegister = outcontractPayRegisterService.get(id);
		String outcontractCheckId = outcontractPayRegister.getOutcontractCheckId();
		OutcontractBill outcontractBill = new OutcontractBill();
		outcontractBill.setOutcontractCheckId(outcontractCheckId);
		outcontractBill = outcontractBillService.get2(outcontractBill);
		String money = changeToBig(outcontractPayRegister.getPayAmount());
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		printMap = outcontractBillService.getprintPay(projectId,outcontractId);
		Project project = projectService.getProjectDetail(projectId);
		Outcontract outcontract = outcontractService.get(outcontractId);
		OutcontractBill outcontractBill1 = new OutcontractBill();
		outcontractBill1.setOutcontractCheckId(outcontractCheckId);
		outcontractBill1 = outcontractBillService.get2(outcontractBill1);
		List<OutcontractBill> list = outcontractBillService.findListByprojectId(projectId);
		if(outcontractPayRegister.getPrintTimes() == 0){
			outcontractPayRegister.setPrintTimes(1);
		}
		else{
			int times = outcontractPayRegister.getPrintTimes()+1;
			outcontractPayRegister.setPrintTimes(times);
		}
		outcontractPayRegisterService.save(outcontractPayRegister);
		model.addAttribute("money",money);
		model.addAttribute("list",list);
		model.addAttribute("outcontractPayRegister",outcontractPayRegister);
		model.addAttribute("outcontractBill", outcontractBill);
		model.addAttribute("outcontractBill1", outcontractBill1);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("project", project);
		model.addAttribute("printMap",printMap);
		return "modules/out/outcontractPrintAdd";
	}

	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping(value = "payform")
	public String payform( Model model, HttpServletRequest request, HttpServletResponse response) {
		OutcontractPayRegister outcontractPayRegister = new OutcontractPayRegister();
		String id = request.getParameter("id");
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		if( id == null || id.equals("")){
			outcontractPayRegister = outcontractPayRegisterService.get2(outcontractCheckId);
		}else{
			outcontractPayRegister = outcontractPayRegisterService.get(id);
		}
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		model.addAttribute("outcontractPayRegister", outcontractPayRegister);
		model.addAttribute("outcontractId", outcontractId);
		model.addAttribute("projectId", projectId);
		model.addAttribute("outcontractCheckId",outcontractCheckId);
		return "modules/out/outcontractBillPayRegisterForm";
	}
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping(value = "form")
	public String form(OutcontractBill outcontractBill, Model model, HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		if(id.equals("") || id == null){
			outcontractBill = new OutcontractBill();
			outcontractBill.setOutcontractCheckId(outcontractCheckId);
			outcontractBill = outcontractBillService.get2(outcontractBill);
		}
		OutcontractCheck outcontractCheck = outcontractCheckService.get(outcontractCheckId);
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		model.addAttribute("outcontractCheck",outcontractCheck);
		model.addAttribute("outcontractBill", outcontractBill);
		model.addAttribute("outcontractId", outcontractId);
		model.addAttribute("outcontractCheckId",outcontractCheckId);
		model.addAttribute("projectId", projectId);
		return "modules/out/outcontractBillForm";
	}
	
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("payList")
	public String payList( HttpServletRequest request, HttpServletResponse response, Model model) {
		OutcontractPayRegister outcontractPayRegister = new OutcontractPayRegister();
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		String outcontractId = request.getParameter("outcontractId");
		Outcontract outcontract = outcontractService.get(outcontractId);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("project", project);
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		outcontractPayRegister.setOutcontractCheckId(outcontractCheckId);
		outcontractPayRegister.setOutcontractId(outcontractId);
		Page<OutcontractPayRegister> page = outcontractPayRegisterService.findPayPage(new Page<OutcontractPayRegister>(request, response),outcontractPayRegister);
		model.addAttribute("page", page);
		model.addAttribute("outcontractCheckId", outcontractCheckId);
		return "modules/out/outcontractBillPayDetailList";
	}
	
	
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("projectList")
	public String projectList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		 
		Page<Project> page = projectService.findOutDetailPage(new Page<Project>(request, response), project); 
		model.addAttribute("page", page);
		return "modules/out/billProjectDetailList";
	}
	
	
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping(value = {"list", ""})
	public String list(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);

		model.addAttribute("project", project);
		outcontract.setProjectId(Integer.parseInt(projectId));
		Page<Outcontract> page = outcontractService.findCheckListPage(new Page<Outcontract>(request, response), outcontract);
	  
		model.addAttribute("page", page);
		return "modules/out/outcontractBillList";
	}
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("billList")
	public String payList(OutcontractBill outcontractBill, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		Project project = projectService.getProjectDetail(projectId);
		String outcontractId = request.getParameter("outcontractId");
		Outcontract outcontract = outcontractService.get(outcontractId);
		OutcontractCheck outcontractCheck = new OutcontractCheck();
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("project", project);
		outcontractBill.setOutcontractId(outcontractId);
		Page<OutcontractBill> page = outcontractBillService.findPage(new Page<OutcontractBill>(request, response),outcontractBill);

		model.addAttribute("page", page);
		return "modules/out/outcontractBillDetailList";
	}
	
	
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping(value = "info")
	public String info(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		
		 Map<String,String> outcontractMap = outcontractBillService.getInfo(outcontract);
		 List<OutcontractBill> subInvoice_list = outcontractBillService.findListBysubIds(outcontract.getId());
		 model.addAttribute("outcontractMap", outcontractMap);
		 model.addAttribute("subInvoice_list", subInvoice_list);
		 return "modules/out/outcontractBillInfo";
	}
//	@RequiresPermissions("out:outcontractBill:view")
//	@RequestMapping(value = "form")
//	public String form( Model model, HttpServletRequest request, HttpServletResponse response) {
//		OutcontractBill outcontractBill = null;
//		String outcontractBillId = request.getParameter("outcontractBillId");
//		if(outcontractBillId != null && !outcontractBillId.equals(""))
//			outcontractBill = outcontractBillService.get(outcontractBillId);
//		else
//			outcontractBill = new OutcontractBill();
//		String outcontractId = request.getParameter("outcontractId");
//		Outcontract outcontract = outcontractService.get(outcontractId);
//		model.addAttribute("outcontract", outcontract);
//		model.addAttribute("outcontractBill", outcontractBill);
//		return "modules/out/outcontractBillForm";
//	}
	
	
	@RequiresPermissions("out:outcontractBill:view")
	@RequestMapping("outcontractBillSelect")
	public String outcontractBillSelect(HttpServletRequest request, HttpServletResponse response, Model model) {
		String selecttype = request.getParameter("selecttype");
		String outcontractId = request.getParameter("outcontractId");
		OutcontractCheck outcontractCheck = new OutcontractCheck();
		outcontractCheck.setOutcontractId(Integer.parseInt(outcontractId));
		Page<OutcontractCheck> page = outcontractCheckService.findSelectPage(new Page<OutcontractCheck>(request, response), outcontractCheck);
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/out/outcontractBillSelect";
	}
	
	@RequestMapping(value ="selectOutcontractBill",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectContract(HttpServletRequest request, HttpServletResponse response, Model model) {		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat d = new DecimalFormat("###,##0.00");
		Map<String,String> map = new HashMap<String,String>();
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		OutcontractCheck outcontractCheck = outcontractCheckService.getAjaxDate(outcontractCheckId);		
		map.put("outcontractCode",outcontractCheck.getOutcontractCode());
		map.put("predictPay", d.format(Double.parseDouble(outcontractCheck.getOutcontractPredictPay())));
		map.put("totalcheckamount",d.format(Double.parseDouble(outcontractCheck.getTotalcheckamount())));
		map.put("payDate",s.format(outcontractCheck.getOutcontractPayDate()));
		map.put("startTime",s.format(outcontractCheck.getSignTime()) );
		map.put("endTime", s.format(outcontractCheck.getEndTime()));
		map.put("signTime", s.format(outcontractCheck.getSignTime()));
		map.put("outcontractContent",outcontractCheck.getOutcontractContent() );
		map.put("checkContent",outcontractCheck.getCheckContent());
		
		map.put("totalcheckamount", d.format(Double.parseDouble(outcontractCheck.getTotalcheckamount())));
		map.put("totalpayamount", d.format(Double.parseDouble(outcontractCheck.getTotalpayamount())));
		map.put("totalbillamount", d.format(Double.parseDouble(outcontractCheck.getTotalbillamount())));
		map.put("checkAmount", d.format(outcontractCheck.getCheckAmount()));
		map.put("checkDate",s.format(outcontractCheck.getCheckDate()));
		map.put("remarks",outcontractCheck.getRemarks());
		map.put("billPayAmount",d.format(Double.parseDouble(outcontractCheck.getBillPayAmount())));
		map.put("surplusamount",d.format(Double.parseDouble(outcontractCheck.getSurplusamount())));
		
//		map.put("checkContent",DictUtils.getDictLabels(contract.getInvoiceRevenue(), "tax_percent", ""));
		//map.put("contract_customerId", contract.getCustomer().getName());
		return map;
	}


	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "printsave",method= RequestMethod.POST)
	public  void printsave(OutcontractBill outcontractBill, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String billNumber;
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		if(outcontractBill.getBillNumber() == null){
			Date date = new Date();
			Map<String,String> a = createBillNumber(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String num = a.get(dateFormat.format(date));
			billNumber = dateFormat.format(date) + "0" + num;
			outcontractBill.setBillNumber(billNumber);
		}
		if(outcontractBill.getPrintTimes() == 0){
			outcontractBill.setPrintTimes(1);
		}
		else{
			int times = outcontractBill.getPrintTimes()+1;
			outcontractBill.setPrintTimes(times);
		}
		outcontractBill.setOutcontractId(request.getParameter("outcontractId"));
		outcontractBillService.save(outcontractBill);


	}




	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "printpaysave",method= RequestMethod.POST)
	public @ResponseBody void  printpaysave(OutcontractPayRegister outcontractPayRegister,String paytype, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		outcontractPayRegister.setOutcontractId(outcontractId);
		outcontractPayRegisterService.save(outcontractPayRegister);

	}




	
	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "save")
	public String save(OutcontractBill outcontractBill, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		outcontractBill.setOutcontractId(outcontractId);
		outcontractBillService.save(outcontractBill);
		addMessage(redirectAttributes, "列账登记保存成功");
	//	return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/form?id="+outcontractBill.getId();
		return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
	 
	}
	
/*	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractBill outcontractBill, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		try{
			outcontractBillService.delete(outcontractBill);
		}catch(Exception e){
			addMessage(redirectAttributes, "列账登记记录已被关联，不能删除该记录");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
		 
		}
		addMessage(redirectAttributes, "列账登记删除成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
	}
*/
	
	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractBill outcontractBill, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		try{
			outcontractBillService.delete(outcontractBill);
		}catch(Exception e){
			addMessage(redirectAttributes, "列账登记记录已被关联，不能删除该记录");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
		 
		}
		addMessage(redirectAttributes, "列账登记删除成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
	}





	
	
	
	
	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "paysave")
	public String  paysave(OutcontractPayRegister outcontractPayRegister,String paytype, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		/*if(paytype.equals("2")){//计提付款
			String outcontractBillId = outcontractPayRegister.getOutcontractBillId();
			Double payAmount = outcontractPayRegister.getPayAmount();
			outcontractBillService.updateBillAmount(outcontractBillId,payAmount);
		}*/
		outcontractPayRegister.setOutcontractId(outcontractId);
		outcontractPayRegisterService.save(outcontractPayRegister);
		addMessage(redirectAttributes, "外包合同付款登记保存成功");
		return  "redirect:"+Global.getAdminPath()+"/out/outcontractBill/payList?outcontractId="+outcontractId+"&projectId="+projectId+"&outcontractCheckId="+outcontractCheckId;
	 
	}
	
	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "paydelete")
	public String paydelete(RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		String id = request.getParameter("id");
		OutcontractPayRegister outcontractPayRegister  = outcontractPayRegisterService.get(id);
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		try{
			outcontractPayRegisterService.delete(outcontractPayRegister);
		}catch(Exception e){
			addMessage(redirectAttributes, "付款登记已被关联，不能删除该记录");
			return "redirect:"+Global.getAdminPath()+"/out/outcontractPayRegister/payList?outcontractId="+outcontractId+"&projectId="+projectId;

		}
		addMessage(redirectAttributes, "外包合同付款登记删除成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/payList?outcontractId="+outcontractId+"&projectId="+projectId+"&outcontractCheckId="+outcontractCheckId;
	}

	@RequiresPermissions("out:outcontractBill:edit")
	@RequestMapping(value = "billdelete")
	public String billdelete(RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String outcontractCheckId = request.getParameter("outcontractCheckId");
		String id = request.getParameter("id");
		String outcontractId = request.getParameter("outcontractId");
		String projectId = request.getParameter("projectId");
		OutcontractBill outcontractBill  = outcontractBillService.get(id);
		try{
			outcontractBillService.delete(outcontractBill);
		}catch(Exception e){

		}
		addMessage(redirectAttributes, "计提删除成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractBill/billList?outcontractId="+outcontractId+"&projectId="+projectId;
	}
}