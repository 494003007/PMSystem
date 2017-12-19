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
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;

/**
 * 岗位Controller
 * @author cdoublej
 * @version 2016-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/per/positionType")
public class PositionTypeController extends BaseController {

	@Autowired
	private PositionTypeService positionTypeService;
	
	@ModelAttribute
	public PositionType get(@RequestParam(required=false) String id) {
		PositionType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = positionTypeService.get(id);
		}
		if (entity == null){
			entity = new PositionType();
		}
		return entity;
	}
	
	@RequiresPermissions("per:positionType:view")
	@RequestMapping(value = {"list", ""})
	public String list(PositionType positionType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PositionType> page = positionTypeService.findPage(new Page<PositionType>(request, response), positionType); 
		model.addAttribute("page", page);
		return "modules/per/positionTypeList";
	}

	@RequiresPermissions("per:positionType:view")
	@RequestMapping(value = "form")
	public String form(PositionType positionType, Model model) {
		model.addAttribute("positionType", positionType);
		return "modules/per/positionTypeForm";
	}

	@RequiresPermissions("per:positionType:edit")
	@RequestMapping(value = "save")
	public String save(PositionType positionType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, positionType)){
			return form(positionType, model);
		}
		positionTypeService.save(positionType);
		addMessage(redirectAttributes, "保存岗位成功");
		return "redirect:"+Global.getAdminPath()+"/per/positionType/?repage";
	}
	
	@RequiresPermissions("per:positionType:edit")
	@RequestMapping(value = "delete")
	public String delete(PositionType positionType, RedirectAttributes redirectAttributes) {
		try{
			positionTypeService.delete(positionType);
		}catch(Exception e){
			addMessage(redirectAttributes, "岗位类型已经被关联，不能删除该敢岗位类型");
			return "redirect:"+Global.getAdminPath()+"/per/positionType/?repage";
		}
		addMessage(redirectAttributes, "删除岗位成功");
		return "redirect:"+Global.getAdminPath()+"/per/positionType/?repage";
	}

}