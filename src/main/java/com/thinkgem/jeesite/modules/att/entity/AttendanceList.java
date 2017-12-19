/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 考勤人员Entity
 * @author cdoublej
 * @version 2016-11-03
 */
public class AttendanceList extends DataEntity<AttendanceList> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// 员工编号
	private Integer headEmployeeId;		// 项目经理编号
	private String projectId;		// 项目编号
 
	private Employee employee;
	private Salary salary; 
	private String allProjectID;
	
	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	public Employee getEmployee() {
		return employee;
	}

	public String getAllProjectID() {
		return allProjectID;
	}

	public void setAllProjectID(String allProjectID) {
		this.allProjectID = allProjectID;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AttendanceList() {
		super();
	}

	public AttendanceList(String id){
		super(id);
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	public Integer getHeadEmployeeId() {
		return headEmployeeId;
	}

	public void setHeadEmployeeId(Integer headEmployeeId) {
		this.headEmployeeId = headEmployeeId;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId2) {
		this.projectId = projectId2;
	}
	
	
}