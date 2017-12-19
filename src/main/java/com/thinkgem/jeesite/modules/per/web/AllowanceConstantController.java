/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

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
import com.thinkgem.jeesite.modules.per.entity.AllowanceConstant;
import com.thinkgem.jeesite.modules.per.service.AllowanceConstantService;

/**
 * 补贴常量Controller
 * @author cdoublej
 * @version 2016-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/per/allowanceConstant")
public class AllowanceConstantController extends BaseController {

	@Autowired
	private AllowanceConstantService allowanceConstantService;

	@ModelAttribute
	public AllowanceConstant get(@RequestParam(required=false) String id) {
		AllowanceConstant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = allowanceConstantService.get(id);
		}
		if (entity == null){
			entity = new AllowanceConstant();
		}
		return entity;
	}

	@RequiresPermissions("per:allowanceConstant:view")
	@RequestMapping(value = {"list", ""})
	public String list(AllowanceConstant allowanceConstant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AllowanceConstant> page = allowanceConstantService.findPage(new Page<AllowanceConstant>(request, response), allowanceConstant);
		model.addAttribute("page", page);
		return "modules/per/allowanceConstantList";
	}

	@RequiresPermissions("per:allowanceConstant:view")
	@RequestMapping(value = "form")
	public String form(AllowanceConstant allowanceConstant, Model model) {
		model.addAttribute("allowanceConstant", allowanceConstant);
		return "modules/per/allowanceConstantForm";
	}

	@RequiresPermissions("per:allowanceConstant:edit")
	@RequestMapping(value = "save")
	public String save(AllowanceConstant allowanceConstant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, allowanceConstant)){
			return form(allowanceConstant, model);
		}
		allowanceConstantService.save(allowanceConstant);
		addMessage(redirectAttributes, "保存补贴常量成功");
		return "redirect:"+Global.getAdminPath()+"/per/allowanceConstant/?repage";
	}

	@RequiresPermissions("per:allowanceConstant:edit")
	@RequestMapping(value = "delete")
	public String delete(AllowanceConstant allowanceConstant, RedirectAttributes redirectAttributes) {
		allowanceConstantService.delete(allowanceConstant);
		addMessage(redirectAttributes, "删除补贴常量成功");
		return "redirect:"+Global.getAdminPath()+"/per/allowanceConstant/?repage";
	}

}