/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.InvoiceType;
import com.thinkgem.jeesite.modules.sys.service.InvoiceTypeService;

/**
 * 发票类型Controller
 * @author cdoublej
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/invoiceType")
public class InvoiceTypeController extends BaseController {

	@Autowired
	private InvoiceTypeService invoiceTypeService;
	
	@ModelAttribute
	public InvoiceType get(@RequestParam(required=false) String id) {
		InvoiceType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = invoiceTypeService.get(id);
		}
		if (entity == null){
			entity = new InvoiceType();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:invoiceType:view")
	@RequestMapping(value = {"list", ""})
	public String list(InvoiceType invoiceType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InvoiceType> page = invoiceTypeService.findPage(new Page<InvoiceType>(request, response), invoiceType); 
		model.addAttribute("page", page);
		return "modules/sys/invoiceTypeList";
	}

	@RequiresPermissions("sys:invoiceType:view")
	@RequestMapping(value = "form")
	public String form(InvoiceType invoiceType, Model model) {
		model.addAttribute("invoiceType", invoiceType);
		return "modules/sys/invoiceTypeForm";
	}

	@RequiresPermissions("sys:invoiceType:edit")
	@RequestMapping(value = "save")
	public String save(InvoiceType invoiceType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, invoiceType)){
			return form(invoiceType, model);
		}
		invoiceTypeService.save(invoiceType);
		addMessage(redirectAttributes, "保存发票类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/invoiceType/?repage";
	}
	
	@RequiresPermissions("sys:invoiceType:edit")
	@RequestMapping(value = "delete")
	public String delete(InvoiceType invoiceType, RedirectAttributes redirectAttributes) {
		try{
			invoiceTypeService.delete(invoiceType);
		}catch(Exception e){
			addMessage(redirectAttributes, "发票类型已经被关联，不能删除该发票类型");
			return "redirect:"+Global.getAdminPath()+"/sys/invoiceType/?repage";
		}
		addMessage(redirectAttributes, "删除发票类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/invoiceType/?repage";
	}

}