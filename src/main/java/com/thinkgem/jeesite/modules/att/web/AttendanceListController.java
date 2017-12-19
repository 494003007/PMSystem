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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;

/**
 * 考勤人员Controller
 * @author cdoublej
 * @version 2016-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/att/attendanceList")
public class AttendanceListController extends BaseController {

	@Autowired
	private AttendanceListService attendanceListService;
	
	@ModelAttribute
	public AttendanceList get(@RequestParam(required=false) String id) {
		AttendanceList entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceListService.get(id);
		}
		if (entity == null){
			entity = new AttendanceList();
		}
		return entity;
	}
	
	@RequiresPermissions("att:attendanceList:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceList attendanceList, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttendanceList> page = attendanceListService.findPage(new Page<AttendanceList>(request, response), attendanceList); 
		model.addAttribute("page", page);
		return "modules/att/attendanceListList";
	}

	@RequiresPermissions("att:attendanceList:view")
	@RequestMapping(value = "form")
	public String form(AttendanceList attendanceList, Model model) {
		model.addAttribute("attendanceList", attendanceList);
		return "modules/att/attendanceListForm";
	}

	@RequiresPermissions("att:attendanceList:edit")
	@RequestMapping(value = "save")
	public String save(AttendanceList attendanceList, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceList)){
			return form(attendanceList, model);
		}
		attendanceListService.save(attendanceList);
		addMessage(redirectAttributes, "保存考勤人员成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendanceList/?repage";
	}
	
	@RequiresPermissions("att:attendanceList:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendanceList attendanceList, RedirectAttributes redirectAttributes) {
		attendanceListService.delete(attendanceList);
		addMessage(redirectAttributes, "删除考勤人员成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendanceList/?repage";
	}

}