/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

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
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.gmo.entity.ExamineAttendance;
import com.thinkgem.jeesite.modules.gmo.service.ExamineAttendanceService;
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;
import com.thinkgem.jeesite.modules.per.service.AttendanceStatusService;

/**
 * 考勤Controller
 * @author cdoublej
 * @version 2016-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/attendanceRecord")
public class t_AttendanceRecordController extends BaseController {
	@Autowired
	private ProjectAttendanceService projectAttendanceService;
	@Autowired
	private AttendanceListDetailService attendanceListDetailService;
	@Autowired
	private ExamineAttendanceService examineAttendanceService;
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
	
	@RequiresPermissions("gmo:attendanceExamine:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceRecord attendanceRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		String attendanceListDetailid ="";
		String employeeid  ="";
		String attendancedate = "";
 
		try{
			attendanceListDetailid = request.getParameter("attendanceListDetailid");
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
		model.addAttribute("attendanceListDetailid",attendanceListDetailid);
		return "modules/gmo/attendanceRecordList";
	}
 
	@RequestMapping(value = "addexamine")
	//examineReason审核原因 examineList审核结果  examineList[i]考勤记录id   examineList[i+!]结果   examineList1不通过  2通过
	public String addexamine(String projectAttendanceid,String addattendancesum,String attendanceListDetailid,String employeeid,String attendancedate, String examineReasonstr,String[] examineList ,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {	

		for(int i = 0; i < examineList.length;i+=2){	
			AttendanceRecord attendanceRecord = attendanceRecordService.get(examineList[i]);
			attendanceRecord.setGmoIsExamine("2");
			attendanceRecord.setExamineDate(new Date());
			attendanceRecord.setExamine_status(Integer.parseInt(examineList[i + 1]));
			if(examineList[i + 1].equals("2"))//审核通过
				attendanceRecord.setAttendanceTypeId(attendanceRecord.getFinattendanceTypeId());
			attendanceRecordService.save(attendanceRecord);
		}
		
		if(addattendancesum.equals("2") || addattendancesum.equals("3")){//审核加班
			ProjectAttendance projectAttendance = projectAttendanceService.get(projectAttendanceid);
			if(addattendancesum.equals("2")){
				projectAttendance.setAttendanceExamineStatus("2");
				projectAttendance.setAddAttendance(projectAttendance.getFinAddAttendance());
			
			}else{
				projectAttendance.setAttendanceExamineStatus("3");
			}
			projectAttendanceService.save(projectAttendance);
		}
				
		//设置10天视图status 记录为已审核
		Integer examineStatus = attendanceRecordService.get(examineList[0]).getAttendanceStatusId();
		AttendanceStatus attendanceStatus = attendanceStatusService.get(examineStatus.toString());
		attendanceStatus.setExamineDate(new Date());
		attendanceStatus.setExamineReason(examineReasonstr);	
		attendanceStatus.setGmoIsExamine("2");
		attendanceStatusService.save(attendanceStatus);
		
		//设置一个月视图status为已审核
		ExamineAttendance examineAttendance = new ExamineAttendance();
		examineAttendance.setEmployeeId(Integer.parseInt(employeeid));
		examineAttendance.setTempleDate(attendancedate);
		examineAttendance.setGmoIsExamineStatus("1");//十天视图中是否存在1 未审核的数据
		List<ExamineAttendance> examineAttendanceLis = examineAttendanceService.findList(examineAttendance);
		if(examineAttendanceLis.size() == 0){//如果不存在，设置月视图状态为已审核
			AttendanceListDetail attendanceListDetail = attendanceListDetailService.get(attendanceListDetailid);
			attendanceListDetail.setGmoisexamine(2);
			attendanceListDetailService.save(attendanceListDetail);	
//			addMessage(redirectAttributes, "员工考勤审核成功");
//			return "redirect:"+Global.getAdminPath()+"/gmo/examineAttendanceDetail/list?employeeid="+employeeid+"&attendancedate="+attendancedate+"&attendanceListDetailid="+attendanceListDetailid;

		}
		
 	
		//更新当月考勤天数
		AttendanceListDetail attendanceListDetail = attendanceListDetailService.get(attendanceListDetailid);
		attendanceListDetailService.attendanceupdate(attendanceListDetail);
		
		addMessage(redirectAttributes, "员工考勤审核成功");
		return "redirect:"+Global.getAdminPath()+"/gmo/examineAttendance/list?attendanceListDetailid="+attendanceListDetailid+"&employeeid="+employeeid+"&attendancedate="+attendancedate;
	}
}