/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.web;

import java.util.ArrayList;
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
import com.thinkgem.jeesite.modules.bus.entity.InvoiceReturnRegister;
import com.thinkgem.jeesite.modules.bus.service.ContractInvoiceConfirmService;
import com.thinkgem.jeesite.modules.bus.service.InvoiceReturnRegisterService;
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.pro.service.ReturnRegisterService;

/**
 * 发票Controller
 * @author fy
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/bus/invoiceRreturnRegister")
public class InvoiceReturnRegisterController extends BaseController {
	@Autowired
	private ContractInvoiceConfirmService invoiceConfirmService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceReturnRegisterService invoiceReturnRegisterService;
	@Autowired
	private ReturnRegisterService returnRegisterService;
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
	
	@RequiresPermissions("bus:invoiceRreturnRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(Invoice invoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		invoice.setStatus("1");
		Page<Invoice> page = invoiceService.findPage(new Page<Invoice>(request, response), invoice); 
		model.addAttribute("page", page);
		return "modules/bus/returnRegisterList";
	}

	@RequiresPermissions("bus:invoiceRreturnRegister:view")
	@RequestMapping(value = "form")
	public String form(Invoice invoice, Model model) {
		model.addAttribute("invoice", invoice);
		List<ReturnRegister> returnRegisterList =(ArrayList<ReturnRegister>)returnRegisterService.findAllToInvoice(invoice);
		
		model.addAttribute("returnRegisterList", returnRegisterList);
		return "modules/bus/returnRegisterForm";
	}
 
 
	@RequiresPermissions("bus:invoiceRreturnRegister:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam("ajudge")String ajudge,@RequestParam("str")String str,@RequestParam("invoiceId")String invoiceId,Model model, RedirectAttributes redirectAttributes) {

		if(ajudge.equals("2")){
			invoiceReturnRegisterService.delectAllRelation(invoiceId);
			if(str.length()>0){
				String [] str2 = str.split(",");
				for(int i = 0; i < str2.length; i +=2){
					InvoiceReturnRegister invoiceReturnRegister = new InvoiceReturnRegister();
					invoiceReturnRegister.setReturnRegisterId(str2[i]);
					invoiceReturnRegister.setReturnAmount(str2[i+1]);
					invoiceReturnRegister.setInvoiceId(invoiceId);
					invoiceReturnRegisterService.insert(invoiceReturnRegister);
				}
			}
			invoiceService.updateStatus(invoiceId);
			addMessage(redirectAttributes, "回款登记成功");
		}else
			{
		 
			}
		
		return "redirect:"+Global.getAdminPath()+"/bus/invoiceRreturnRegister/list?repage";
	}
	 
}