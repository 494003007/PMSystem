/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;

/**
 * 工资报表Entity
 * @author cdoublej
 * @version 2016-10-26
 */
public class AttendanceReport extends DataEntity<AttendanceReport> {
	DecimalFormat d = new DecimalFormat("###,##0.00");	
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
	private String employeeId;		// 员工编号
	private Date attendanceMonth;		// 考勤月份
	private String month;
	private Integer attendanceSum;		// 当月需考勤天数
	private Integer inAttendanceSum ;		// 内勤天数
	private Integer outerAttendanceSum  ;		// 外勤天数
	private Integer illAttendanceSum  ;		// 病假天数
	private Integer thingAttendanceSum  ;		// 事假天数
	private Integer lackAttendanceSum  ;		// 旷工天数
	private Integer lateAttendanceSum  ;		// 迟到天数
	private Integer earlyAttendanceSum  ;		// 早退天数
	private Integer yearAttendanceSum ;		// 年休假天数
	private Integer addAttendanceSum ;
	private String projectId;
	private String performanceCoefficient;//绩效系数
	private String allProjectID;
	private int status;						//考勤状态
	private String projectAllowanceDetailId;
	private String projectAllowanceDetailstatu;
	private Integer attendanceListID;

	private Integer departmentId;
	private String sysAreaId;
	private String employeeName;
	private String employeeTypeName;
	private String sysAreaName;
	private String departmentName;
	private String projectName;
	private String lifeSubsidy;
	private String positionSubsidy;
	private String salarySubsidy;
	private String projectSubsidy;
	private String totalSalary;
	private Integer attendanceSum1;
	private Integer projectSum;
	private Double totalSubsidy;
	private Integer countOnLine;
	
	@ExcelField(title="分身", align=2, sort=55)
	public Integer getCountOnLine() {
		return countOnLine;
	}
	public void setCountOnLine(Integer countOnLine) {
		this.countOnLine = countOnLine;
	}
	public Double getTotalSubsidy() {
		return Double.parseDouble(lifeSubsidy==null || lifeSubsidy.equals("")?"0":lifeSubsidy)+  Double.parseDouble(salarySubsidy==null || salarySubsidy.equals("")?"0":salarySubsidy) + Double.parseDouble(projectSubsidy==null || projectSubsidy.equals("")?"0":projectSubsidy) + Double.parseDouble(positionSubsidy==null || positionSubsidy.equals("")?"0":positionSubsidy);
	}
	public void setTotalSubsidy(Double totalSubsidy) {
		this.totalSubsidy = totalSubsidy;
	}
	@ExcelField(title="考勤总天数", align=2, sort=150)
	public Integer getAttendanceSum1() {
		return addAttendanceSum + lateAttendanceSum + yearAttendanceSum + outerAttendanceSum + earlyAttendanceSum;
	}
	public void setAttendanceSum1(Integer attendanceSum1) {
		this.attendanceSum1 = attendanceSum1;
	}
	@ExcelField(title="正常出勤天数", align=2, sort=160)
	public Integer getProjectSum() {
		return addAttendanceSum + lateAttendanceSum  + outerAttendanceSum + earlyAttendanceSum;
	}
	public void setProjectSum(Integer projectSum) {
		this.projectSum = projectSum;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	@ExcelField(title="姓名", align=2, sort=30)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@ExcelField(title="员工类型", align=2, sort=40)
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	@ExcelField(title="所属区域", align=2, sort=10)
	public String getSysAreaName() {
		return sysAreaName;
	}
	public void setSysAreaName(String sysAreaName) {
		this.sysAreaName = sysAreaName;
	}
	@ExcelField(title="所属部门", align=2, sort=50)
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@ExcelField(title="   所属项目    ", align=2, sort=20)
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@ExcelField(title="生活补贴", align=3, sort=80)
	public String getLifeSubsidy() {
		return d.format(Double.parseDouble(lifeSubsidy==null?"0":lifeSubsidy));
	 
	}
	public void setLifeSubsidy(String lifeSubsidy) {
		this.lifeSubsidy = lifeSubsidy;
	}
	@ExcelField(title="岗位补贴", align=3, sort=90)
	public String getPositionSubsidy() {
		return d.format(Double.parseDouble(positionSubsidy==null?"0":positionSubsidy));
	 
	}
	public void setPositionSubsidy(String positionSubsidy) {
		this.positionSubsidy = positionSubsidy;
	}
	@ExcelField(title="薪资基准", align=3, sort=70)
	public String getSalarySubsidy() {
		return d.format(Double.parseDouble(salarySubsidy==null?"0":salarySubsidy));
	 
	}
	public void setSalarySubsidy(String salarySubsidy) {
		this.salarySubsidy = salarySubsidy;
	}
	@ExcelField(title="项目补贴", align=3, sort=80)
	public String getProjectSubsidy() {
		return d.format(Double.parseDouble(projectSubsidy==null?"0":projectSubsidy));
 
	}
	public void setProjectSubsidy(String projectSubsidy) {
		this.projectSubsidy = projectSubsidy;
	}
	public String getTotalSalary() {
		return totalSalary;
	}
	public void setTotalSalary(String totalSalary) {
		this.totalSalary = totalSalary;
	}
	private Date thisMonth; //系统当前月份

	@ExcelField(title="绩效系数", align=2, sort=100)
	public String getPerformanceCoefficient() {
		 
		return d.format(Double.parseDouble(performanceCoefficient==null?"0":performanceCoefficient));
	}
	public void setPerformanceCoefficient(String performanceCoefficient) {
		this.performanceCoefficient = performanceCoefficient;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public Date getAttendanceMonth() {
		 
		return attendanceMonth;
	}
	public void setAttendanceMonth(Date attendanceMonth) {
		this.attendanceMonth = attendanceMonth;
	}
	@ExcelField(title="考勤月份", align=2, sort=60)
	public String getMonth() {
		return s.format(attendanceMonth);
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
	public Integer getAttendanceSum() {
		return attendanceSum;
	}
	public void setAttendanceSum(Integer attendanceSum) {
		this.attendanceSum = attendanceSum;
	}
	public Integer getInAttendanceSum() {
		return inAttendanceSum;
	}
	public void setInAttendanceSum(Integer inAttendanceSum) {
		this.inAttendanceSum = inAttendanceSum;
	}
	public Integer getOuterAttendanceSum() {
		return outerAttendanceSum;
	}
	public void setOuterAttendanceSum(Integer outerAttendanceSum) {
		this.outerAttendanceSum = outerAttendanceSum;
	}
	@ExcelField(title="病假(天)", align=2, sort=180)
	public Integer getIllAttendanceSum() {
		return illAttendanceSum;
	}
	public void setIllAttendanceSum(Integer illAttendanceSum) {
		this.illAttendanceSum = illAttendanceSum;
	}
	@ExcelField(title="事假(天)", align=2, sort=200)
	public Integer getThingAttendanceSum() {
		return thingAttendanceSum;
	}
	public void setThingAttendanceSum(Integer thingAttendanceSum) {
		this.thingAttendanceSum = thingAttendanceSum;
	}
	@ExcelField(title="旷工(天)", align=2, sort=220)
	public Integer getLackAttendanceSum() {
		return lackAttendanceSum;
	}
	public void setLackAttendanceSum(Integer lackAttendanceSum) {
		this.lackAttendanceSum = lackAttendanceSum;
	}
	@ExcelField(title="迟到(天)", align=2, sort=230)
	public Integer getLateAttendanceSum() {
		return lateAttendanceSum;
	}
	public void setLateAttendanceSum(Integer lateAttendanceSum) {
		this.lateAttendanceSum = lateAttendanceSum;
	}
	@ExcelField(title="早退(天)", align=2, sort=240)
	public Integer getEarlyAttendanceSum() {
		return earlyAttendanceSum;
	}
	
	public void setEarlyAttendanceSum(Integer earlyAttendanceSum) {
		this.earlyAttendanceSum = earlyAttendanceSum;
	}
	@ExcelField(title="年休假(天)", align=2, sort=250)
	public Integer getYearAttendanceSum() {
		return yearAttendanceSum;
	}
	public void setYearAttendanceSum(Integer yearAttendanceSum) {
		this.yearAttendanceSum = yearAttendanceSum;
	}
	@ExcelField(title="加班(天)", align=2, sort=260)
	public Integer getAddAttendanceSum() {
		return addAttendanceSum;
	}
	public void setAddAttendanceSum(Integer addAttendanceSum) {
		this.addAttendanceSum = addAttendanceSum;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getAllProjectID() {
		return allProjectID;
	}
	public void setAllProjectID(String allProjectID) {
		this.allProjectID = allProjectID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getProjectAllowanceDetailId() {
		return projectAllowanceDetailId;
	}
	public void setProjectAllowanceDetailId(String projectAllowanceDetailId) {
		this.projectAllowanceDetailId = projectAllowanceDetailId;
	}
	public String getProjectAllowanceDetailstatu() {
		return projectAllowanceDetailstatu;
	}
	public void setProjectAllowanceDetailstatu(String projectAllowanceDetailstatu) {
		this.projectAllowanceDetailstatu = projectAllowanceDetailstatu;
	}
	public Integer getAttendanceListID() {
		return attendanceListID;
	}
	public void setAttendanceListID(Integer attendanceListID) {
		this.attendanceListID = attendanceListID;
	}
 
	public Date getThisMonth() {
		return thisMonth;
	}
	public void setThisMonth(Date thisMonth) {
		this.thisMonth = thisMonth;
	}
	
	
}