/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.per.entity.AllowanceConstant;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 补贴名单Entity
 * @author cdoublej
 * @version 2016-10-12
 */
public class AllowanceList extends DataEntity<AllowanceList> {

	private static final long serialVersionUID = 1L;
	private Integer allowanceConstantId;		// 财务常量id
	private Integer employeeId;		// 员工id
	private String identityCard;		// 身份证
	private Date startDate;		// 补贴开始时间
	private Date endDate;		// 补贴结束时间
	private AllowanceConstant allowanceConstant;//补贴常量
 
	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AllowanceList() {
		super();
	}

	public AllowanceList(String id){
		super(id);
	}

	@NotNull(message="财务常量id不能为空")
	public Integer getAllowanceConstantId() {
		return allowanceConstantId;
	}

	public void setAllowanceConstantId(Integer allowanceConstantId) {
		this.allowanceConstantId = allowanceConstantId;
	}

	@NotNull(message="员工id不能为空")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Length(min=0, max=30, message="身份证长度必须介于 0 和 30 之间")
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
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
	public AllowanceConstant getAllowanceConstant() {
		return allowanceConstant;
	}

	public void setAllowanceConstant(AllowanceConstant allowanceConstant) {
		this.allowanceConstant = allowanceConstant;
	}

}