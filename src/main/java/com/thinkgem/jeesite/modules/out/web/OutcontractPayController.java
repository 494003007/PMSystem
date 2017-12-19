/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.out.entity.OutcontractPayRegister;
import com.thinkgem.jeesite.modules.out.service.OutcontractPayRegisterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;
import com.thinkgem.jeesite.modules.out.service.OutcontractPayService;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;

/**
 * 外包付款Controller
 * @author LKY
 * @version 2016-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractPay")
public class OutcontractPayController extends BaseController {

	@Autowired
	private OutcontractPayService outcontractPayService;
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private OutcontractService outcontractService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private OutcontractPayRegisterService outcontractPayRegisterService;
	
	
	@ModelAttribute
	public OutcontractPay get(@RequestParam(required=false) String id) {
		OutcontractPay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = outcontractPayService.get(id);
		}
		if (entity == null){
			entity = new OutcontractPay();
		}
		return entity;
	}
	
	@RequiresPermissions("out:outcontractPay:view")
	@RequestMapping(value = {"list", ""})
	public String list(OutcontractPay outcontractPay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay); 
		model.addAttribute("page", page);
		return "modules/out/outcontractPayList";
	}

	@RequiresPermissions("out:outcontractPay:view")
	@RequestMapping(value = "form")
	public String form(OutcontractPay outcontractPay, Model model) {
		model.addAttribute("outcontractPay", outcontractPay);
		return "modules/out/outcontractPayForm";
	}


	@RequestMapping(value = "save")
	public String save(OutcontractPay outcontractPay, Model model, HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, outcontractPay)){
			return form(outcontractPay, model);
		}
		outcontractPayService.save(outcontractPay);
	Outcontract outcontract=outcontractService.get(""+outcontractPay.getOutcontractId());
OutcontractPay outcontractPay1=new OutcontractPay();
outcontractPay1.setOutcontractId( Integer.parseInt(outcontract.getId()));
Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay1); 
for(int i=0;i<page.getList().size();i++){
	page.getList().get(i).setOrderName(outcontractService.get(""+page.getList().get(i).getOrderId()).getName());
}
model.addAttribute("page", page);
Outcontract outcontract1=new Outcontract();
outcontract1.setParent(outcontract);
List<Outcontract> list=outcontractService.findList(outcontract1);

model.addAttribute("list",list );
model.addAttribute("message", "保存成功");
outcontract.setProjectName(projectService.get(outcontract.getProjectId()+"").getName());
   model.addAttribute("outcontract", outcontract);
//框架合同
return "modules/out/addpay2";
//		addMessage(redirectAttributes, "保存外包付款信息成功");
//		return "redirect:"+Global.getAdminPath()+"/out/outcontractPay/?repage";
	}
	
	
//	@RequiresPermissions("out:outcontractPay:edit")
//	@RequestMapping(value = "save2")
//	public String save2(OutcontractPay outcontractPay, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, outcontractPay)){
//			return form(outcontractPay, model);
//		}
//		outcontractPayService.save(outcontractPay);
////		Outcontract outcontract=outcontractService.get(""+outcontractPay.getOutcontractId());
////	OutcontractPay outcontractPay1=new OutcontractPay();
////	outcontractPay1.setOutcontractId( Integer.parseInt(outcontract.getId()));
////	Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay); 
////	for(int i=0;i<page.getList().size();i++){
////		page.getList().get(i).setOrderName(outcontractService.get(""+page.getList().get(i).getOrderId()).getName());
////	}
////	model.addAttribute("page", page);
////	Outcontract outcontract1=new Outcontract();
////	outcontract1.setParent(outcontract);
////	List list=outcontractService.findList(outcontract1);
////	System.out.println("长度为"+list.size());
////	model.addAttribute("list",list );
////	outcontract.setProjectName(projectService.get(outcontract.getProjectId()+"").getName());
////	   model.addAttribute("outcontract", outcontract);
//////	框架合同
////	return "modules/out/addpay2";
//		addMessage(redirectAttributes, "保存外包付款信息成功");
//		return "redirect:"+Global.getAdminPath()+"/out/outcontractPay/?repage";
//	}
	
	
	
	
	
	
	
	
	
//	@RequestMapping(value = "save2")
//	public String save2(OutcontractPay outcontractPay, Model model, HttpServletRequest request,HttpServletResponse response) {
//		outcontractPayService.save(outcontractPay);
//		Outcontract outcontract=outcontractService.get(""+outcontractPay.getOutcontractId());
//	OutcontractPay outcontractPay1=new OutcontractPay();
//	outcontractPay1.setOutcontractId( Integer.parseInt(outcontract.getId()));
//	Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay); 
//	for(int i=0;i<page.getList().size();i++){
//		page.getList().get(i).setOrderName(outcontractService.get(""+page.getList().get(i).getOrderId()).getName());
//	}
//	model.addAttribute("page", page);
//	Outcontract outcontract1=new Outcontract();
//	outcontract1.setParent(outcontract);
//	List list=outcontractService.findList(outcontract1);
//	System.out.println("长度为"+list.size());
//	model.addAttribute("list",list );
//	outcontract.setProjectName(projectService.get(outcontract.getProjectId()+"").getName());
//	   model.addAttribute("outcontract", outcontract);
////	框架合同
//	return "modules/out/addpay2";
//	}
	
	
	
	
	
	@RequestMapping(value = "save1")
	public String save1(OutcontractPay outcontractPay, Model model, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, outcontractPay)){
			return form(outcontractPay, model);
		}
		outcontractPayService.save(outcontractPay);
		
		OutcontractPay outcontractPay1=new OutcontractPay();
    	outcontractPay1.setOutcontractId( outcontractPay.getOutcontractId());
    	Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay1); 
		for(int i=0;i<page.getList().size();i++){
			page.getList().get(i).setOrderName(outcontractService.get(""+page.getList().get(i).getOrderId()).getName());
			
		}
    	Outcontract outcontract=outcontractService.get(""+outcontractPay.getOutcontractId());
    	model.addAttribute("page", page);
		Outcontract outcontract1=new Outcontract();
    	outcontract1.setParent(outcontract);
    	List<Outcontract> list=outcontractService.findList(outcontract1);
    	System.out.println("长度为"+list.size());
    	model.addAttribute("list",list );
    	model.addAttribute("message", "保存信息成功");
    	return "modules/out/addpay";
		

	}
	
	
	@RequestMapping(value = "save2")
	public String save2(OutcontractPay outcontractPay, Model model, HttpServletResponse response,HttpServletRequest request) {
		if (!beanValidator(model, outcontractPay)){
			return form(outcontractPay, model);
		}
		outcontractPayService.save(outcontractPay);
		
		OutcontractPay outcontractPay1=new OutcontractPay();
    	outcontractPay1.setOutcontractId( outcontractPay.getOutcontractId());
    	Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response), outcontractPay); 
		model.addAttribute("page", page);
		model.addAttribute("message", "保存成功");
		return "modules/out/addpay1";
		
		
	}
	

	@RequestMapping(value = "delete")
	public String delete(OutcontractPay outcontractPay, HttpServletRequest request,HttpServletResponse response,Model model) {
		outcontractPayService.delete(outcontractPay);
		Outcontract a=outcontractService.get(outcontractPay.getOutcontractId()+"");
		
		model.addAttribute("message", "删除成功");
		OutcontractPay outcontractPay1 = new OutcontractPay();
		outcontractPay1.setOutcontractId(Integer.parseInt(a.getId()));
		Page<OutcontractPay> page = outcontractPayService.findPage(new Page<OutcontractPay>(request, response),
				outcontractPay1);
		model.addAttribute("newoutcontract", new Outcontract());
		if ("1".equals(a.getSettlementWay())) {
			// 按订单结算
	
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i)
						.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());

			}
			model.addAttribute("page", page);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(a);
			List<Outcontract> list = outcontractService.findList(outcontract1);
		
			model.addAttribute("list", list);
			model.addAttribute("outcontract", a);
			return "modules/out/addpay";
		} else if ("2".equals(a.getSettlementWay())) {
			// 按合同结算
		
			model.addAttribute("page", page);
			model.addAttribute("outcontract", a);
			return "modules/out/addpay1";
		} else {
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i)
						.setOrderName(outcontractService.get("" + page.getList().get(i).getOrderId()).getName());
			}
			model.addAttribute("page", page);
			Outcontract outcontract1 = new Outcontract();
			outcontract1.setParent(a);
			List<Outcontract> list = outcontractService.findList(outcontract1);
			model.addAttribute("list", list);
			a.setProjectName(projectService.get(a.getProjectId() + "").getName());
			model.addAttribute("outcontract", a);
			return "modules/out/addpay2";
		}
		
		

	}
	
	@RequestMapping(value = "KJ_addorder")
	public String KJ_addorder(OutcontractPay outcontractPay, Model model,HttpServletRequest request,HttpServletResponse response) {
//  所对应的订单的id
 Contract contract =contractService.get(outcontractPay.getOrderId()+"");
contract.getCheckClause();
String parentId=request.getParameter("parentid");
  Outcontract outcontract=new Outcontract();
  Outcontract outcontract1=outcontractService.get(parentId);
  outcontract.setParent(outcontract1);
  outcontract.setRemarks(contract.getRemarks());
  outcontract.setStartTime(contract.getStartTime());
		outcontract.setEndTime(contract.getEndTime());
outcontract.setOutcontractContent(contract.getCheckClause());		
double c=	contract.getAmount();
		outcontract.setAmount(c);
		outcontract.setOutcontractCode(contract.getId());
		outcontract.setName(contract.getName());
		outcontractService.save(outcontract);
//		返回页面
//	 	Outcontract outcontract2=new Outcontract();
//    	outcontract2.setParent(outcontract1);
//    	List list=outcontractService.findList(outcontract2);
//    	System.out.println("长度为"+list.size());
//    	model.addAttribute("list",list );
//    	model.addAttribute("outcontract", outcontract1);
//    	model.addAttribute("father", outcontract1);
//    Contract 	contract2=new Contract();
//    contract2 .setProjectId(outcontract1.getProjectId());
//    model.addAttribute("orderlist", contractService.findList(contract2));
//    model.addAttribute("message", "添加成功");
//    model.addAttribute("outcontractPay", new OutcontractPay());
//		return "modules/out/add3";
//		萨达


	model.addAttribute("outcontract", new Outcontract());
	model.addAttribute("aaa", outcontract1);
	Outcontract outcontract2 = new Outcontract();
	outcontract2.setParent(outcontract1);
		Page<Outcontract> page=new  Page<Outcontract>(request, response);
		outcontract2.setPage(page);
	List<Outcontract> list = outcontractService.findList(outcontract2);


model.addAttribute("list", list);
page.setList(list);
model.addAttribute("page", page);
Contract contract1 = new Contract();
contract1.setProjectId(outcontract1.getProjectId());
model.addAttribute("orderlist", contractService.findList(contract1));
model.addAttribute("outcontractPay", new OutcontractPay());
return "modules/out/add3";
		
		
		
		
	}
	

}