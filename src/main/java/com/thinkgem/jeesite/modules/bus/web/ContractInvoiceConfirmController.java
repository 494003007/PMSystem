/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.web;

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
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;
import com.thinkgem.jeesite.modules.bus.service.ContractInvoiceConfirmService;

/**
 * busController
 * @author cdoublej
 * @version 2016-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/bus/contractInvoiceConfirm")
public class ContractInvoiceConfirmController extends BaseController {

	@Autowired
	private ContractInvoiceConfirmService contractInvoiceConfirmService;
	
	@ModelAttribute
	public ContractInvoiceConfirm get(@RequestParam(required=false) String id) {
		ContractInvoiceConfirm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractInvoiceConfirmService.get(id);
		}
		if (entity == null){
			entity = new ContractInvoiceConfirm();
		}
		return entity;
	}
	
	@RequiresPermissions("bus:contractInvoiceConfirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractInvoiceConfirm contractInvoiceConfirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractInvoiceConfirm> page = contractInvoiceConfirmService.findPage(new Page<ContractInvoiceConfirm>(request, response), contractInvoiceConfirm); 
		model.addAttribute("page", page);
		return "modules/bus/contractInvoiceConfirmList";
	}

	@RequiresPermissions("bus:contractInvoiceConfirm:view")
	@RequestMapping(value = "form")
	public String form(ContractInvoiceConfirm contractInvoiceConfirm, Model model) {
		model.addAttribute("contractInvoiceConfirm", contractInvoiceConfirm);
		return "modules/bus/contractInvoiceConfirmForm";
	}

	@RequiresPermissions("bus:contractInvoiceConfirm:edit")
	@RequestMapping(value = "save")
	public String save(ContractInvoiceConfirm contractInvoiceConfirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractInvoiceConfirm)){
			return form(contractInvoiceConfirm, model);
		}
		contractInvoiceConfirmService.save(contractInvoiceConfirm);
		addMessage(redirectAttributes, "保存bus成功");
		return "redirect:"+Global.getAdminPath()+"/bus/contractInvoiceConfirm/?repage";
	}
	
	@RequiresPermissions("bus:contractInvoiceConfirm:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractInvoiceConfirm contractInvoiceConfirm, RedirectAttributes redirectAttributes) {
		contractInvoiceConfirmService.delete(contractInvoiceConfirm);
		addMessage(redirectAttributes, "删除bus成功");
		return "redirect:"+Global.getAdminPath()+"/bus/contractInvoiceConfirm/?repage";
	}

}