/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 员工考勤Controller
 * 
 * @author cdoublej
 * @version 2016-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/gmo/examineAttendanceDetail")
public class ExamineAttendanceDetailController extends BaseController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectAttendanceService projectAttendanceService;
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private AttendenceTypeService attendenceTypeService;
	@Autowired
	private AttendanceListDetailService attendanceListDetailService;
	@Autowired
	private AttendanceListService attendanceListService;
	@Autowired
	private SysAreaService sysAreaService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionTypeService positionTypeService;

	@ModelAttribute
	public AttendanceListDetail get(@RequestParam(required = false) String id) {
		AttendanceListDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = attendanceListDetailService.get(id);
		}
		if (entity == null) {
			entity = new AttendanceListDetail();
		}
		return entity;
	}
 

	@RequiresPermissions("gmo:examineAttendanceDetailList:view")
	@RequestMapping(value = { "list", "" })
	public String list(AttendanceListDetail attendanceListDetail, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
	// 要展示的数据
	Page<AttendanceListDetail> page = attendanceListDetailService.findExaminAttendance(new Page<AttendanceListDetail>(request, response),attendanceListDetail);
	model.addAttribute("page", page);
	return "modules/gmo/examineAttendanceDetailList";
	}

	@RequiresPermissions("gmo:examineAttendanceDetailList:view")
	@RequestMapping(value = "form")
	public String form(AttendanceListDetail attendanceListDetail, Model model) {
		model.addAttribute("attendanceListDetail", attendanceListDetail);
		return "modules/gmo/attendanceListDetailForm";
	}

	@RequiresPermissions("gmo:examineAttendanceDetailList:view")
	@RequestMapping(value = "select")
	public String select(HttpServletRequest request,HttpServletResponse response, Model model) throws ParseException {
 		String employeeID = request.getParameter("employeeid");
		 String projectAllowanceDetaliID = request.getParameter("projectAllowanceDetailId");//当月第几个十天

		String projectId = request.getParameter("projectId");// 所选项目id

		ProjectAttendance projectAttendance;
		if (projectAllowanceDetaliID == null)
			projectAttendance = new ProjectAttendance();
		else
			projectAttendance = projectAttendanceService.get(projectAllowanceDetaliID);
		// String employeeId = projectAttendance.getEmployeeId().toString();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Integer startdate1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[2]);
		String startdate = startdate1.toString();
		Integer enddate1 = Integer.parseInt(date.format(projectAttendance.getEndDate()).split("-")[2]);
		String enddate = enddate1.toString();
		String year = date.format(projectAttendance.getStartDate()).split("-")[0];
		Integer month1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[1]);
		String month = month1.toString();
		int effectiveDate = projectAttendance.getEffectiveDate();
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		model.addAttribute("effectiveDate", effectiveDate);
		String statr_date = date.format(projectAttendance.getStartDate());
		String end_date = date.format(projectAttendance.getEndDate());
		// int projectId = projectAttendance.getProjectId();
		ArrayList<AttendanceRecord> list = (ArrayList<AttendanceRecord>) attendanceRecordService.findAttendanceList(projectAllowanceDetaliID);
		model.addAttribute("AttendanceRecordList", list);// 考勤记录
		ArrayList<AttendenceType> attendenceTypelist = (ArrayList<AttendenceType>) attendenceTypeService.findList(null);
		model.addAttribute("attendenceTypelist", attendenceTypelist);// 日历考勤类型
		ArrayList<ProjectAttendance> coeifficientList = (ArrayList<ProjectAttendance>) projectAttendanceService.findCoeifficientList(Integer.parseInt(projectId), statr_date, end_date);
		model.addAttribute("coeifficientList", coeifficientList);// 考勤系数列表
		model.addAttribute("projectAttendance", projectAttendance);// 日历页面个人信息

		return "modules/gmo/attendanceSelect";
	}

	@RequiresPermissions("gmo:examineAttendanceDetailList:edit")
	@RequestMapping(value = "save")
	public String save(AttendanceListDetail attendanceListDetail, Model model,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceListDetail)) {
			return form(attendanceListDetail, model);
		}
		attendanceListDetailService.save(attendanceListDetail);
		addMessage(redirectAttributes, "保存员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/gmo/examineAttendanceDetailList/?repage";
	}

	@RequiresPermissions("gmo:examineAttendanceDetailList:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendanceListDetail attendanceListDetail,RedirectAttributes redirectAttributes) {
		attendanceListDetailService.delete(attendanceListDetail);
		addMessage(redirectAttributes, "删除员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/gmo/examineAttendanceDetailList/?repage";
	}

	@RequestMapping(value = "addList")
	// projectAttendanceID 十天一条记录。记录编号。employeeID员工编号 idsArr考勤
	// coeifficientList绩效系数 projectMoneyArray项目补贴
	public String addList(String[] addAttendanceArray, String projectId2,String getattendancedate1, String IDhidden,String projectAttendanceID, String employeeID, String[] idsArr,
			String[] coeifficientList, String[] projectMoneyArray,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes)throws ParseException {
		StringBuilder msg = new StringBuilder();
		ProjectAttendance projectAttendance = projectAttendanceService.get(projectAttendanceID);

		// 考勤
		for (int i = 0; i < idsArr.length; i += 2) {
			int projectid = projectAttendance.getProjectId();
			attendanceRecordService.addList(employeeID, idsArr[i],idsArr[i + 1], projectAttendanceID, projectid);
		}

		// 绩效系数
		for (int j = 0; j < coeifficientList.length; j += 2) {
			ProjectAttendance projectAttendance2 = projectAttendanceService.get(coeifficientList[j]);
			projectAttendance2.setPerformanceCoefficient(coeifficientList[j + 1]);
			projectAttendanceService.save(projectAttendance2);
		}

		if (projectMoneyArray.length != 0) {
			ProjectAttendance projectAttendance2 = projectAttendanceService.get(projectMoneyArray[0]);
			projectAttendance2.setProjectMoney(projectMoneyArray[1]);
			projectAttendanceService.save(projectAttendance2);
		}
		// 加班
		if (addAttendanceArray.length != 0) {
			ProjectAttendance projectAttendance2 = projectAttendanceService.get(addAttendanceArray[0]);
			projectAttendance2.setAddAttendance((addAttendanceArray[1]));
			projectAttendanceService.save(projectAttendance2);
		}
		if (idsArr.length > 0) {
			ArrayList list = (ArrayList) attendanceRecordService.findAttendanceList(projectAttendanceID);
			if (list.size() == projectAttendance.getDateSum()) {
				projectAttendance.setStatus("2");

			} else {
				projectAttendance.setStatus("1");
			}
			projectAttendanceService.save(projectAttendance);
		}
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		AttendanceListDetail attendanceListDetail = new AttendanceListDetail();
		attendanceListDetail.setEmployeeId(employeeID);
		attendanceListDetail.setId(IDhidden);
		attendanceListDetail.setAttendanceMonth(date.parse(getattendancedate1));
		attendanceListDetail.setProjectId(projectId2);

		attendanceListDetailService.attendanceupdate(attendanceListDetail);
		String allprojectId = request.getParameter("projectId");

		addMessage(redirectAttributes, "员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/gmo/examineAttendanceDetailList/list?projectId=" + allprojectId;
	}
}