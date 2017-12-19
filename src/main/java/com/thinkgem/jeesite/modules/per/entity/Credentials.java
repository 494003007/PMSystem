/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author ThinkGem
 * @version 2016-10-25
 */
public class Credentials extends DataEntity<Credentials> {
	
	private static final long serialVersionUID = 1L;
	private String employeeId;		// employee_id
	private String certificatesTypeId;		// certificates_type_id
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private String url;		// url
	private Date signDate;		// sign_date
	private Date examineDate;		// examine_date
	private CertificatesType certificatesType;
	private Employee employee;

	private String outOfDate;
	 
	public CertificatesType getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(CertificatesType certificatesType) {
		this.certificatesType = certificatesType;
	}

	public Credentials() {
		super();
	}

	public Credentials(String id){
		super(id);
	}

	@Length(min=1, max=11, message="employee_id长度必须介于 1 和 11 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=1, max=11, message="certificates_type_id长度必须介于 1 和 11 之间")
	public String getCertificatesTypeId() {
		return certificatesTypeId;
	}

	public void setCertificatesTypeId(String certificatesTypeId) {
		this.certificatesTypeId = certificatesTypeId;
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
	
	 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getOutOfDate() {
		return outOfDate;
	}

	public void setOutOfDate(String outOfDate) {
		this.outOfDate = outOfDate;
	}
}