/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.web;

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
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecord;
import com.thinkgem.jeesite.modules.product_record.service.ProductRecordService;

/**
 * product_recordController
 * @author cdoublej
 * @version 2016-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/product_record/productRecord")
public class ProductRecordController extends BaseController {

	@Autowired
	private ProductRecordService productRecordService;
	//被@ModelAttribute注释的方法会在此controller每个方法执行前被执行,也就是说映射到productRecord会执行modelattribute
	@ModelAttribute
	//@RequestParam = request.getParameter("name")，通过required=false（要传）或者true来要求@RequestParam配置的前端参数是否一定要传 
	public ProductRecord get(@RequestParam(required=false) String id) {
		ProductRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productRecordService.get(id);
		}
		if (entity == null){
			entity = new ProductRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("product_record:productRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductRecord productRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductRecord> page = productRecordService.findPage(new Page<ProductRecord>(request, response), productRecord); 
		model.addAttribute("page", page);
		return "modules/product_record/productRecordList";
	}

	@RequiresPermissions("product_record:productRecord:view")
	@RequestMapping(value = "form")
	public String form(ProductRecord productRecord, Model model) {
		model.addAttribute("productRecord", productRecord);
		return "modules/product_record/productRecordForm";
	}

	@RequiresPermissions("product_record:productRecord:edit")
	@RequestMapping(value = "save")
	public String save(ProductRecord productRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productRecord)){
			return form(productRecord, model);
		}
		productRecordService.save(productRecord);
		addMessage(redirectAttributes, "保存框架更改记录成功");
		return "redirect:"+Global.getAdminPath()+"/product_record/productRecord/?repage";
	}
	
	@RequiresPermissions("product_record:productRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductRecord productRecord, RedirectAttributes redirectAttributes) {
		productRecordService.delete(productRecord);
		addMessage(redirectAttributes, "删除框架更改记录成功");
		return "redirect:"+Global.getAdminPath()+"/product_record/productRecord/?repage";
	}
	
 
	@RequestMapping(value = "selectFarme")
	public String selectFarme(ProductRecord productRecord, Model model, RedirectAttributes redirectAttributes) {
		 model.addAttribute("productRecord", productRecord);
		 return "modules/product_record/selectFarme";
	}
	@RequestMapping(value = "farme")
	public String farme(ProductRecord productRecord, Model model, RedirectAttributes redirectAttributes) {
		 model.addAttribute("productRecord", productRecord);
		 return "modules/product_record/farme";
	}
	
	 
		@RequestMapping(value = "jqueryValidator")
		public String jqueryValidator(ProductRecord productRecord, Model model, RedirectAttributes redirectAttributes) {
			 model.addAttribute("productRecord", productRecord);
			 return "modules/product_record/jqueryValidator";
		}
		
		@RequestMapping(value = "DBword")
		public String DBword(ProductRecord productRecord, Model model, RedirectAttributes redirectAttributes) {
			 model.addAttribute("productRecord", productRecord);
			 return "modules/product_record/DBword";
		}
}