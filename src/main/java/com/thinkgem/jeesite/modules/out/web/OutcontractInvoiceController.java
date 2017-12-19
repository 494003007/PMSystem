/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;
import com.thinkgem.jeesite.modules.out.entity.OutcontractInvoice;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import com.thinkgem.jeesite.modules.out.service.OutsourcerService;

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
import com.thinkgem.jeesite.modules.out.entity.OutcontractInvoice;
import com.thinkgem.jeesite.modules.out.service.OutcontractInvoiceService;

import java.util.List;
import java.util.Map;

/**
 * 发票登记Controller
 * @author czy
 * @version 2016-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractInvoice")
public class OutcontractInvoiceController extends BaseController {

	@Autowired
	private OutcontractInvoiceService outcontractInvoiceService;

    @Autowired
    private OutcontractService outcontractService;

    
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
	
	@RequiresPermissions("out:outcontractInvoice:view")
	@RequestMapping(value = {"list", ""})
	public String list(OutcontractInvoice outcontractInvoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OutcontractInvoice> page = outcontractInvoiceService.findPage(new Page<OutcontractInvoice>(request, response), outcontractInvoice); 
		model.addAttribute("page", page);
		return "modules/out/outcontractInvoiceList";
	}

    @RequiresPermissions("out:outcontractInvoice:view")
    @RequestMapping(value = "info")
    public String info(Outcontract outcontract, HttpServletRequest request, HttpServletResponse response, Model model) {
       
    	 Map<String,String> outcontractMap = outcontractInvoiceService.getInfo(outcontract);
    	   
        List<OutcontractInvoice> subInvoice_list = outcontractInvoiceService.findListBysubIds(outcontract.getId());
       
        model.addAttribute("subInvoice_list", subInvoice_list);
        model.addAttribute("outcontractMap", outcontractMap);

        return "modules/out/outcontractInvoiceInfo";
    }
	
	@RequiresPermissions("out:outcontractInvoice:view")
	@RequestMapping(value = "form")
	public String form( Model model, HttpServletRequest request, HttpServletResponse response) {
		String outcontractId = request.getParameter("outcontractId");
		OutcontractInvoice outcontractInvoice = null;
		Outcontract outcontract = outcontractService.get(outcontractId);
		String outcontractInvoiceId = request.getParameter("outcontractInvoiceId");
		if(outcontractInvoiceId != null && !outcontractInvoiceId.equals(""))
			outcontractInvoice = outcontractInvoiceService.get(outcontractInvoiceId);
		else{
			outcontractInvoice = new OutcontractInvoice();
			outcontractInvoice.setOutcontractId(outcontractId);
		}
		
	
		model.addAttribute("outcontract", outcontract);
 
		model.addAttribute("outcontractInvoice", outcontractInvoice);
		return "modules/out/outcontractInvoiceForm";
	}

	@RequiresPermissions("out:outcontractInvoice:edit")
	@RequestMapping(value = "save")
	public String save(OutcontractInvoice outcontractInvoice,Model model, RedirectAttributes redirectAttributes) {
 
		outcontractInvoiceService.save(outcontractInvoice);
		addMessage(redirectAttributes, "保存发票登记成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractInvoice/?repage";
	}
	
	@RequiresPermissions("out:outcontractInvoice:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractInvoice outcontractInvoice, RedirectAttributes redirectAttributes) {
		outcontractInvoiceService.delete(outcontractInvoice);
		addMessage(redirectAttributes, "删除发票登记成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractInvoice/?repage";
	}

}