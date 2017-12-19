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
import com.thinkgem.jeesite.modules.sys.entity.InvoiceContent;
import com.thinkgem.jeesite.modules.sys.service.InvoiceContentService;

/**
 * 开票内容Controller
 * @author cdoublejj
 * @version 2016-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/invoiceContent")
public class InvoiceContentController extends BaseController {

	@Autowired
	private InvoiceContentService invoiceContentService;
	
	@ModelAttribute
	public InvoiceContent get(@RequestParam(required=false) String id) {
		InvoiceContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = invoiceContentService.get(id);
		}
		if (entity == null){
			entity = new InvoiceContent();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:invoiceContent:view")
	@RequestMapping(value = {"list", ""})
	public String list(InvoiceContent invoiceContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InvoiceContent> page = invoiceContentService.findPage(new Page<InvoiceContent>(request, response), invoiceContent); 
		model.addAttribute("page", page);
		return "modules/sys/invoiceContentList";
	}

	@RequiresPermissions("sys:invoiceContent:view")
	@RequestMapping(value = "form")
	public String form(InvoiceContent invoiceContent, Model model) {
		model.addAttribute("invoiceContent", invoiceContent);
		return "modules/sys/invoiceContentForm";
	}

	@RequiresPermissions("sys:invoiceContent:edit")
	@RequestMapping(value = "save")
	public String save(InvoiceContent invoiceContent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, invoiceContent)){
			return form(invoiceContent, model);
		}
		invoiceContentService.save(invoiceContent);
		addMessage(redirectAttributes, "保存开票内容成功");
		return "redirect:"+Global.getAdminPath()+"/sys/invoiceContent/?repage";
	}
	
	@RequiresPermissions("sys:invoiceContent:edit")
	@RequestMapping(value = "delete")
	public String delete(InvoiceContent invoiceContent, RedirectAttributes redirectAttributes) {
		try{
			invoiceContentService.delete(invoiceContent);
		}catch(Exception e){
			addMessage(redirectAttributes, "发票内容已经被关联，不能删除该发票内容");
			return "redirect:"+Global.getAdminPath()+"/sys/invoiceContent/?repage";
		}
		addMessage(redirectAttributes, "删除开票内容成功");
		return "redirect:"+Global.getAdminPath()+"/sys/invoiceContent/?repage";
	}

}