/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 考勤Entity
 * @author cdoublej
 * @version 2016-10-19
 */
public class CorrectAttendance extends DataEntity<CorrectAttendance> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// 员工编号
	private Integer projectId;		// 项目编号
	private Date startDate;		// 考勤开始时间
	private Date endDate;		// 考勤结束时间
	private Integer dateSum;		// 考勤天数
	private String status;		// 状态
	private String projectMoney; //项目补贴金额
	private String performanceCoefficient;//绩效系数
	private String templeDate;
	private Integer attendanceStatusId;
	private Employee employee;//员工信息
	private String addAttendance;
	private SysArea sysArea;//区域信息
	private Date thisDay;//当前日，用于查询比当前日小的记录
	private AttendanceStatus attendanceStatus;
	private String attendanceExamineStatus;
	private String beforeAddAttendance;
	private String finAddAttendance;
	
	public String getAttendanceExamineStatus() {
		return attendanceExamineStatus;
	}

	public void setAttendanceExamineStatus(String attendanceExamineStatus) {
		this.attendanceExamineStatus = attendanceExamineStatus;
	}

	public String getBeforeAddAttendance() {
		return beforeAddAttendance;
	}

	public void setBeforeAddAttendance(String beforeAddAttendance) {
		this.beforeAddAttendance = beforeAddAttendance;
	}

	public String getFinAddAttendance() {
		return finAddAttendance;
	}

	public void setFinAddAttendance(String finAddAttendance) {
		this.finAddAttendance = finAddAttendance;
	}

	public Date getThisDay() {
		return thisDay;
	}

	public void setThisDay(Date thisDay) {
		this.thisDay = thisDay;
	}

	public String getAddAttendance() {
		return addAttendance;
	}

	public void setAddAttendance(String addAttendance) {
		this.addAttendance = addAttendance;
	}

	public Integer getAttendanceStatusId() {
		return attendanceStatusId;
	}

	public String getTempleDate() {
		return templeDate;
	}

	public void setTempleDate(String templeDate) {
		this.templeDate = templeDate;
	}

	public void setAttendanceStatusId(Integer attendanceStatusId) {
		this.attendanceStatusId = attendanceStatusId;
	}

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public String getProjectMoney() {
		return projectMoney;
	}

	public void setProjectMoney(String projectMoney) {
		this.projectMoney = projectMoney;
	}

	public String getPerformanceCoefficient() {
		return performanceCoefficient;
	}

	public void setPerformanceCoefficient(String performanceCoefficient) {
		this.performanceCoefficient = performanceCoefficient;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public CorrectAttendance() {
		super();
	}

	public CorrectAttendance(String id){
		super(id);
	}

	@NotNull(message="员工编号不能为空")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@NotNull(message="项目编号不能为空")
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getDateSum() {
		return dateSum;
	}

	public void setDateSum(Integer dateSum) {
		this.dateSum = dateSum;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}