/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 员工薪资基准Entity
 * @author cdoublej
 * @version 2016-12-03
 */
public class Salary extends DataEntity<Salary> {
	
	private static final long serialVersionUID = 1L;
	private String employeeId;		// 员工编号
	private String projectId;		// 项目编号
	private Double salarySubsidy;		// 当前工资
	private Double positionSubsidy;		// 岗位补贴
	private Double projectSubsidy;		// 项目补贴
	private Double lifeSubsidy;		// 生活补贴
	private Double totalSalary;		// 理论收入
	
	private String projectids;
	private String employeeids;
	private Employee employee;
	private String examineStatus;
	private String examineRemarks;
	
	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getExamineRemarks() {
		return examineRemarks;
	}

	public void setExamineRemarks(String examineRemarks) {
		this.examineRemarks = examineRemarks;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getProjectids() {
		return projectids;
	}

	public void setProjectids(String projectids) {
		this.projectids = projectids;
	}

	public String getEmployeeids() {
		return employeeids;
	}

	public void setEmployeeids(String employeeids) {
		this.employeeids = employeeids;
	}

	public Salary() {
		super();
	}

	public Salary(String id){
		super(id);
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public Double getSalarySubsidy() {
		return salarySubsidy;
	}

	public void setSalarySubsidy(Double salarySubsidy) {
		this.salarySubsidy = salarySubsidy;
	}
	
	public Double getPositionSubsidy() {
		return positionSubsidy;
	}

	public void setPositionSubsidy(Double positionSubsidy) {
		this.positionSubsidy = positionSubsidy;
	}
	
	public Double getProjectSubsidy() {
		return projectSubsidy;
	}

	public void setProjectSubsidy(Double projectSubsidy) {
		this.projectSubsidy = projectSubsidy;
	}
	
	public Double getLifeSubsidy() {
		return lifeSubsidy;
	}

	public void setLifeSubsidy(Double lifeSubsidy) {
		this.lifeSubsidy = lifeSubsidy;
	}
	
	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}
	
}