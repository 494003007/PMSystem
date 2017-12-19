/**
  * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;
import com.thinkgem.jeesite.modules.per.entity.CorrectAttendance;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.AttendanceStatusService;
import com.thinkgem.jeesite.modules.per.service.CorrectAttendanceService;

/**
 * 考勤纠正Controller
 * @author cdoublej
 * @version 2016-10-19 
 */
@Controller
@RequestMapping(value = "${adminPath}/per/correctAttendance")
public class CorrectAttendanceController extends BaseController {
	@Autowired
	private AttendenceTypeService attendenceTypeService;
	@Autowired
	private AttendanceListDetailService attendanceListDetailService;
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private CorrectAttendanceService correctAttendanceService;
	
	@Autowired
	private AttendanceStatusService attendanceStatusService;

	@ModelAttribute
	public CorrectAttendance get(@RequestParam(required=false) String id) {
		CorrectAttendance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = correctAttendanceService.get(id);
		}
		if (entity == null){
			entity = new CorrectAttendance();
		}
		return entity;
	}
	
//	@RequiresPermissions("per:attendanceCorrect:view")
	@RequestMapping(value = {"list", ""})
	public String list(CorrectAttendance correctAttendance, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String employeeid  ="";
		String attendancedate = "";
		String attendanceListDetailid ="";
		try{
			attendanceListDetailid = request.getParameter("attendanceListDetailid");
			employeeid = request.getParameter("employeeid");
			attendancedate = request.getParameter("attendancedate");
			correctAttendance.setEmployeeId(Integer.parseInt(employeeid));
			correctAttendance.setTempleDate(attendancedate);
		}catch(Exception e){

		}	
		
		Date date = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String thisDay= sdf.format(date);
	 
		correctAttendance.setThisDay(sdf.parse(thisDay));
 
		model.addAttribute("employeeid", employeeid);
		model.addAttribute("attendancedate", attendancedate);
		model.addAttribute("attendanceListDetailid", attendanceListDetailid);
		Page<CorrectAttendance> page = correctAttendanceService.findPage(new Page<CorrectAttendance>(request, response), correctAttendance); 

		model.addAttribute("page", page);
		return "modules/per/correctAttendanceList";
	}

	@RequiresPermissions("per:attendanceCorrect:view")
	@RequestMapping(value = "form")
	public String form(CorrectAttendance correctAttendance, Model model) {
		model.addAttribute("correctAttendance", correctAttendance);
		return "modules/per/correctAttendanceForm";
	}

	@RequiresPermissions("per:attendanceCorrect:edit")
	@RequestMapping(value = "save")
	public String save(CorrectAttendance correctAttendance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, correctAttendance)){
			return form(correctAttendance, model);
		}
		correctAttendanceService.save(correctAttendance);
		addMessage(redirectAttributes, "员工考勤纠正成功");
		return "redirect:"+Global.getAdminPath()+"/per/correctAttendance/?repage";
	}
	

	
	@RequestMapping(value ="select")
	public String select(HttpServletRequest request, HttpServletResponse response,Model model) {
		String s = request.getParameter("id");
		CorrectAttendance correctAttendance = correctAttendanceService.get(s);
	//	String employeeId =  correctAttendance.getEmployeeId().toString();		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Integer startdate1 = Integer.parseInt(date.format(correctAttendance.getStartDate()).split("-")[2]);	
		String startdate = startdate1.toString();
		Integer enddate1 =Integer.parseInt(date.format(correctAttendance.getEndDate()).split("-")[2]);
		String enddate = enddate1.toString();
		String year = date.format(correctAttendance.getStartDate()).split("-")[0];
		Integer month1 = Integer.parseInt(date.format(correctAttendance.getStartDate()).split("-")[1]);
		String month = month1.toString();
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate",enddate);
		model.addAttribute("year", year);
		model.addAttribute("month",month);
		
		String statr_date = date.format(correctAttendance.getStartDate());
		String end_date = date.format(correctAttendance.getEndDate());
		int projectId = correctAttendance.getProjectId();
		ArrayList list =  (ArrayList) attendanceRecordService.findAttendanceList(s);
	
		ArrayList attendenceTypelist = (ArrayList) attendenceTypeService.findList(null);

		model.addAttribute("AttendanceRecordList",list);//考勤记录
		model.addAttribute("attendenceTypelist",attendenceTypelist);//日历考勤类型
		ArrayList coeifficientList =  (ArrayList) correctAttendanceService.findCoeifficientList(projectId, statr_date,end_date);
		model.addAttribute("coeifficientList",coeifficientList);//考勤系数列表
		model.addAttribute("correctAttendance",correctAttendance);//日历页面个人信息 
 		return "modules/per/correctSelect";
	}
	

	@RequestMapping(value = "addList")
	//correctAttendanceID 十天为一考勤记录。project_attendance_detail  主键
	public String addList(String attendanceListDetailid,String[] addAttendanceArray, String correctReason,String correctAttendanceID,String employeeID,String[] idsArr,String[] coeifficientList, String[] projectMoneyArray ,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {			 
	
		AttendanceListDetail attendanceListDetail = attendanceListDetailService.get(attendanceListDetailid);
		attendanceListDetail.setFiniscorrect(2); //设置fin——is——correct为已纠正，默认1
		attendanceListDetail.setGmoisexamine(1);//设置gmo_is_examine为未审核，默认1。有可能后来审核后变成2，这里恢复成1
		attendanceListDetailService.save(attendanceListDetail);
		String employeeid  ="";
		String attendancedate = "";
		try{		 
			employeeid = request.getParameter("employeeid");
			attendancedate = request.getParameter("attendancedate");
		}catch(Exception e){
		
		}		
	
		StringBuilder msg = new StringBuilder();
		CorrectAttendance correctAttendance = correctAttendanceService.get(correctAttendanceID);	 
		AttendanceStatus attendanceStatus = new AttendanceStatus(); 
		attendanceStatus.setCorrectDate(new Date());
		attendanceStatus.setCorrectReason(correctReason);
		attendanceStatusService.getPrimaryId(attendanceStatus);		
		int attendanceStatusId = Integer.parseInt(attendanceStatus.getId());
		correctAttendance.setAttendanceStatusId(attendanceStatusId);
		correctAttendanceService.save(correctAttendance);
		
		// 添加考勤记录 idsArr i 日期 ，年月日。i + 1考勤类型
		for (int i = 0; i < idsArr.length; i+=2) {
			int projectid = correctAttendance.getProjectId();
			attendanceRecordService.finaddList(attendanceStatusId,employeeID,idsArr[i],idsArr[i+1],correctAttendanceID,projectid);
		}	
		//加班

		if (addAttendanceArray.length != 0) {
			correctAttendanceService.updateAddAttendance(addAttendanceArray[0],addAttendanceArray[1]);
			/*correctAttendance.setFinAddAttendance(addAttendanceArray[1]);
			correctAttendanceService.save(correctAttendance);*/
		}
		
		// 修改绩效系数	
		
		for(int j = 0; j < coeifficientList.length; j+=2){			
			correctAttendanceService.updatePerformanceCoefficient(coeifficientList[j],coeifficientList[j + 1]);
		}	
		// 修改项目补贴金额 
		
		/*if(projectMoneyArray.length != 0){
			CorrectAttendance correctAttendance2 = correctAttendanceService.get(projectMoneyArray[0]);
			correctAttendance2.setProjectMoney(projectMoneyArray[1]);
			correctAttendanceService.save(correctAttendance2);
		}*/
		if(idsArr.length > 0){
		//1 未考勤  2 已考勤
		ArrayList list =  (ArrayList) attendanceRecordService.findAttendanceList(correctAttendanceID);
	 
				String status=null;	
				if(list.size() == correctAttendance.getDateSum()){
						status = "2";
					
					}else{
						status = "1";
					}
			correctAttendanceService.updateStatus(attendanceListDetailid,status);
		}
		addMessage(redirectAttributes, "员工考勤纠正成功");
		request.setAttribute("employeeid", employeeid);
	 
		return "redirect:"+Global.getAdminPath()+"/per/correctAttendance/list?attendanceListDetailid="+attendanceListDetailid+"&employeeid="+employeeid+"&attendancedate="+attendancedate;
	}
	 
}