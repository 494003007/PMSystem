/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.att.dao.AttendanceListDao;
import com.thinkgem.jeesite.modules.att.dao.ProjectAttendanceDao;
import com.thinkgem.jeesite.modules.att.dao.SalaryDao;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.att.service.SalaryService;
import com.thinkgem.jeesite.modules.bus.service.InvoiceService;
import com.thinkgem.jeesite.modules.per.dao.EmployeeDao;
import com.thinkgem.jeesite.modules.per.dao.PositionTypeDao;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.dao.ContractDao;
import com.thinkgem.jeesite.modules.pro.dao.ProjectDao;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ContractConfirmService;
import com.thinkgem.jeesite.modules.pro.service.ContractService;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.dao.SysAreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;
import com.thinkgem.jeesite.modules.sys.entity.Notify;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CustomerUserService;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 通知菜单Controller
 * @author cdoublej
 * @version 2016-12-10
 */
@Controller
public class NotifyController extends BaseController {
	@Autowired
	SysAreaService sysAreaService;
	@Autowired
	AttendanceListDetailService attendanceListDetailService;
	@Autowired
	ContractConfirmService confirmService;
	@Autowired
	ContractService contractService;
	@Autowired
	ProjectAttendanceService attendanceService;
	@Autowired
	AttendanceListService attendanceListService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	SalaryService salaryService;
	@Autowired
	PositionTypeService positionTypeService;
	@Autowired
	ProjectService projectService;
	@Autowired
	SysAreaService areaService;
	@Autowired
	InvoiceService invoiceService;
	@RequestMapping(value ="${adminPath}/sys/notify",method= RequestMethod.POST)
	public @ResponseBody List<Notify> notify(HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {				
		List<Notify> notifyList = new ArrayList<Notify> ();		
		
		//发票登记
		if(SecurityUtils.getSubject().isPermitted("bus:invoiceRegister:*")){
			 
			int count = invoiceService.getInvoiceNotifyCount("1");
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/bus/invoiceRegister/register");
				notify.setName("发票登记");
				notifyList.add(notify);
			}
		}
		
		//回款登记
		if(SecurityUtils.getSubject().isPermitted("bus:invoiceRreturnRegister:*")){
			 
			int count = invoiceService.getInvoiceNotifyCount("2");
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/bus/invoiceRreturnRegister/list");
				notify.setName("回款登记");
				notifyList.add(notify);
			}
		}
		
		
		//项目考勤 工资基准"att:salary:*"
		if(SecurityUtils.getSubject().isPermitted("att:attendanceListDetail:*")){		 
			User user = UserUtils.getUser();
			String employeeId = user.getEmployeeId().toString();// 系统用户员工编号
			Employee employee = employeeService.get(employeeId);// 参数要改成自动获取！！！！
			String positionId = employee==null || employee.getQuarters().equals("")?"0":employee.getQuarters();
			String positionName = positionTypeService.get(positionId)==null?"":positionTypeService.get(positionId).getName();// 岗位名称
			List<Project> projectlist = new  ArrayList<Project>();	 		 
 
			if (positionName.equals("项目经理")) {// 只是项目经理的话，只需要查询工号下面的项目		
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
					projectlist.addAll((ArrayList<Project>) projectService.findList(project));
				}
			} else if (positionName.equals("区域经理")) {// 如果是 区域经理。得查询对应区域下的所有项目
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("spm_employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
					projectlist.addAll((ArrayList<Project>) projectService.findList(project));
				}
			} else if (positionName.equals("大区经理")) {// 如果是大区经理。得查询对应区域下的所有分区、项目
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("bpm_employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
					projectlist.addAll((ArrayList<Project>)projectService.findList(project));	
				}				
			}
			
			String projectids = "";
			String employeeids = "";
			for (int j = 0; j < projectlist.size(); j++) {
				projectids += projectlist.get(j).getId() + ",";
			}
			AttendanceList alist = new AttendanceList();
			alist.setAllProjectID(projectids);
			List<AttendanceList> alAttendanceList = attendanceListService.findAllEmployee(alist);// 获取项目下的所有员工attendancelist
			for (int i = 0; i < alAttendanceList.size(); i++)
				employeeids += alAttendanceList.get(i).getEmployeeId().toString() + ",";// 获取全部员工 id的字符串
			ProjectAttendance projectAttendance = new ProjectAttendance();
			Date date = new Date();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			projectAttendance.setEmployeeIds(employeeids);
			projectAttendance.setProjectIds(projectids);
			if(date.getDate() > 10 && date.getDate() < 21){
				projectAttendance.setStartDate(s.parse(s.format(date).substring(0, 7)+"-01"));
			}else if(date.getDate() > 0 && date.getDate() < 11){
				Calendar calender =  Calendar.getInstance();
				calender.setTime(date);
				calender.add(Calendar.MONTH,-1);
				String a = s.format(calender.getTime()).substring(0, 7)+"-21";
				projectAttendance.setStartDate(s.parse(s.format(calender.getTime()).substring(0, 7)+"-21"));
			}else{
				projectAttendance.setStartDate(s.parse(s.format(date).substring(0, 7)+"-11"));
			}
			int count = attendanceService.getAttendanceCount(projectAttendance);
			if(count > 0){
				
				Notify notify = new Notify();
				notify.setCount(count);
				notify.setUrl("/att/attendanceListDetail/index");
				notify.setName("员工考勤");
				notifyList.add(notify);
			}
			Salary salary = new Salary();
			salary.setEmployeeids(employeeids);
			salary.setProjectids(projectids);
		 
			int count1 = salaryService.getEmptyCount(salary);
			if(count1 > 0){
				Notify notify1 = new Notify();	
				notify1.setCount(count1);
				notify1.setUrl("/att/salary/index");
				notify1.setName("员工薪资基准");
				notifyList.add(notify1);
			}
		}
		//getAttendanceCount
		//工作量确认
		if(SecurityUtils.getSubject().isPermitted("pro:workConfirm:*")){
			Date date = new Date();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH,-1);
			String templeDate =  s.format(c.getTime()) +"-01";
			int count = confirmService.getConfirmCount(templeDate);
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/pro/workConfirm/projectList");
				notify.setName("工作量确认");
				notifyList.add(notify);
			}
			
		}
		//合同归档
		if(SecurityUtils.getSubject().isPermitted("bus:contractFile:*")){
			 
			int count = contractService.getContractFileCount();
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/bus/contractFile");
				notify.setName("合同归档");
				notifyList.add(notify);
			}
		}

	
		//入职审核
		if(SecurityUtils.getSubject().isPermitted("gmo:entryemployee:*")){
		 
			int count = employeeService.getExamineEntryemployeeCount();
			if(count > 0){
				Notify notify = new Notify();		
				notify.setCount(count);
				notify.setUrl("/gmo/examineemployee/examineentrylist");
				notify.setName("员工入职审核");
				notifyList.add(notify);
			}
		}

		//离职审核
		if(SecurityUtils.getSubject().isPermitted("gmo:quitemployee:*")){
		 
			int count = employeeService.getExamineQuitemployeeCount();
			if(count > 0){
				Notify notify = new Notify();		
				notify.setCount(count);
				notify.setUrl("/gmo/examineemployee/examinequitlist");
				notify.setName("员工离职审核");
				notifyList.add(notify);
			}
		}
		//项目审核
		if(SecurityUtils.getSubject().isPermitted("gmo:examineProject:*")){
		 
			int count = projectService.getExamineProjectCount();
			if(count > 0){
				Notify notify = new Notify();		
				notify.setCount(count);
				notify.setUrl("/gmo/examineProject");
				notify.setName("项目立项审核");
				notifyList.add(notify);
			}
		}
		//工作量确认审核
		if(SecurityUtils.getSubject().isPermitted("gmo:workConfirm:*")){
			
			 
			int count = confirmService.getExamineConfirmCount();
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/gmo/workConfirm/projectList");
				notify.setName("工作量确认审核");
				notifyList.add(notify);
			}
			
		}
		//考勤纠正审核
		if(SecurityUtils.getSubject().isPermitted("gmo:examineAttendanceDetailList:*")){		 
			int count = attendanceListDetailService.getExamineAttendanceCount();
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/gmo/examineAttendanceDetail/list");
				notify.setName("考勤纠正审核");
				notifyList.add(notify);
			}
			
		}
		//发票登记
		if(SecurityUtils.getSubject().isPermitted("bus:invoice:*")){
			
		}
		//工资基准
		if(SecurityUtils.getSubject().isPermitted("gmo:salary:")){
			int count = salaryService.getExamineSalayCount();
			if(count > 0){
				Notify notify = new Notify();	
				notify.setCount(count);
				notify.setUrl("/gmo/salary");
				notify.setName("薪资基准审核");
				notifyList.add(notify);
			}
			
		}
		return notifyList;	 
	 }
}