/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;

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
public class ProjectAttendance extends DataEntity<ProjectAttendance> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// 员工编号
	private Integer projectId;		// 项目编号
	private Date startDate;		// 考勤开始时间
	private Date endDate;		// 考勤结束时间
	private Integer dateSum;		// 考勤天数
	private String status;		// 状态
	private Integer effectiveDate;//考勤有效期
	private Integer attendance_status_id;//纠正审核相关信息外键
	private String projectMoney;
	private String addAttendance;
	private String performanceCoefficient;
	private Employee employee;
	private SysArea sysArea;
	private String projectIds;
	private String employeeIds;
	private boolean isattendance;//是否超过有效期
	private String beforeAddAttendance;
	private String finAddAttendance;
	private String attendanceExamineStatus;
	
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

	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getAddAttendance() {
		return addAttendance;
	}

	public void setAddAttendance(String addAttendance) {
		this.addAttendance = addAttendance;
	}

	public boolean isIsattendance() {
		return isattendance;
	}

	public void setIsattendance(boolean isattendance) {
		this.isattendance = isattendance;
	}

	public Integer getAttendance_status_id() {
		return attendance_status_id;
	}

	public void setAttendance_status_id(Integer attendance_status_id) {
		this.attendance_status_id = attendance_status_id;
	}

	public Integer getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
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

	public ProjectAttendance() {
		super();
	}

	public ProjectAttendance(String id){
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