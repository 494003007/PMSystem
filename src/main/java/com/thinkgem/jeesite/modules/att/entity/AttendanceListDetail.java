/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 员工考勤Entity
 * @author cdoublej
 * @version 2016-11-04
 */
public class AttendanceListDetail extends DataEntity<AttendanceListDetail> {
	
	private static final long serialVersionUID = 1L;
	private String employeeId;		// 员工编号
	private Date attendanceMonth;		// 考勤月份
	private Integer attendanceSum;		// 当月需考勤天数
	private Integer inAttendanceSum  = 0;		// 内勤天数
	private Integer outerAttendanceSum  = 0;		// 外勤天数
	private Integer illAttendanceSum  = 0;		// 病假天数
	private Integer thingAttendanceSum  = 0;		// 事假天数
	private Integer lackAttendanceSum  = 0;		// 旷工天数
	private Integer lateAttendanceSum  = 0;		// 迟到天数
	private Integer earlyAttendanceSum  = 0;		// 早退天数
	private Integer yearAttendanceSum = 0;		// 年休假天数
	private Integer addAttendanceSum = 0;
	private String projectId;
	private Employee employee;
	private String allProjectID;
	private int status;//考勤状态
	private String projectAllowanceDetailId;
	private String projectAllowanceDetailstatu;
	private Integer attendanceListID;
	private Integer finiscorrect;
	private Integer gmoisexamine;
	private Date thisMonth; //系统当前月份
	
	public Date getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(Date thisMonth) {
		this.thisMonth = thisMonth;
	}

	public Integer getFiniscorrect() {
		return finiscorrect;
	}

	public void setFiniscorrect(Integer finiscorrect) {
		this.finiscorrect = finiscorrect;
	}

	public Integer getGmoisexamine() {
		return gmoisexamine;
	}

	public void setGmoisexamine(Integer gmoisexamine) {
		this.gmoisexamine = gmoisexamine;
	}

	public Integer getAttendanceListID() {
		return attendanceListID;
	}

	public void setAttendanceListID(Integer attendanceListID) {
		this.attendanceListID = attendanceListID;
	}

	public String getProjectAllowanceDetailstatu() {
		return projectAllowanceDetailstatu;
	}

	public void setProjectAllowanceDetailstatu(String projectAllowanceDetailstatu) {
		this.projectAllowanceDetailstatu = projectAllowanceDetailstatu;
	}

	public String getProjectAllowanceDetailId() {
		return projectAllowanceDetailId;
	}

	public void setProjectAllowanceDetailId(String projectAllowanceDetailId) {
		this.projectAllowanceDetailId = projectAllowanceDetailId;
	}

	public Integer getAddAttendanceSum() {
		return addAttendanceSum;
	}

	public void setAddAttendanceSum(Integer addAttendanceSum) {
		this.addAttendanceSum = addAttendanceSum;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AttendanceListDetail() {
		super();
	}

	public AttendanceListDetail(String id){
		super(id);
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String allemployeeId) {
		this.employeeId = allemployeeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAttendanceMonth() {
		return attendanceMonth;
	}

	public void setAttendanceMonth(Date attendanceMonth) {
		this.attendanceMonth = attendanceMonth;
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
	
	public Integer getIllAttendanceSum() {
		return illAttendanceSum;
	}

	public void setIllAttendanceSum(Integer illAttendanceSum) {
		this.illAttendanceSum = illAttendanceSum;
	}
	
	public Integer getThingAttendanceSum() {
		return thingAttendanceSum;
	}

	public void setThingAttendanceSum(Integer thingAttendanceSum) {
		this.thingAttendanceSum = thingAttendanceSum;
	}
	
	public Integer getLackAttendanceSum() {
		return lackAttendanceSum;
	}

	public void setLackAttendanceSum(Integer lackAttendanceSum) {
		this.lackAttendanceSum = lackAttendanceSum;
	}
	
	public Integer getLateAttendanceSum() {
		return lateAttendanceSum;
	}

	public void setLateAttendanceSum(Integer lateAttendanceSum) {
		this.lateAttendanceSum = lateAttendanceSum;
	}
	
	public Integer getEarlyAttendanceSum() {
		return earlyAttendanceSum;
	}

	public void setEarlyAttendanceSum(Integer earlyAttendanceSum) {
		this.earlyAttendanceSum = earlyAttendanceSum;
	}
	
	public Integer getYearAttendanceSum() {
		return yearAttendanceSum;
	}

	public void setYearAttendanceSum(Integer yearAttendanceSum) {
		this.yearAttendanceSum = yearAttendanceSum;
	}
	
}