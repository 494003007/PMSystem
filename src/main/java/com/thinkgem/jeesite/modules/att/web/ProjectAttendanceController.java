/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import groovyjarjarantlr.collections.List;

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
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 考勤Controller
 * @author cdoublej
 * @version 2016-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/att/projectAttendance")
public class ProjectAttendanceController extends BaseController {
	@Autowired
	AttendenceTypeService attendenceTypeService;

	@Autowired
	AttendanceRecordService attendanceRecordService;
	@Autowired
	private ProjectAttendanceService projectAttendanceService;
	
	@ModelAttribute
	public ProjectAttendance get(@RequestParam(required=false) String id) {
		ProjectAttendance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectAttendanceService.get(id);
		}
		if (entity == null){
			entity = new ProjectAttendance();
		}
		return entity;
	}
	
	@RequiresPermissions("att:projectAttendance:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProjectAttendance projectAttendance, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<ProjectAttendance> page = projectAttendanceService.findPage(new Page<ProjectAttendance>(request, response), projectAttendance); 
		model.addAttribute("page", page);
		return "modules/att/projectAttendanceList";
	}

	@RequiresPermissions("att:projectAttendance:view")
	@RequestMapping(value = "form")
	public String form(ProjectAttendance projectAttendance, Model model) {
		model.addAttribute("projectAttendance", projectAttendance);
		return "modules/att/projectAttendanceForm";
	}

	@RequiresPermissions("att:projectAttendance:edit")
	@RequestMapping(value = "save")
	public String save(ProjectAttendance projectAttendance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectAttendance)){
			return form(projectAttendance, model);
		}
		projectAttendanceService.save(projectAttendance);
		addMessage(redirectAttributes, "保存考勤成功");
		return "redirect:"+Global.getAdminPath()+"/att/projectAttendance/?repage";
	}
	
	@RequiresPermissions("att:projectAttendance:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectAttendance projectAttendance, RedirectAttributes redirectAttributes) {
		projectAttendanceService.delete(projectAttendance);
		addMessage(redirectAttributes, "删除考勤成功");
		return "redirect:"+Global.getAdminPath()+"/att/projectAttendance/?repage";
	}
	
	
	@RequestMapping(value ="select")
	public String select(HttpServletRequest request, HttpServletResponse response,Model model) {
		String s = request.getParameter("id");
		ProjectAttendance projectAttendance = projectAttendanceService.get(s);
	//	String employeeId =  projectAttendance.getEmployeeId().toString();		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Integer startdate1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[2]);	
		String startdate = startdate1.toString();
		Integer enddate1 =Integer.parseInt(date.format(projectAttendance.getEndDate()).split("-")[2]);
		String enddate = enddate1.toString();
		String year = date.format(projectAttendance.getStartDate()).split("-")[0];
		Integer month1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[1]);
		String month = month1.toString();
		int effectiveDate = projectAttendance.getEffectiveDate();

		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate",enddate);
		model.addAttribute("year", year);
		model.addAttribute("month",month);

		model.addAttribute("effectiveDate",effectiveDate);
		String statr_date = date.format(projectAttendance.getStartDate());
		String end_date = date.format(projectAttendance.getEndDate());
		int projectId = projectAttendance.getProjectId();
		ArrayList list =  (ArrayList) attendanceRecordService.findAttendanceList(s);
		model.addAttribute("AttendanceRecordList",list);//考勤记录
		ArrayList attendenceTypelist = (ArrayList) attendenceTypeService.findList(null);
		model.addAttribute("attendenceTypelist",attendenceTypelist);//日历考勤类型
		ArrayList coeifficientList =  (ArrayList) projectAttendanceService.findCoeifficientList(projectId, statr_date,end_date);
		model.addAttribute("coeifficientList",coeifficientList);//考勤系数列表
		model.addAttribute("projectAttendance",projectAttendance);//日历页面个人信息 
 		return "modules/att/attendanceSelect";
	}
	
	@RequestMapping(value = "addList")
	//projectAttendanceID 十天一条记录。记录编号。employeeID员工编号  idsArr考勤 coeifficientList绩效系数   projectMoneyArray项目补贴
	public String addList(String projectAttendanceID,String employeeID,String[] idsArr,String[] coeifficientList, String[] projectMoneyArray ,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {			 
		StringBuilder msg = new StringBuilder();
		ProjectAttendance projectAttendance = projectAttendanceService.get(projectAttendanceID);

		for (int i = 0; i < idsArr.length; i+=2) {
			int projectid = projectAttendance.getProjectId();
			attendanceRecordService.addList(employeeID,idsArr[i],idsArr[i+1],projectAttendanceID,projectid);
		}
		
		for(int j = 0; j < coeifficientList.length; j+=2){			
			projectAttendanceService.updatePerformanceCoefficient(coeifficientList[j],coeifficientList[j + 1]);
		}	
		
		/*if(projectMoneyArray.length!= 0){
			ProjectAttendance projectAttendance2 = projectAttendanceService.get(projectMoneyArray[0]);
			projectAttendance2.setProjectMoney(projectMoneyArray[1]);
			projectAttendanceService.save(projectAttendance2);
		}*/
	
		
		if(idsArr.length > 0){
		ArrayList list =  (ArrayList) attendanceRecordService.findAttendanceList(projectAttendanceID);
   		String status=null;	
		if(list.size() == projectAttendance.getDateSum()){
				status = "2";
			
			}else{
				status = "1";
			}
   			projectAttendanceService.updateStatus(projectAttendanceID,status);
		}
		
		addMessage(redirectAttributes, "员工考勤成功");
		return "redirect:"+Global.getAdminPath()+"/att/projectAttendance/?repage";
	}
	 
}