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
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecordDb;
import com.thinkgem.jeesite.modules.product_record.service.ProductRecordDbService;

/**
 * 数据库更改记录Controller
 * @author cdoublej
 * @version 2016-10-10
 */
@Controller
@RequestMapping(value = "${adminPath}/product_record/productRecordDb")
public class ProductRecordDbController extends BaseController {

	@Autowired
	private ProductRecordDbService productRecordDbService;
	
	@ModelAttribute
	public ProductRecordDb get(@RequestParam(required=false) String id) {
		ProductRecordDb entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productRecordDbService.get(id);
		}
		if (entity == null){
			entity = new ProductRecordDb();
		}
		return entity;
	}
	
	@RequiresPermissions("product_record:productRecordDb:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductRecordDb productRecordDb, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductRecordDb> page = productRecordDbService.findPage(new Page<ProductRecordDb>(request, response), productRecordDb); 
		model.addAttribute("page", page);
		return "modules/product_record/productRecordDbList";
	}

	@RequiresPermissions("product_record:productRecordDb:view")
	@RequestMapping(value = "form")
	public String form(ProductRecordDb productRecordDb, Model model) {
		model.addAttribute("productRecordDb", productRecordDb);
		return "modules/product_record/productRecordDbForm";
	}

	@RequiresPermissions("product_record:productRecordDb:edit")
	@RequestMapping(value = "save")
	public String save(ProductRecordDb productRecordDb, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productRecordDb)){
			return form(productRecordDb, model);
		}
		productRecordDbService.save(productRecordDb);
		addMessage(redirectAttributes, "保存数据库更改记录成功");
		return "redirect:"+Global.getAdminPath()+"/product_record/productRecordDb/?repage";
	}
	
	@RequiresPermissions("product_record:productRecordDb:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductRecordDb productRecordDb, RedirectAttributes redirectAttributes) {
		productRecordDbService.delete(productRecordDb);
		addMessage(redirectAttributes, "删除数据库更改记录成功");
		return "redirect:"+Global.getAdminPath()+"/product_record/productRecordDb/?repage";
	}

}