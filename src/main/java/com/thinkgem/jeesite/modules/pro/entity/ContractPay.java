/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 付款Entity
 * @author cdoublej
 * @version 2016-11-23
 */
public class ContractPay extends DataEntity<ContractPay> {
	
	private static final long serialVersionUID = 1L;
	private Date payDate;			// 付款时间
	private Date appointePayDate;	// 约定付款时间
	private Integer count;			// 数量
	private Double payAmount;		// 付款金额
	private String payUnit;			// 付款单位
	private Double payPercent;		// 付款比例
	private Integer contractId;		// 所属合同编号
	private String inConfirmStatus;	// 内部确认状态
	private String inCheckRemarks;	// 内部验收说明
	private String outConfirmStatus;// 外部确认状态
	private double inConfirmAmount;//内部确认金额
	
	public String getInConfirmStatus() {
		return inConfirmStatus;
	}

	public void setInConfirmStatus(String inConfirmStatus) {
		this.inConfirmStatus = inConfirmStatus;
	}

	public String getInCheckRemarks() {
		return inCheckRemarks;
	}

	public void setInCheckRemarks(String inCheckRemarks) {
		this.inCheckRemarks = inCheckRemarks;
	}

	public String getOutConfirmStatus() {
		return outConfirmStatus;
	}

	public void setOutConfirmStatus(String outConfirmStatus) {
		this.outConfirmStatus = outConfirmStatus;
	}

	public double getInConfirmAmount() {
		return inConfirmAmount;
	}

	public void setInConfirmAmount(double inConfirmAmount) {
		this.inConfirmAmount = inConfirmAmount;
	}

	public ContractPay() {
		super();
	}

	public ContractPay(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAppointePayDate() {
		return appointePayDate;
	}

	public void setAppointePayDate(Date appointePayDate) {
		this.appointePayDate = appointePayDate;
	}
	
	 
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	@Length(min=0, max=20, message="付款单位长度必须介于 0 和 20 之间")
	public String getPayUnit() {
		return payUnit;
	}

	public void setPayUnit(String payUnit) {
		this.payUnit = payUnit;
	}
	
	public Double getPayPercent() {
		return payPercent;
	}

	public void setPayPercent(Double payPercent) {
		this.payPercent = payPercent;
	}
	
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
}