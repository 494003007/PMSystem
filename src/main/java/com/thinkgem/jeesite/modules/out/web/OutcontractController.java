/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractCheck;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.service.OutcontractCheckService;
import com.thinkgem.jeesite.modules.out.service.OutcontractPayService;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import com.thinkgem.jeesite.modules.out.service.OutsourcerService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

/**
 * 外包登记Controller
 * 
 * @author LKY
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontract")
public class OutcontractController extends BaseController {

	@Autowired
	private OutcontractService outcontractService;
	@Autowired
	private OutcontractCheckService outcontractCheckService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private OutcontractPayService outcontractPayService;

	@Autowired
	private OutsourcerService outsourcerService;

	@Autowired
	private ContractService contractService;

	@ModelAttribute
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

	@RequiresPermissions("out:outcontract:view")
	@RequestMapping(value = { "list", "" })
	public String list(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Outcontract outsourcer2 = new Outcontract();
		outsourcer2.setId("0");
		outcontract.setParent(outsourcer2);
		Page<Outcontract> page = outcontractService.findPage(new Page<Outcontract>(request, response), outcontract); 
//		outcontract.setPage(page);
//		
//		List<Outcontract> list = outcontractService.findList1(outcontract);
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getProjectId() != null && list.get(i).getProjectId() != 0) {
//
//				list.get(i).setProjectName(projectService.get(list.get(i).getProjectId() + "").getName());
//			}
//
//			if (list.get(i).getOutcontractTypeId() != null && list.get(i).getOutcontractTypeId() != 0) {
//				if (list.get(i).getOutcontractTypeId() == 1) {
//					list.get(i).setOutcontractTypeName("服务合同");
//				} else if (list.get(i).getOutcontractTypeId() == 2) {
//					list.get(i).setOutcontractTypeName("采购合同");
//				} else {
//					list.get(i).setOutcontractTypeName("分包合同");
//				}
//			}
//
//			if (list.get(i).getOutsourcerId() != null && list.get(i).getOutsourcerId() != 0) {
//
//				list.get(i)
//						.setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId() + "").getCompanyName());
//			}
//
//		}
//		page.setList(list);
		model.addAttribute("page", page);
//		Outsourcer outsourcer = new Outsourcer();
//		model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));
		model.addAttribute("projectlist", projectService.findList(new Project()));
		return "modules/out/outcontractList";
	}



	@RequestMapping(value = { "changeproject" })
	public String changeproject(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOrderId(Integer.parseInt(outcontract.getId()));
		List<OutcontractPay> outcontractPayList = outcontractPayService.findList(outcontractPay);
		model.addAttribute("contractPayList", outcontractPayList);
		model.addAttribute("outsourcersList", outsourcerService.findList(new Outsourcer()));
		model.addAttribute("projectList", projectService.findList(new Project()));
		model.addAttribute("outcontract", outcontract);
		if (outcontract.getProjectId() == null || outcontract.getProjectId() == 0) {
			return "modules/out/addwithoutproject";
		}
		return "modules/out/form1";
	}

	@RequestMapping(value = { "addwithproject" })
	public String addwithproject(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// List<Outcontract> list = outcontractService.findList(outcontract);
		// model.addAttribute("list", list);
		Outsourcer outsourcer = new Outsourcer();
		model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));
		model.addAttribute("projectList", projectService.findList(new Project()));
		return "modules/out/addwithproject";
	}

	@RequestMapping(value = { "addwithoutproject" })
	public String addwithoutproject(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		// List<Outcontract> list = outcontractService.findList(outcontract);
		// model.addAttribute("list", list);
		Outsourcer outsourcer = new Outsourcer();
		model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));

		return "modules/out/addwithoutproject";
	}

	@RequestMapping(value = { "look" })
	public void look(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
		// List<Outcontract> list = outcontractService.findList(outcontract);
		// model.addAttribute("list", list);
		// Outsourcer outsourcer=new Outsourcer();
		// model.addAttribute("outsourcersList",
		// outsourcerService.findList(outsourcer));
		System.out.println(outcontract);
	}

	@RequestMapping(value = { "searchonecontract" })
	public @ResponseBody Map<String, Object> login1(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	
		System.out.println(request.getParameter("name"));
		String id = request.getParameter("name");
	Outcontract a=outcontractService.get(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcontract", a);
		
		return map;
	}
	
	
	@RequestMapping(value = { "searchcontract" })
	public @ResponseBody Map<String, Object> login(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	 
		String id = request.getParameter("name");
		Contract contract = new Contract();
		contract.setProjectId(Integer.parseInt(id));
		List<Contract> list = contractService.findList(contract);
	 

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractlist", list);
		if (request.getParameter("name").equals("123")) {
			System.out.println("城东");
			map.put("msg", "成功");
		} else {
			System.out.println("失败");
			map.put("msg", "失败");
		}
		return map;
	}

	// 返回合同的信息
	@RequestMapping(value = { "findproject" })
	public @ResponseBody Map<String, Object> findproject(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	
		System.out.println(request.getParameter("name"));
		String id = request.getParameter("name");
		Project a=projectService.get(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", a);
		
		return map;
	}
	@RequestMapping(value = { "findcontract" })
	public @ResponseBody Map<String, Object> findcontract(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println(request.getParameter("name"));
		String id = request.getParameter("name");
		Contract contract = contractService.get(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contract", contract);
		return map;
	}
	// 返回合同的信息
	@RequestMapping(value = { "findoutsourcer" })
	public @ResponseBody Map<String, Object> findoutsourcer(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	 
		String id = request.getParameter("name");

		Outsourcer outsourcer = outsourcerService.get(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outsourcer", outsourcer);

		return map;
	}

	@RequestMapping(value = { "findorder" })
	public @ResponseBody Map<String, Object> findorder(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	 
		String id = request.getParameter("name");
		Outcontract outcontract = outcontractService.get(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcontract", outcontract);

		return map;
	}

	@RequiresPermissions("out:outcontract:view")
	@RequestMapping(value = "form")
	public String form(Outcontract outcontract, Model model) {
		if (outcontract.getParent() != null && StringUtils.isNotBlank(outcontract.getParent().getId())) {
			outcontract.setParent(outcontractService.get(outcontract.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(outcontract.getId())) {
				Outcontract outcontractChild = new Outcontract();
				outcontractChild.setParent(new Outcontract(outcontract.getParent().getId()));
				List<Outcontract> list = outcontractService.findList(outcontract);
				if (list.size() > 0) {
					outcontract.setSort(list.get(list.size() - 1).getSort());
					if (outcontract.getSort() != null) {
						outcontract.setSort(outcontract.getSort() + 30);
					}
				}
			}
		}
		if (outcontract.getSort() == null) {
			outcontract.setSort(30);
		}
		model.addAttribute("outcontract", outcontract);
		return "modules/out/outcontractForm";
	}

	@RequiresPermissions("out:outcontract:edit")
	@RequestMapping(value = "save")
	public String save(String [] paydate,Outcontract outcontract, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) throws ParseException {
		if (!beanValidator(model, outcontract)) {
			return intomessage(outcontract.getContractId().toString(),outcontract, model, request);
		}
		outcontractService.save(outcontract);
		 
		outcontract = outcontractService.get(outcontract.getId());
		String orderId = outcontract.getId();
		String outcontractId = outcontract.getParentId().equals("0")?outcontract.getId():outcontract.getParentId();
		outcontractPayService.deleteAll(orderId);
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOrderId(Integer.parseInt(orderId));
		outcontractPay.setOutcontractId(Integer.parseInt(outcontractId));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(paydate.length > 2)
		for(int i = 0; paydate.length > i;i+=4){			
			outcontractPay.setPayPercent(Double.parseDouble(paydate[i]));
			outcontractPay.setPredictPay(Double.parseDouble(paydate[i + 1]));
			outcontractPay.setPayDate(simpleDateFormat.parse(paydate[i + 2]));			 
			outcontractPay.setRemarks(paydate[i+3]);
			outcontractPayService.insert(outcontractPay);
		}		
		addMessage(redirectAttributes, "外包合同添加成功");
		return "redirect:" + Global.getAdminPath() + "/out/outcontract/?repage";
	}

	// 保存补充合同
	@RequestMapping(value = "savecontract")
	public String savecontract(String [] paydate,Outcontract outcontract, Model model, HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Outcontract parent = outcontractService.get(outcontract.getParent().getId() + "");
		Date d1 = parent.getEndTime();
		Date d2 = outcontract.getEndTime();
		outcontractService.save(outcontract);
		if (d1.getTime() < d2.getTime()) {
			System.out.println("<");
			parent.setEndTime(d2);

			outcontractService.save(parent);
		}
		model.addAttribute("outcontract", new Outcontract());
		Outcontract father=outcontractService.get(outcontract.getParentId());
		model.addAttribute("aaa", father);
		Outcontract outcontract1 = new Outcontract();
		outcontract1.setParent(father);
		List<Outcontract> list = outcontractService.findList(outcontract1);
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract1.setPage(page);

		
		outcontract = outcontractService.get(outcontract.getId());
		String orderId = outcontract.getId();
		String outcontractId = outcontract.getParentId().equals("0")?outcontract.getId():outcontract.getParentId();
		outcontractPayService.deleteAll(orderId);
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOrderId(Integer.parseInt(orderId));
		outcontractPay.setOutcontractId(Integer.parseInt(outcontractId));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(paydate.length > 2)
		for(int i = 0; paydate.length > i;i+=4){			
			outcontractPay.setPayPercent(Double.parseDouble(paydate[i]));
			outcontractPay.setPredictPay(Double.parseDouble(paydate[i + 1]));
			outcontractPay.setPayDate(simpleDateFormat.parse(paydate[i + 2]));			 
			outcontractPay.setRemarks(paydate[i+3]);
			outcontractPayService.insert(outcontractPay);
		}		
		
		
		
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/out/add2";
	}
	
	
	@RequestMapping(value = "saveorder")
	public String saveorder(String [] paydate,Outcontract outcontract, Model model, HttpServletRequest request,HttpServletResponse response) throws ParseException {

		outcontractService.save(outcontract);
		model.addAttribute("outcontract", new Outcontract());
		Outcontract outcontract1 = new Outcontract();
		outcontract1.setParent(outcontractService.get(outcontract.getParentId()));
		List<Outcontract> list = outcontractService.findList(outcontract1);
	
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract1.setPage(page);
		page.setList(list);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("aaa", outcontractService.get(outcontract.getParentId()));

		outcontract = outcontractService.get(outcontract.getId());
		String orderId = outcontract.getId();
		String outcontractId = outcontract.getParentId().equals("0")?outcontract.getId():outcontract.getParentId();
		outcontractPayService.deleteAll(orderId);
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOrderId(Integer.parseInt(orderId));
		outcontractPay.setOutcontractId(Integer.parseInt(outcontractId));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(paydate.length > 2)
		for(int i = 0; paydate.length > i;i+=4){			
			outcontractPay.setPayPercent(Double.parseDouble(paydate[i]));
			outcontractPay.setPredictPay(Double.parseDouble(paydate[i + 1]));
			outcontractPay.setPayDate(simpleDateFormat.parse(paydate[i + 2]));			 
			outcontractPay.setRemarks(paydate[i+3]);
			outcontractPayService.insert(outcontractPay);
		}		
		
		
		
		
		return "modules/out/add1";
	}

	@RequiresPermissions("out:outcontract:edit")
	@RequestMapping(value = "delete")
	public String delete(Outcontract outcontract, Model model,HttpServletResponse response,HttpServletRequest request) {
		try{
			outcontractService.delete(outcontract);
		}catch(Exception e){
			model.addAttribute("message", "该合同已被关联，不能删除该合同");
		}
		String name=request.getParameter("name");
		 
		if(name.equals("1")){
			outcontractPayService.delete3(outcontract);
			model.addAttribute("message", "删除成功");
			return list(new Outcontract(), request, response, model);
		}else if(name.equals("2")){
			outcontractPayService.delete2(outcontract);
			model.addAttribute("message", "删除成功");
			Outcontract a=outcontractService.get(outcontract.getParentId());
			return add(a, model, response, request);
		} 	
		 return null;
	}

	@RequestMapping(value = "intomessage")
	public String intomessage(String contractid,Outcontract outcontract, Model model, HttpServletRequest request) {
		
		 
		int view=0;
		Project project=projectService.get(""+outcontract.getProjectId());
		if(project.getIsframecontract().equals("1")){
	
		
			view=1;
		}else{
		
		    if(contractid==null || contractid.equals("")){
		    view=2;
	 
		    }
		}
		model.addAttribute("view", view);
		model.addAttribute("outcontract", outcontract);
		model.addAttribute("outsourcersList", outsourcerService.findList(new Outsourcer()));
		return "modules/out/form1";
	}

	@RequestMapping(value = { "onecontract" })
	public @ResponseBody
	Map<String, Object> onecontract(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		System.out.println(request.getParameter("name"));
		String id = request.getParameter("name");
		Contract a = contractService.get(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contract", a);

		return map;
	}
	// 跳转到增加订单页面
	@RequestMapping(value = "addorder")
	public String addorder(Outcontract outcontract, Model model, HttpServletRequest request) {
		if (outcontract.getParent() != null && StringUtils.isNotBlank(outcontract.getParent().getId())) {
			outcontract.setParent(outcontractService.get(outcontract.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(outcontract.getId())) {
				Outcontract outcontractChild = new Outcontract();
				outcontractChild.setParent(new Outcontract(outcontract.getParent().getId()));
				List<Outcontract> list = outcontractService.findList(outcontract);
				if (list.size() > 0) {
					outcontract.setSort(list.get(list.size() - 1).getSort());
					if (outcontract.getSort() != null) {
						outcontract.setSort(outcontract.getSort() + 30);
					}
				}
			}
		}
		if (outcontract.getSort() == null) {
			outcontract.setSort(30);
		}
		List<OutcontractPay> outcontractPayList = new ArrayList<OutcontractPay>();
		int i = 0;
		OutcontractPay outcontractPay = new OutcontractPay();
		try{
			outcontractPay.setOrderId(Integer.parseInt(outcontract.getId()));
		}catch(Exception r){
			i = 1;
		}
		if(i ==0)
			outcontractPayList  = outcontractPayService.findList(outcontractPay);
		
		model.addAttribute("contractPayList", outcontractPayList);
		model.addAttribute("outcontract", outcontract);
		if (outcontractService.get(outcontract.getParentId()).getSettlementWay().equals("3")) {
			return "modules/out/addKJorder";
		}
		else{
			Contract contract = new Contract();
			contract.setProjectId(outcontractService.get(outcontract.getParentId()).getProjectId());
			model.addAttribute("orderlist", contractService.findList(contract));
			return "modules/out/addorder";
		}
	}

	// 增加补充合同
	@RequestMapping(value = "addcontract")
	public String addcontract(Outcontract outcontract, Model model, HttpServletRequest request) {
		if (outcontract.getParent() != null && StringUtils.isNotBlank(outcontract.getParent().getId())) {
			outcontract.setParent(outcontractService.get(outcontract.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(outcontract.getId())) {
				Outcontract outcontractChild = new Outcontract();
				outcontractChild.setParent(new Outcontract(outcontract.getParent().getId()));
				List<Outcontract> list = outcontractService.findList(outcontract);
				if (list.size() > 0) {
					outcontract.setSort(list.get(list.size() - 1).getSort());
					if (outcontract.getSort() != null) {
						outcontract.setSort(outcontract.getSort() + 30);
					}
				}
			}
		}
		if (outcontract.getSort() == null) {
			outcontract.setSort(30);
		}
		List<OutcontractPay> outcontractPayList = new ArrayList<OutcontractPay>();
		int i = 0;
		OutcontractPay outcontractPay = new OutcontractPay();
		try{
			outcontractPay.setOrderId(Integer.parseInt(outcontract.getId()));
		}catch(Exception r){
			i = 1;
		}
		if(i ==0)
			outcontractPayList  = outcontractPayService.findList(outcontractPay);
		
		model.addAttribute("contractPayList", outcontractPayList);
		model.addAttribute("outcontract", outcontract);
		return "modules/out/addcontract";
	}

	@RequestMapping(value = "add")
	public String add(Outcontract outcontract, Model model,HttpServletResponse response, HttpServletRequest request) {
		String change = request.getParameter("method");
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract.setPage(page);
		List<Outcontract> list=null;
		if (change != null) {
			// 通过查询进来	
			Outcontract father = outcontractService.get(change);
			Outcontract son = new Outcontract();
			son.setOutcontractCode(outcontract.getOutcontractCode());
			son.setParent(father);
			 list = outcontractService.findnewList(son);
			model.addAttribute("list", list);
			
		
			model.addAttribute("outcontract", new Outcontract());
			model.addAttribute("aaa", father);
			outcontract=father;
		} else {
			model.addAttribute("outcontract", new Outcontract());
			model.addAttribute("aaa", outcontract);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(outcontract);
			 list = outcontractService.findList(outcontract1);
		
		
		model.addAttribute("list", list);}
		page.setList(list);
		model.addAttribute("page", page);
		if ("1".equals(outcontract.getSettlementWay())) {
			return "modules/out/add1";
		} else if ("2".equals(outcontract.getSettlementWay())) {
			return "modules/out/add2";
		} else {
			model.addAttribute("outcontractPay", new OutcontractPay());
			return "modules/out/add4";
		}
	}

	// 跳转到具体的增加付款页面
	@RequestMapping(value = "addpayway")
	public String addpayway(Outcontract outcontract, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		System.out.println("进入了addpayway方法");
String outcontractPayId=request.getParameter("outcontractPayId");
if(outcontractPayId!=null){
	OutcontractPay outcontractPay=outcontractPayService.get(outcontractPayId);
	outcontract  =outcontractService.get(""+outcontractPay.getOutcontractId());
	model.addAttribute("outcontractPay", outcontractPay);
	
}else{
	model.addAttribute("outcontractPay", new OutcontractPay());
}
		if ("1".equals(outcontract.getSettlementWay())) {
			// 按订单结算

			OutcontractPay outcontractPay1 = new OutcontractPay();
			outcontractPay1.setOutcontractId(Integer.parseInt(outcontract.getId()));
			Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response),
					outcontractPay1);
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i)
						.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());

			}
			List<OutcontractPay> firstlist = page.getList();
			model.addAttribute("list120", firstlist);

			model.addAttribute("outcontract", outcontract);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(outcontract);
			List<Outcontract> list = outcontractService.findList(outcontract1);
			System.out.println("长度为" + list.size());
			model.addAttribute("list", list);
			
			return "modules/out/payway1";
		} else if ("2".equals(outcontract.getSettlementWay())) {
			// 按合同结算
			model.addAttribute("outcontract", outcontract);
			return "modules/out/payway2";
		} else {
	
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(outcontract);
			List<Outcontract> list = outcontractService.findList(outcontract1);
			System.out.println("长度为" + list.size());
			model.addAttribute("list", list);
			if (outcontract.getProjectId() != null && outcontract.getProjectId() != 0) {
				outcontract.setProjectName(projectService.get(outcontract.getProjectId() + "").getName());
			}
			model.addAttribute("outcontract", outcontract);
			// 框架合同
			return "modules/out/payway3";

		}
	}

	@RequestMapping(value = "outcontractcheck111")
	public String outcontractcheck111(Outcontract outcontract, Model model, HttpServletRequest request) {
		System.out.println("yes");

		model.addAttribute("outcontract", outcontract);

		Outcontract outcontract1 = new Outcontract();
		outcontract1.setParent(outcontract);
		List<Outcontract> list = outcontractService.findList(outcontract1);
		System.out.println("长度为" + list.size());
		model.addAttribute("list", list);
		return "modules/out/check1";

	}

	// 跳转到增加付款页面
	@RequestMapping(value = "addpay")
	public String addpay(Outcontract outcontract, Model model, HttpServletResponse response,
			HttpServletRequest request) {
String method=request.getParameter("method");
if(method!=null){
OutcontractPay outcontractPay = new OutcontractPay();
outcontractPay.setOutcontractId(Integer.parseInt(method));
outcontractPay.setBeginPayDate(outcontract.getStartTime());
outcontractPay.setEndPayDate(outcontract.getEndTime());
Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response),
		outcontractPay);
model.addAttribute("newoutcontract", new Outcontract());
outcontract=outcontractService.get(method);
if ("1".equals(outcontract.getSettlementWay())) {
	// 按订单结算
	for (int i = 0; i < page.getList().size(); i++) {
		page.getList().get(i)
				.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());

	}
	model.addAttribute("page", page);
	Outcontract outcontract1 = new Outcontract();
	outcontract1.setParent(outcontract);
	List<Outcontract> list = outcontractService.findList(outcontract1);
	model.addAttribute("list", list);
	model.addAttribute("outcontract", outcontract);
	return "modules/out/addpay";
} else if ("2".equals(outcontract.getSettlementWay())) {
	// 按合同结算
	model.addAttribute("page", page);
	model.addAttribute("outcontract", outcontract);
	return "modules/out/addpay1";
} else {
	for (int i = 0; i < page.getList().size(); i++) {
		page.getList().get(i)
				.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());
	}
	model.addAttribute("page", page);
	Outcontract outcontract1 = new Outcontract();
	outcontract1.setParent(outcontract);
	List<Outcontract> list = outcontractService.findList(outcontract1);
	model.addAttribute("list", list);
	outcontract.setProjectName(projectService.get(outcontract.getProjectId() + "").getName());
	model.addAttribute("outcontract", outcontract);
	return "modules/out/addpay2";
}
}else{
		
		
		
		
		OutcontractPay outcontractPay = new OutcontractPay();
		outcontractPay.setOutcontractId(Integer.parseInt(outcontract.getId()));
		Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response),
				outcontractPay);
		model.addAttribute("newoutcontract", new Outcontract());
		if ("1".equals(outcontract.getSettlementWay())) {
			// 按订单结算
	
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i)
						.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());

			}
			model.addAttribute("page", page);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(outcontract);
			List<Outcontract> list = outcontractService.findList(outcontract1);
		
			model.addAttribute("list", list);
			model.addAttribute("outcontract", outcontract);
			return "modules/out/addpay";
		} else if ("2".equals(outcontract.getSettlementWay())) {
			// 按合同结算
		
			model.addAttribute("page", page);
			model.addAttribute("outcontract", outcontract);
			return "modules/out/addpay1";
		} else {
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i)
						.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());
			}
			model.addAttribute("page", page);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(outcontract);
			List<Outcontract> list = outcontractService.findList(outcontract1);
			model.addAttribute("list", list);
			outcontract.setProjectName(projectService.get(outcontract.getProjectId() + "").getName());
			model.addAttribute("outcontract", outcontract);
			return "modules/out/addpay2";
		}}
	}



	@RequestMapping(value = { "list1" })
	public String list1(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		List<Outcontract> list = outcontractService.findList(outcontract);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProjectId() != null && list.get(i).getProjectId() != 0) {
				list.get(i).setProjectName(projectService.get(list.get(i).getProjectId() + "").getName());
			}
			if (list.get(i).getOutsourcerId() != null && list.get(i).getOutsourcerId() != 0) {
				list.get(i).setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId() + "").getName());
			}
			if (list.get(i).getContractId() != null && list.get(i).getContractId() != 0) {
				list.get(i).setContractName(contractService.get(list.get(i).getContractId() + "").getName());
			}
			if (list.get(i).getOutcontractTypeId() != null) {
				if (list.get(i).getOutcontractTypeId() == 1) {
					list.get(i).setOutcontractTypeName("服务合同");
				} else if (list.get(i).getOutcontractTypeId() == 2) {
					list.get(i).setOutcontractTypeName("采购合同");
				} else {
					list.get(i).setOutcontractTypeName("分包合同");
				}

			}
		}
		model.addAttribute("list", list);
		model.addAttribute("alist", projectService.findList(new Project()));
		model.addAttribute("blist", outsourcerService.findList(new Outsourcer()));
		return "modules/out/outcontractListAccessory";
	}
	@RequestMapping(value = { "upload" })
	public  String upload(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
	System.out.println("将要进行上传的"+outcontract);
	if(outcontract.getParentId().equals("0")){
		
		return changeproject(outcontract, request, response, model);
	}else{
	Outcontract a=outcontractService.get(	outcontract.getParentId());
		if("1".equals(a.getSettlementWay())){
//			订单
		return 	addorder(outcontract, model, request);
		}else if("1".equals(a.getSettlementWay())){
			return addcontract(outcontract, model, request);
		}
	}
	
		return null;
	}
	@RequiresPermissions("out:outcontract:examine")
	@RequestMapping(value = { "examinechecklist" })
	public String examinecheck(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		
		
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract.setPage(page);
		model.addAttribute("alist", projectService.findList(new Project()));
		model.addAttribute("blist", outsourcerService.findList(new Outsourcer()));
System.out.println("用来查询的"+outcontract);

		List<Outcontract> list = outcontractService.findList1(outcontract);
		// id2保存当前外包合同的id
		String id2 = "";
		// list2用来保存当前外包项目下的一二级合同
		List<Outcontract> list2 = null;

		// b时用来进行查询的outcontract
		String sql = "";
		// sql用来进行查询已验收
		Outcontract b = new Outcontract();
		for (int i = 0; i < list.size(); i++) {
			id2 = list.get(i).getId();
			b = outcontractService.get(id2);
			list2 = outcontractService.find111List(b);
			
			for (int d = 0; d < list2.size(); d++) {
				if (d == (list2.size() - 1)) {

					sql = sql + list2.get(d).getId();

					list.get(i).setCheckamounts(outcontractCheckService.getnumber(sql + ","));
					sql = "";
				

				} else {
					sql = sql + list2.get(d).getId() + ",";

				}
			}
		}
page.setList(list);
model.addAttribute("page", page);
		model.addAttribute("list", list);
	

		return "modules/out/examinecheck";
	}
	
	
	@RequestMapping(value = { "list12" })
	public String list12(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		
		
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract.setPage(page);
		model.addAttribute("alist", projectService.findList(new Project()));
		model.addAttribute("blist", outsourcerService.findList(new Outsourcer()));
System.out.println("用来查询的"+outcontract);

		List<Outcontract> list = outcontractService.findList1(outcontract);
		// id2保存当前外包合同的id
		String id2 = "";
		// list2用来保存当前外包项目下的一二级合同
		List<Outcontract> list2 = null;

		// b时用来进行查询的outcontract
		String sql = "";
		// sql用来进行查询已验收
		Outcontract b = new Outcontract();
		for (int i = 0; i < list.size(); i++) {
			id2 = list.get(i).getId();
			b = outcontractService.get(id2);
			list2 = outcontractService.find111List(b);
			
			for (int d = 0; d < list2.size(); d++) {
				if (d == (list2.size() - 1)) {

					sql = sql + list2.get(d).getId();

					list.get(i).setCheckamounts(outcontractCheckService.getnumber(sql + ","));
					sql = "";
				

				} else {
					sql = sql + list2.get(d).getId() + ",";

				}
			}
		}
page.setList(list);
model.addAttribute("page", page);
		model.addAttribute("list", list);
	

		return "modules/out/outcontractListAccessory1";
	}

	// 查找出当前1级合同下面的2级菜单
	@RequestMapping(value = { "list123" })
	public String list123(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String change = request.getParameter("change");
		System.out.println("是否要进行查询" + change);
		System.out.println("传进来的" + outcontract);
		model.addAttribute("aaaa", outcontract);
		List<Outcontract> list = null;
		if (change != null) {
			list = outcontractService.find111List(outcontract);
		} else {
			Outcontract ab = new Outcontract();
			ab.setId(outcontract.getId());
			list = outcontractService.find111List(ab);

		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
	
		for (int i = 0; i < list.size(); i++) {
			
			
			
			list.get(i).setCheckamounts(outcontractCheckService.getnumber(list.get(i).getId() + ","));
             list.get(i).setAmountcopy(df.format(list.get(i).getAmount()));
			list.get(i).setCheckamountcopy(df.format(list.get(i).getCheckamounts()));
			System.out.println("金额"+list.get(i).getAmountcopy());
			System.out.println("验收"+list.get(i).getCheckamountcopy());
			OutcontractCheck a = new OutcontractCheck();
			a.setOutcontractId(Integer.parseInt(list.get(i).getId()));
			List<OutcontractCheck> list22 = outcontractCheckService.findList(a);
			if (list22.size() != 0) {

				list.get(i).setCheckremarks(list22.get(0).getCheckRemarks());
			}

			if (list.get(i).getProjectId() != null && list.get(i).getProjectId() != 0) {

				list.get(i).setProjectName(projectService.get(list.get(i).getProjectId() + "").getName());
			}

			if (list.get(i).getOutcontractTypeId() != null && list.get(i).getOutcontractTypeId() != 0) {
				if (list.get(i).getOutcontractTypeId() == 1) {
					list.get(i).setOutcontractTypeName("服务合同");
				} else if (list.get(i).getOutcontractTypeId() == 2) {
					list.get(i).setOutcontractTypeName("采购合同");
				} else {
					list.get(i).setOutcontractTypeName("分包合同");
				}
			}

			if (list.get(i).getOutsourcerId() != null && list.get(i).getOutsourcerId() != 0) {

				list.get(i)
						.setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId() + "").getCompanyName());
			}

			if (list.get(i).getCheckExamineStatus().equals("2")) {
				list.get(i).setCheckStatus("验收未审核");
			} else if (list.get(i).getCheckExamineStatus().equals("3")) {
				list.get(i).setCheckStatus("审核通过");
			}
			else if (list.get(i).getCheckExamineStatus().equals("4")) {
				list.get(i).setCheckStatus("审核不通过");
			}
			else {
				list.get(i).setCheckStatus("未验收");
			}

		}
		model.addAttribute("list", list);
		Outsourcer outsourcer = new Outsourcer();
		model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));
		model.addAttribute("projectlist", projectService.findList(new Project()));
		model.addAttribute("outcontract", new Outcontract());
		return "modules/out/outcontractListAccessory2";
	}
	
	// 查找出当前1级合同下面的2级菜单
		@RequestMapping(value = { "examinecheck1" })
		public String examinecheck1(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
				Model model) {
			String change = request.getParameter("change");
			System.out.println("是否要进行查询" + change);
			System.out.println("传进来的" + outcontract);
			model.addAttribute("aaaa", outcontract);
			List<Outcontract> list = null;
			if (change != null) {
				list = outcontractService.find111List(outcontract);
			} else {
				Outcontract ab = new Outcontract();
				ab.setId(outcontract.getId());
				list = outcontractService.find111List(ab);

			}
			DecimalFormat df = new DecimalFormat("#,##0.00");
		
			for (int i = 0; i < list.size(); i++) {
				
				
				
				list.get(i).setCheckamounts(outcontractCheckService.getnumber(list.get(i).getId() + ","));
	             list.get(i).setAmountcopy(df.format(list.get(i).getAmount()));
				list.get(i).setCheckamountcopy(df.format(list.get(i).getCheckamounts()));
				System.out.println("金额"+list.get(i).getAmountcopy());
				System.out.println("验收"+list.get(i).getCheckamountcopy());
				OutcontractCheck a = new OutcontractCheck();
				a.setOutcontractId(Integer.parseInt(list.get(i).getId()));
				List<OutcontractCheck> list22 = outcontractCheckService.findList(a);
				if (list22.size() != 0) {

					list.get(i).setCheckremarks(list22.get(0).getCheckRemarks());
				}

				if (list.get(i).getProjectId() != null && list.get(i).getProjectId() != 0) {

					list.get(i).setProjectName(projectService.get(list.get(i).getProjectId() + "").getName());
				}

				if (list.get(i).getOutcontractTypeId() != null && list.get(i).getOutcontractTypeId() != 0) {
					if (list.get(i).getOutcontractTypeId() == 1) {
						list.get(i).setOutcontractTypeName("服务合同");
					} else if (list.get(i).getOutcontractTypeId() == 2) {
						list.get(i).setOutcontractTypeName("采购合同");
					} else {
						list.get(i).setOutcontractTypeName("分包合同");
					}
				}

				if (list.get(i).getOutsourcerId() != null && list.get(i).getOutsourcerId() != 0) {

					list.get(i)
							.setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId() + "").getCompanyName());
				}

				if (list.get(i).getCheckExamineStatus().equals("2")) {
					list.get(i).setCheckStatus("验收未审核");
				} else if (list.get(i).getCheckExamineStatus().equals("3")) {
					list.get(i).setCheckStatus("审核通过");
				}
				else if (list.get(i).getCheckExamineStatus().equals("4")) {
					list.get(i).setCheckStatus("审核不通过");
				}
				else {
					list.get(i).setCheckStatus("未验收");
				}
			}
			model.addAttribute("list", list);
			Outsourcer outsourcer = new Outsourcer();
			model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));
			model.addAttribute("projectlist", projectService.findList(new Project()));
			model.addAttribute("outcontract", new Outcontract());
			return "modules/out/examinecheck1";
		}
	
	
	
	
	
	@RequestMapping(value = { "listcheck" })
	public String listcheck(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		List<Outcontract> list = outcontractService.findList(outcontract);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProjectId() != null && list.get(i).getProjectId() != 0) {
				list.get(i).setProjectName(projectService.get(list.get(i).getProjectId() + "").getName());
			}
			if (list.get(i).getOutsourcerId() != null && list.get(i).getOutsourcerId() != 0) {
				list.get(i).setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId() + "").getName());
			}
			if (list.get(i).getContractId() != null && list.get(i).getContractId() != 0) {
				list.get(i).setContractName(contractService.get(list.get(i).getContractId() + "").getName());
			}
			if (list.get(i).getOutcontractTypeId() != null) {
				if (list.get(i).getOutcontractTypeId() == 1) {
					list.get(i).setOutcontractTypeName("服务合同");
				} else if (list.get(i).getOutcontractTypeId() == 2) {
					list.get(i).setOutcontractTypeName("采购合同");
				} else {
					list.get(i).setOutcontractTypeName("分包合同");
				}

			}
		}
		model.addAttribute("list", list);
		return "modules/out/outcontractcheck";
	}
	
	
	@RequestMapping(value = { "returnCheck" })
	public String returnCheck(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		
		String parentid="";
			if(outcontract.getParentId().equals("0")){
			parentid=outcontract.getId();
			}else{
			parentid=outcontract.getParentId();
			}
			Outcontract outcontract2=outcontractService.get(parentid);
		
			Outcontract aa=new Outcontract();
			aa.setId(outcontract2.getId());
			List<Outcontract> list = outcontractService.find111List(aa); 
			DecimalFormat df = new DecimalFormat("#,##0.00");
			for(int i=0;i<list.size();i++){
				list.get(i).setCheckamounts(outcontractCheckService.getnumber(list.get(i).getId() + ","));
	             list.get(i).setAmountcopy(df.format(list.get(i).getAmount()));
				list.get(i).setCheckamountcopy(df.format(list.get(i).getCheckamounts()));
				
				if(list.get(i).getProjectId()!=null&&list.get(i).getProjectId()!=0){
		
					list.get(i).setProjectName(projectService.get(list.get(i).getProjectId()+"").getName());
				}
				if(list.get(i).getOutcontractTypeId()!=null&&list.get(i).getOutcontractTypeId()!=0){
					if(list.get(i).getOutcontractTypeId()==1){
						list.get(i).setOutcontractTypeName("服务合同");
					}else if(list.get(i).getOutcontractTypeId()==2){
						list.get(i).setOutcontractTypeName("采购合同");				
					}else{
					list.get(i).setOutcontractTypeName("分包合同");
					}
				}
				if(list.get(i).getOutsourcerId()!=null&&list.get(i).getOutsourcerId()!=0){
					
					list.get(i).setOutsourcerName(outsourcerService.get(list.get(i).getOutsourcerId()+"").getCompanyName());
				}
				if(list.get(i).getCheckExamineStatus().equals("2")){
					list.get(i).setCheckStatus("验收未审核");
				}else if(list.get(i).getCheckExamineStatus().equals("3")){
					list.get(i).setCheckStatus("验收已审核");
				}else{
					list.get(i).setCheckStatus("未验收");
				}
			}
			model.addAttribute("list", list);
			Outsourcer outsourcer=new Outsourcer();
			model.addAttribute("outsourcersList", outsourcerService.findList(outsourcer));
			model.addAttribute("projectlist", projectService.findList(new Project()));
		model.addAttribute("outcontract", new Outcontract());

			return "modules/out/outcontractListAccessory2";
	}
	

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Outcontract> list = outcontractService.findList(new Outcontract());
		for (int i = 0; i < list.size(); i++) {
			Outcontract e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId())
					&& e.getParentIds().indexOf("," + extId + ",") == -1)) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

}