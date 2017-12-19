/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

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
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.service.OutsourcerService;

/**
 * 外包商管理Controller
 * @author LKY
 * @version 2016-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outsourcer")
public class OutsourcerController extends BaseController {

	@Autowired
	private OutsourcerService outsourcerService;
	
	@ModelAttribute
	public Outsourcer get(@RequestParam(required=false) String id) {
		Outsourcer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = outsourcerService.get(id);
		}
		if (entity == null){
			entity = new Outsourcer();
		}
		return entity;
	}
	
	@RequiresPermissions("out:outsourcer:view")
	@RequestMapping(value = {"list", ""})
	public String list(Outsourcer outsourcer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Outsourcer> page = outsourcerService.findPage(new Page<Outsourcer>(request, response), outsourcer); 
		model.addAttribute("page", page);
		return "modules/out/outsourcerList";
	}

	@RequiresPermissions("out:outsourcer:view")
	@RequestMapping(value = "form")
	public String form(Outsourcer outsourcer, Model model) {
		model.addAttribute("outsourcer", outsourcer);
		return "modules/out/outsourcerForm";
	}

	@RequiresPermissions("out:outsourcer:edit")
	@RequestMapping(value = "save")
	public String save(Outsourcer outsourcer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, outsourcer)){
			return form(outsourcer, model);
		}
		outsourcerService.save(outsourcer);
		addMessage(redirectAttributes, "保存外包商信息成功");
		return "redirect:"+Global.getAdminPath()+"/out/outsourcer/?repage";
	}
	
	@RequiresPermissions("out:outsourcer:edit")
	@RequestMapping(value = "delete")
	public String delete(Outsourcer outsourcer, RedirectAttributes redirectAttributes) {
		outsourcerService.delete(outsourcer);
		addMessage(redirectAttributes, "删除外包商信息成功");
		return "redirect:"+Global.getAdminPath()+"/out/outsourcer/?repage";
	}

}