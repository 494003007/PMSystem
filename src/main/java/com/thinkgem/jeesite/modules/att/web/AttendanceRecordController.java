/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;
import com.thinkgem.jeesite.modules.per.service.AttendanceStatusService;

/**
 * 考勤Controller
 * @author cdoublej
 * @version 2016-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/att/attendanceRecord")
public class AttendanceRecordController extends BaseController {
	@Autowired
	private ProjectAttendanceService projectAttendanceService;
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	
	@Autowired
	private AttendanceStatusService attendanceStatusService;
	@ModelAttribute
	public AttendanceRecord get(@RequestParam(required=false) String id) {
		AttendanceRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceRecordService.get(id);
		}
		if (entity == null){
			entity = new AttendanceRecord();
		}
		return entity;
	}
	
//	@RequiresPermissions("att:attendanceRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceRecord attendanceRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		String employeeid  ="";
		String attendancedate = "";
		try{
		 
			employeeid = request.getParameter("employeeid");
			attendancedate = request.getParameter("attendancedate");
		 
		}catch(Exception e){

		}	
		model.addAttribute("employeeid", employeeid);
		model.addAttribute("attendancedate", attendancedate);
	
		
		String project_allowance_detail_id = request.getParameter("projectAttendanceid");
	 
		//AttendanceRecord 获取考勤记录
		ArrayList<AttendanceRecord> attendanceRecordlist2 = (ArrayList<AttendanceRecord> ) attendanceRecordService.findAttendanceList(project_allowance_detail_id);
		List<AttendanceRecord> attendanceRecordlist = new  ArrayList<AttendanceRecord>();
		ProjectAttendance projectAttendance = projectAttendanceService.get(project_allowance_detail_id);//获取project_attendance_detail对象
		Date startdate = projectAttendance.getStartDate();
		Date enddate = projectAttendance.getEndDate();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();		
		c.setTime(startdate);
		
	//	填充考勤详细页面，把未考勤的也给填充进去
		int i = 0,j = 0;
		String a = s.format(startdate);
		String b = s.format(enddate);
		while(j == 0){
			if(a.equals(b))
				j++;
			AttendanceRecord attendanceRecord2 = new AttendanceRecord();
			if(i < attendanceRecordlist2.size()){			 
				attendanceRecord2 = attendanceRecordlist2.get(i);
				String attendanceRecordDate = s.format(attendanceRecord2.getAttendanceDate());
				if(!a.equals(attendanceRecordDate)){ 
					attendanceRecord2 = new AttendanceRecord();
					attendanceRecord2.setAttendanceDate(c.getTime());
					attendanceRecordlist.add(attendanceRecord2);
				}else{
					attendanceRecordlist.add(attendanceRecord2);
					i++;
				} 
			}else{
				attendanceRecord2.setAttendanceDate(c.getTime());
				attendanceRecordlist.add(attendanceRecord2);
			}
			c.add(Calendar.DATE, 1);
			a = s.format(c.getTime());
		 
		}
		
		
		String attendance_status_id;
		try{
			attendance_status_id = projectAttendance.getAttendance_status_id().toString(); //这个值有可能没有
		}catch(Exception w){
			attendance_status_id = null;
		}
		AttendanceStatus attendanceStatus;
		if(attendance_status_id == null || attendance_status_id =="")
			attendanceStatus = new AttendanceStatus();
		else
			attendanceStatus = attendanceStatusService.get(attendance_status_id);
		model.addAttribute("attendanceStatus",attendanceStatus);
		//	Page<AttendanceRecord> page = attendanceRecordService.findPage(new Page<AttendanceRecord>(request, response), attendanceRecord); 
		model.addAttribute("attendanceRecordlist", attendanceRecordlist);
		model.addAttribute("projectAttendance",projectAttendance);
		model.addAttribute("projectAttendanceid",project_allowance_detail_id);
		return "modules/att/attendanceRecordList";
	}

	@RequiresPermissions("att:attendanceRecord:view")
	@RequestMapping(value = "form")
	public String form(AttendanceRecord attendanceRecord, Model model) {
		model.addAttribute("attendanceRecord", attendanceRecord);
		return "modules/att/attendanceRecordForm";
	}

	@RequiresPermissions("att:attendanceRecord:edit")
	@RequestMapping(value = "save")
	public String save(AttendanceRecord attendanceRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceRecord)){
			return form(attendanceRecord, model);
		}
		attendanceRecordService.save(attendanceRecord);
		addMessage(redirectAttributes, "保存考勤成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendanceRecord/?repage";
	}
	
	@RequiresPermissions("att:attendanceRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendanceRecord attendanceRecord, RedirectAttributes redirectAttributes) {
		attendanceRecordService.delete(attendanceRecord);
		addMessage(redirectAttributes, "删除考勤成功");
		return "redirect:"+Global.getAdminPath()+"/att/attendanceRecord/?repage";
	}

}