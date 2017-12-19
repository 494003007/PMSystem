/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;
import com.thinkgem.jeesite.modules.gmo.entity.ExamineAttendance;
import com.thinkgem.jeesite.modules.gmo.service.ExamineAttendanceService;
import com.thinkgem.jeesite.modules.per.service.AttendanceStatusService;


/**
 * 考勤纠正Controller
 * @author cdoublej
 * @version 2016-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/examineAttendance")
public class ExamineAttendanceController extends BaseController {
	@Autowired
	private AttendenceTypeService attendenceTypeService;

	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private ExamineAttendanceService examineAttendanceService;
	
	@Autowired
	private AttendanceStatusService attendanceStatusService;

	@ModelAttribute
	public ExamineAttendance get(@RequestParam(required=false) String id) {
		ExamineAttendance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = examineAttendanceService.get(id);
		}
		if (entity == null){
			entity = new ExamineAttendance();
		}
		return entity;
	}
	
	@RequiresPermissions("gmo:attendanceExamine:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamineAttendance examineAttendance, HttpServletRequest request, HttpServletResponse response, Model model) {
		String employeeid  ="";
		String attendancedate = "";
		String attendanceListDetailid ="";
		
		try{
			attendanceListDetailid = request.getParameter("attendanceListDetailid");
			employeeid = request.getParameter("employeeid");
			attendancedate = request.getParameter("attendancedate");
			examineAttendance.setEmployeeId(Integer.parseInt(employeeid));
			examineAttendance.setTempleDate(attendancedate);
		}catch(Exception e){

		}
		model.addAttribute("employeeid", employeeid);
		model.addAttribute("attendancedate", attendancedate);
		model.addAttribute("attendanceListDetailid", attendanceListDetailid);		
		Page<ExamineAttendance> page = examineAttendanceService.findPage(new Page<ExamineAttendance>(request, response), examineAttendance); 
		model.addAttribute("page", page);
		return "modules/gmo/examineAttendanceList";
	}
}