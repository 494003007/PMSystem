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
import com.thinkgem.jeesite.modules.out.entity.OutcontractAccessory;
import com.thinkgem.jeesite.modules.out.service.OutcontractAccessoryService;

/**
 * 外包附件Controller
 * @author LKY
 * @version 2016-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractAccessory")
public class OutcontractAccessoryController extends BaseController {

	@Autowired
	private OutcontractAccessoryService outcontractAccessoryService;
	
	@ModelAttribute
	public OutcontractAccessory get(@RequestParam(required=false) String id) {
		OutcontractAccessory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = outcontractAccessoryService.get(id);
		}
		if (entity == null){
			entity = new OutcontractAccessory();
		}
		return entity;
	}
	
	@RequiresPermissions("out:outcontractAccessory:view")
	@RequestMapping(value = {"list", ""})
	public String list(OutcontractAccessory outcontractAccessory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OutcontractAccessory> page = outcontractAccessoryService.findPage(new Page<OutcontractAccessory>(request, response), outcontractAccessory); 
		model.addAttribute("page", page);
		return "modules/out/outcontractAccessoryList";
	}

	@RequiresPermissions("out:outcontractAccessory:view")
	@RequestMapping(value = "form")
	public String form(OutcontractAccessory outcontractAccessory, Model model) {
		model.addAttribute("outcontractAccessory", outcontractAccessory);
		return "modules/out/outcontractAccessoryForm";
	}

	@RequiresPermissions("out:outcontractAccessory:edit")
	@RequestMapping(value = "save")
	public String save(OutcontractAccessory outcontractAccessory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, outcontractAccessory)){
			return form(outcontractAccessory, model);
		}
		outcontractAccessoryService.save(outcontractAccessory);
		addMessage(redirectAttributes, "保存附件信息成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractAccessory/?repage";
	}
	
	@RequiresPermissions("out:outcontractAccessory:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractAccessory outcontractAccessory, RedirectAttributes redirectAttributes) {
		outcontractAccessoryService.delete(outcontractAccessory);
		addMessage(redirectAttributes, "删除附件信息成功");
		return "redirect:"+Global.getAdminPath()+"/out/outcontractAccessory/?repage";
	}

}