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
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;
import com.thinkgem.jeesite.modules.per.service.AttendanceStatusService;

/**
 * 考勤纠正Controller
 * @author cdoublej
 * @version 2016-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/per/attendanceStatus")
public class AttendanceStatusController extends BaseController {

	@Autowired
	private AttendanceStatusService attendanceStatusService;
	
	@ModelAttribute
	public AttendanceStatus get(@RequestParam(required=false) String id) {
		AttendanceStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceStatusService.get(id);
		}
		if (entity == null){
			entity = new AttendanceStatus();
		}
		return entity;
	}
	
	@RequiresPermissions("per:attendanceStatus:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceStatus attendanceStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttendanceStatus> page = attendanceStatusService.findPage(new Page<AttendanceStatus>(request, response), attendanceStatus); 
		model.addAttribute("page", page);
		return "modules/per/attendanceStatusList";
	}

	@RequiresPermissions("per:attendanceStatus:view")
	@RequestMapping(value = "form")
	public String form(AttendanceStatus attendanceStatus, Model model) {
		model.addAttribute("attendanceStatus", attendanceStatus);
		return "modules/per/attendanceStatusForm";
	}

	@RequiresPermissions("per:attendanceStatus:edit")
	@RequestMapping(value = "save")
	public String save(AttendanceStatus attendanceStatus, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceStatus)){
			return form(attendanceStatus, model);
		}
		attendanceStatusService.save(attendanceStatus);
		addMessage(redirectAttributes, "保存考勤纠正成功");
		return "redirect:"+Global.getAdminPath()+"/per/attendanceStatus/?repage";
	}
	
	@RequiresPermissions("per:attendanceStatus:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendanceStatus attendanceStatus, RedirectAttributes redirectAttributes) {
		attendanceStatusService.delete(attendanceStatus);
		addMessage(redirectAttributes, "删除考勤纠正成功");
		return "redirect:"+Global.getAdminPath()+"/per/attendanceStatus/?repage";
	}

}