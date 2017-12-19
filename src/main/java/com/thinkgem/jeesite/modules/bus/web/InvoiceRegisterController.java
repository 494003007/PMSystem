/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.web;

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

/**
 * 发票Controller
 * @author fy
 * @version 2016-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/bus/invoiceRegister")
public class InvoiceRegisterController extends BaseController {
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
	
	 
	@RequiresPermissions("bus:invoiceRegister:view")
	@RequestMapping(value = {"register"})
	public String register(Invoice invoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Invoice> page = invoiceService.findPage(new Page<Invoice>(request, response), invoice); 
		model.addAttribute("page", page);
		return "modules/bus/invoiceRegister";
	}
	
 
	@RequiresPermissions("bus:invoiceRegister:view")
	@RequestMapping(value = "registerForm")
	public String registerForm(Invoice invoice, Model model) {
		model.addAttribute("invoice", invoice);
		return "modules/bus/invoiceRegisterForm";
	}
 
	@RequiresPermissions("bus:invoiceRegister:edit")
	@RequestMapping(value = "registerSave")
	public String registerSave(Invoice invoice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, invoice)){
			return registerForm(invoice, model);
		}
		invoice.setStatus("2");
		invoiceService.registerUpdate(invoice);
		addMessage(redirectAttributes, "登记发票成功");
		return "redirect:"+Global.getAdminPath()+"/bus/invoiceRegister/register?repage";
	}
	

	
}