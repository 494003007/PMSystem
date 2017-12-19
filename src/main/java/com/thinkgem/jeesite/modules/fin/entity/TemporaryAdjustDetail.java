/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author ThinkGem
 * @version 2016-10-18
 */
public class TemporaryAdjustDetail extends DataEntity<TemporaryAdjustDetail> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// employee_id
	private String attendanceListDetailId;		// sys_area_name
	private Double money;		// money
	private Date createTime;		// create_time
	private String createReason;		// create_reason

	private Employee employee;
 

	public TemporaryAdjustDetail() {
		super();
	}

	public TemporaryAdjustDetail(String id){
		super(id);
	}

	@NotNull(message="员工id不能为空")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
 
	public String getAttendanceListDetailId() {
		return attendanceListDetailId;
	}

	public void setAttendanceListDetailId(String attendanceListDetailId) {
		this.attendanceListDetailId = attendanceListDetailId;
	}

	@NotNull(message="金额不能为空")
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
 
}