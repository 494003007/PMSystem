/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * accumulation_fundEntity
 * @author czy
 * @version 2016-10-13
 */
public class AccumulationFund extends DataEntity<AccumulationFund> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// employee_id
	private Date payDate;		// pay_date
	private String companyPay;		// company_pay
	private String personalPay;		// personal_pay
	private Employee employee;
	private Date beginPayTime;		// 开始 pay_time
	private Date endPayTime;		// 结束 pay_time
	private String sysAreaId;
	private SysArea sysArea;

	public AccumulationFund() {
		super();
	}

	public AccumulationFund(String id){
		super(id);
	}

	@NotNull(message="employee_id不能为空")
	@ExcelField(title="员工id", align=3, sort=1)
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="付款时间", align=3, sort=20)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@Length(min=0, max=10, message="company_pay长度必须介于 0 和 10 之间")
	@ExcelField(title="公司支付", align=3, sort=25)
	public String getCompanyPay() {
		return companyPay;
	}

	public void setCompanyPay(String companyPay) {
		this.companyPay = companyPay;
	}
	
	@Length(min=0, max=10, message="personal_pay长度必须介于 0 和 10 之间")
	@ExcelField(title="个人支付", align=3, sort=30)
	public String getPersonalPay() {
		return personalPay;
	}

	public void setPersonalPay(String personalPay) {
		this.personalPay = personalPay;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(Date endPayTime) {
		this.endPayTime = endPayTime;
	}

	public Date getBeginPayTime() {
		return beginPayTime;
	}

	public void setBeginPayTime(Date beginPayTime) {
		this.beginPayTime = beginPayTime;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}
}