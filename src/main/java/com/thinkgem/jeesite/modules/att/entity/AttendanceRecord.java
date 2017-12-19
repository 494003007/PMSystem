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
 * @version 2016-10-18
 */
public class AttendanceRecord extends DataEntity<AttendanceRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// 员工编号
	private Integer projectId;		// 项目编号
	private Date attendanceDate;		// 考勤时间
	private Integer attendanceTypeId;		// 考勤类型编号
	private String finIsCorrect;		// 财务是否纠正
	private Date correctDate;		// 纠正时间
	private String gmoIsExamine;		// 总经办是否审核
	private Date examineDate;		// 审核时间
	private Integer attendanceStatusId;//外键，attendace_status
	private Integer finattendanceTypeId;//纠正后的考勤类型
	private Integer before_attendance_type;//纠正前的考勤类型
	private String before_attendance_name;
	private Employee employee; 
	private SysArea sysArea;
	private AttendenceType attendenceType;
	private Integer examine_status;//审核状态
	private int project_allowance_detail_id;//外键
	 
	private String finattendanceTypeName;//纠正后的考勤名称

	public String getBefore_attendance_name() {
		return before_attendance_name;
	}


	public void setBefore_attendance_name(String before_attendance_name) {
		this.before_attendance_name = before_attendance_name;
	}


	public Integer getExamine_status() {
		return examine_status;
	}


	public void setExamine_status(Integer examine_status) {
		this.examine_status = examine_status;
	}


	public Integer getBefore_attendance_type() {
		return before_attendance_type;
	}


	public void setBefore_attendance_type(Integer before_attendance_type) {
		this.before_attendance_type = before_attendance_type;
	}


	public String getFinattendanceTypeName() {
		return finattendanceTypeName;
	}

 
	public void setFinattendanceTypeName(String finattendanceTypeName) {
		this.finattendanceTypeName = finattendanceTypeName;
	}

	public Integer getFinattendanceTypeId() {
		return finattendanceTypeId;
	}

	public void setFinattendanceTypeId(Integer finattendanceTypeId) {
		this.finattendanceTypeId = finattendanceTypeId;
	}

	public Integer getAttendanceStatusId() {
		return attendanceStatusId;
	}

	public void setAttendanceStatusId(Integer attendanceStatusId) {
		this.attendanceStatusId = attendanceStatusId;
	}

	public int getProject_allowance_detail_id() {
		return project_allowance_detail_id;
	}

	public void setProject_allowance_detail_id(int project_allowance_detail_id) {
		this.project_allowance_detail_id = project_allowance_detail_id;
	}

	public AttendenceType getAttendenceType() {
		return attendenceType;
	}

	public void setAttendenceType(AttendenceType attendenceType) {
		this.attendenceType = attendenceType;
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

	public AttendanceRecord() {
		super();
	}

	public AttendanceRecord(String id){
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
	public Date getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
	@NotNull(message="考勤类型编号不能为空")
	public Integer getAttendanceTypeId() {
		return attendanceTypeId;
	}

	public void setAttendanceTypeId(Integer attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	
 
	public String getFinIsCorrect() {
		return finIsCorrect;
	}

	public void setFinIsCorrect(String finIsCorrect) {
		this.finIsCorrect = finIsCorrect;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCorrectDate() {
		return correctDate;
	}

	public void setCorrectDate(Date correctDate) {
		this.correctDate = correctDate;
	}
	
 
	public String getGmoIsExamine() {
		return gmoIsExamine;
	}

	public void setGmoIsExamine(String gmoIsExamine) {
		this.gmoIsExamine = gmoIsExamine;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}
	
}