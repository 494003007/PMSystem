/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 单表生成Entity
 * @author ThinkGem
 * @version 2016-10-23
 */
public class SalaryStandardDetail extends DataEntity<SalaryStandardDetail> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// employee_id
	private String sysAreaName;		// sys_area_name
	private Double money;		// money
	private Date createTime;		// create_time
	private String createReason;		// create_reason

	private Employee employee;

	private SysArea sysArea;
	
	public SalaryStandardDetail() {
		super();
	}

	public SalaryStandardDetail(String id){
		super(id);
	}

	@NotNull(message="员工id不能为空")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=1, max=64, message="你还没有选择区域")
	public String getSysAreaName() {
		return sysAreaName;
	}

	public void setSysAreaName(String sysAreaName) {
		this.sysAreaName = sysAreaName;
	}

	@NotNull(message="调整金额不能为空")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="调整理由长度必须介于 0 和 255 之间")
	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}
}