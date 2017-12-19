/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.web;

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

import com.google.common.util.concurrent.ExecutionError;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;

/**
 * 出勤类型Controller
 * @author cdoublej
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/attendenceType")
public class AttendenceTypeController extends BaseController {

	@Autowired
	private AttendenceTypeService attendenceTypeService;
	
	@ModelAttribute
	public AttendenceType get(@RequestParam(required=false) String id) {
		AttendenceType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendenceTypeService.get(id);
		}
		if (entity == null){
			entity = new AttendenceType();
		}
		return entity;
	}
	
	@RequiresPermissions("att:attendenceType:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendenceType attendenceType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttendenceType> page = attendenceTypeService.findPage(new Page<AttendenceType>(request, response), attendenceType); 
		model.addAttribute("page", page);
		return "modules/att/attendenceTypeList";
	}

	@RequiresPermissions("att:attendenceType:view")
	@RequestMapping(value = "form")
	public String form(AttendenceType attendenceType, Model model) {
		model.addAttribute("attendenceType", attendenceType);
		return "modules/att/attendenceTypeForm";
	}

	@RequiresPermissions("att:attendenceType:edit")
	@RequestMapping(value = "save")
	public String save(AttendenceType attendenceType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendenceType)){
			return form(attendenceType, model);
		}
		attendenceTypeService.save(attendenceType);
		addMessage(redirectAttributes, "保存出勤类型成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendenceType/?repage";
	}
	
	@RequiresPermissions("att:attendenceType:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendenceType attendenceType, RedirectAttributes redirectAttributes) {
		try{
		attendenceTypeService.delete(attendenceType);
		}catch(Exception e){
			addMessage(redirectAttributes, "出勤类型已经被关联，不能删除该出勤类型");
			return "redirect:"+Global.getAdminPath()+"/att/attendenceType/?repage";
		}
		addMessage(redirectAttributes, "删除出勤类型成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendenceType/?repage";
	}

}