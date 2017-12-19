/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包付款Entity
 * @author LKY
 * @version 2016-11-30
 */
public class OutcontractPay extends DataEntity<OutcontractPay> {
	
	private static final long serialVersionUID = 1L;
	private Date payStartDate;		// 付款(结算)开始时间
	private Date payEndDate;		// 付款(结算)结束时间
	private Date payDate;		// 付款(结算)时间
	private Double predictPay;		// 应付款(结算)金额(验收前,预计)
	private Double realPay;		// 实际应支付金额(验收金额)
	private Double payPercent;		// 付款比例
	private String payReason;		// 付款事由
	private String payStatus;		// 付款情况、状态
	private Integer outcontractId;		// 所属外包合同编号
	private Integer orderId;		// 所属订单编号
	private Date beginPayDate;		// 开始 付款(结算)时间
	private Date endPayDate;		// 结束 付款(结算)时间
	private String orderName;
	private String totalcheckamount;
	private String outcontractName;
	private String outcontractCode;
	private String outcontractContent;
	private String checkContent;
	private Date startTime;
	private Date endTime;
	private Date signTime;
	private Outcontract outcontract;
	private String surplusamount;
	private Integer isdelete;
	private String printName;
	private String oaNumber;
	private int printTimes;

	public int getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}

	public String getOaNumber() {
		return oaNumber;
	}

	public void setOaNumber(String oaNumber) {
		this.oaNumber = oaNumber;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getSurplusamount() {
		return surplusamount;
	}

	public void setSurplusamount(String surplusamount) {
		this.surplusamount = surplusamount;
	}

	public String getOutcontractContent() {
		return outcontractContent;
	}

	public void setOutcontractContent(String outcontractContent) {
		this.outcontractContent = outcontractContent;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Outcontract getOutcontract() {
		return outcontract;
	}

	public void setOutcontract(Outcontract outcontract) {
		this.outcontract = outcontract;
	}

	public String getTotalcheckamount() {
		return totalcheckamount;
	}

	public void setTotalcheckamount(String totalcheckamount) {
		this.totalcheckamount = totalcheckamount;
	}

	public String getOutcontractName() {
		return outcontractName;
	}

	public void setOutcontractName(String outcontractName) {
		this.outcontractName = outcontractName;
	}

	public String getOutcontractCode() {
		return outcontractCode;
	}

	public void setOutcontractCode(String outcontractCode) {
		this.outcontractCode = outcontractCode;
	}

	public OutcontractPay() {
		super();
	}

	public OutcontractPay(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayStartDate() {
		return payStartDate;
	}

	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public Double getPredictPay() {
		return predictPay;
	}

	public void setPredictPay(Double predictPay) {
		this.predictPay = predictPay;
	}
	
	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}
	
	public Double getPayPercent() {
		return payPercent;
	}

	public void setPayPercent(Double payPercent) {
		this.payPercent = payPercent;
	}
	
	@Length(min=0, max=500, message="付款事由长度必须介于 0 和 500 之间")
	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}
	
	@Length(min=0, max=10, message="付款情况、状态长度必须介于 0 和 10 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public Integer getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(Integer outcontractId) {
		this.outcontractId = outcontractId;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Date getBeginPayDate() {
		return beginPayDate;
	}

	public void setBeginPayDate(Date beginPayDate) {
		this.beginPayDate = beginPayDate;
	}
	
	public Date getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(Date endPayDate) {
		this.endPayDate = endPayDate;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
		
}