/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.att.dao.AttendanceListDao;
import com.thinkgem.jeesite.modules.att.dao.ProjectAttendanceDao;
import com.thinkgem.jeesite.modules.att.dao.SalaryDao;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.att.service.SalaryService;
import com.thinkgem.jeesite.modules.bus.web.ContractFileController;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.dao.ContractDao;
import com.thinkgem.jeesite.modules.pro.dao.ProjectDao;
import com.thinkgem.jeesite.modules.pro.dao.ProjectTypeDao;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.dao.DepartmentDao;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.InvoiceContentDao;
import com.thinkgem.jeesite.modules.sys.dao.InvoiceTypeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.SysAreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Notify;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 下拉框工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
 
 
	//fin 包下拉框
	public static List getFin_Selected(String type){
		List  list = Lists.newArrayList();
		if(type.equals("allowance_type")){ //财务常量下拉框
			AllowanceConstantDao dao = SpringContextHolder.getBean(AllowanceConstantDao.class);
			list =  dao.findList(null);
		}
		return list;
	}



	/*sys 包下拉列表*/
	public static List getSys_Selected(String type){
		List  list = Lists.newArrayList();
		if(type.equals("role_type")){ //角色类型
			RoleDao dao = SpringContextHolder.getBean(RoleDao.class);
			list =  dao.getAllList();
		}else if(type.equals("department_type")){ //部门
			DepartmentDao dao = SpringContextHolder.getBean(DepartmentDao.class);
			list =  dao.findAllList();
		}
		else if(type.equals("employee_type")){ //员工类别
			EmployeeTypeDao dao = SpringContextHolder.getBean(EmployeeTypeDao.class);
			list =  dao.findAllList();
		}else if(type.equals("area_type")){ //大区、区域
			SysAreaDao dao = SpringContextHolder.getBean(SysAreaDao.class);
			list =  dao.getAllList();
		}else if(type.equals("invoice_content")){ //开票内容
			InvoiceContentDao dao = SpringContextHolder.getBean(InvoiceContentDao.class);
			list =  dao.findList(null);
		}else if(type.equals("invoice_type")){ //发票类型
			InvoiceTypeDao dao = SpringContextHolder.getBean(InvoiceTypeDao.class);
			list =  dao.findList(null);
		}
		return list;
	}

	//per 包下拉框
	public static List getPer_Selected(String type){
		List  list = Lists.newArrayList();
		if(type.equals("quarters_type")){ //岗位下拉框
			PositionTypeDao dao = SpringContextHolder.getBean(PositionTypeDao.class);
			list =  dao.findList(null);
		}
		if(type.equals("credentialsList_type")){ //证件类型下拉框
			CertificatesTypeDao dao = SpringContextHolder.getBean(CertificatesTypeDao.class);
			list =  dao.findList(null);
		}
		return list;
	}

	
	//pro 包下拉框
	public static List getPro_Selected(String type){
		List  list = Lists.newArrayList();
		if(type.equals("projectType")){ //项目类型下拉框
			ProjectTypeDao dao = SpringContextHolder.getBean(ProjectTypeDao.class);
			list =  dao.findList(null);
		}
		 
		return list;
	}
	
	
	
	public static List<Notify> getNotifyList() throws ParseException {
//		
		List<Notify> notifyList = new ArrayList<Notify> ();
//			 
//		//项目考勤 工资基准"att:salary:*"
//		if(SecurityUtils.getSubject().isPermitted("att:attendanceListDetail:*")){
//			ProjectAttendanceDao attendanceDao = SpringContextHolder.getBean(ProjectAttendanceDao.class);	
//			AttendanceListDao attendanceListDao =  SpringContextHolder.getBean(AttendanceListDao.class);	
//			EmployeeDao employeeDao =  SpringContextHolder.getBean(EmployeeDao.class);	
//			SalaryDao salaryDao =  SpringContextHolder.getBean(SalaryDao.class);	
//			PositionTypeDao positionTypeDao =  SpringContextHolder.getBean(PositionTypeDao.class);	
//			ProjectDao projectDao =  SpringContextHolder.getBean(ProjectDao.class);	
//			SysAreaDao sysAreaDao = SpringContextHolder.getBean(SysAreaDao.class);	
//			User user = UserUtils.getUser();
//			String employeeId = user.getEmployeeId().toString();// 系统用户员工编号
//			Employee employee = employeeDao.get(employeeId);// 参数要改成自动获取！！！！
//			String sysId = employee.getSysArea().getId();// 大区/区域id
//			String positionName = positionTypeDao.get(employee.getQuarters().toString()).getName();// 区域名称
//			List<Project> projectlist = new  ArrayList<Project>();	 		 
// 
//			if (positionName.equals("项目经理")) {// 只是项目经理的话，只需要查询工号下面的项目			
//				Project project = new Project();
//				project.setEmployeeId(Integer.parseInt(employeeId));
//				projectlist = (ArrayList<Project>) projectDao.findList(project);
//			} else if (positionName.equals("区域经理")) {// 如果是 区域经理。得查询对应区域下的所有项目
// 
//				Project project = new Project();
//				project.setSysAreaId(sysId);
//				projectlist = (ArrayList<Project>) projectDao.findList(project);
//			 
//			} else if (positionName.equals("大区经理")) {// 如果是大区经理。得查询对应区域下的所有分区、项目
//			 	SysArea s = new SysArea();
//				s.setId(sysId);
//				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaDao.findAllchildren(s);
//				for (int i = 0; i < allChildrenList.size(); i++) {
//					SysArea sa = allChildrenList.get(i);
//					Project project = new Project();
//					project.setSysAreaId(sa.getId());
//					projectlist.addAll((ArrayList<Project>)projectDao.findList(project));	
//				}				
//			}
//			
//			String projectids = "";
//			String employeeids = "";
//			for (int j = 0; j < projectlist.size(); j++) {
//				projectids += projectlist.get(j).getId() + ",";
//			}
//			AttendanceList alist = new AttendanceList();
//			alist.setAllProjectID(projectids);
//			List<AttendanceList> alAttendanceList = attendanceListDao.findAllEmployee(alist);// 获取项目下的所有员工attendancelist
//			for (int i = 0; i < alAttendanceList.size(); i++)
//				employeeids += alAttendanceList.get(i).getEmployeeId().toString() + ",";// 获取全部员工 id的字符串
//			ProjectAttendance projectAttendance = new ProjectAttendance();
//			Date date = new Date();
//			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
//			projectAttendance.setEmployeeIds(employeeids);
//			projectAttendance.setProjectIds(projectids);
//			if(date.getDate() > 10 && date.getDate() < 21){
//				projectAttendance.setStartDate(s.parse(s.format(date)+"-01"));
//			}else if(date.getDate() > 0 && date.getDate() < 11){
//				Calendar calender =  Calendar.getInstance();
//				calender.setTime(date);
//				calender.add(Calendar.MONTH,-1);
//				projectAttendance.setStartDate(s.parse(s.format(calender.getTime())+"-21"));
//			}else{
//				projectAttendance.setStartDate(s.parse(s.format(date)+"-11"));
//			}
//			int count = attendanceDao.getAttendanceCount(projectAttendance);
//			Notify notify = new Notify();
//			notify.setCount(count);
//			notify.setUrl("/att/attendanceListDetail/index");
//			notify.setName("员工考勤");
//			notifyList.add(notify);
//			Salary salary = new Salary();
//			salary.setEmployeeids(employeeids);
//			salary.setProjectids(projectids);
//			Notify notify1 = new Notify();
//			int count1 = salaryDao.getEmptyCount(salary);
//			notify1.setCount(count1);
//			notify1.setUrl("/att/salary/index");
//			notify1.setName("员工薪资基准");
//			notifyList.add(notify1);
//		}
//		//getAttendanceCount
//		 
//		//合同归档
//		if(SecurityUtils.getSubject().isPermitted("bus:contractFile:*")){
//			Notify notify = new Notify();
//			ContractDao dao = SpringContextHolder.getBean(ContractDao.class);	
//			int count = dao.getContractFileCount();
//			notify.setCount(count);
//			notify.setUrl("/bus/contractFile");
//			notify.setName("合同归档");
//			notifyList.add(notify);
//		}
//		//工作量确认
//		if(SecurityUtils.getSubject().isPermitted("pro:workConfirm:*")){
//			
//		}
//		//发票登记
//		if(SecurityUtils.getSubject().isPermitted("bus:invoice:*")){
//			
//		}
		return notifyList;
	}
}
