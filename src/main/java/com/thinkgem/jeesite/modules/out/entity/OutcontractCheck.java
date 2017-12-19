/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包验收Entity
 * @author LKY
 * @version 2016-12-02
 */
public class OutcontractCheck extends DataEntity<OutcontractCheck> {
	
	private static final long serialVersionUID = 1L;
	private Integer outcontractId;		// 所属外包合同编号
	private Date checkDate;		// 验收时间
	private String checkRemarks;		// 验收说明
	private String checkStatus;		// 验收情况，状态
	private Double checkAmount;		// 验收金额
	private Date beginCheckDate;		// 开始 验收时间
	private Date endCheckDate;		// 结束 验收时间
	private String files;
	private String outcontractName;
	private String outcontractCode;
	private String outcontractPredictPay;
	private Date outcontractPayDate;
	private String totalcheckamount;
	private OutcontractPay outcontractPay;
	private String outcontractPayId;
	private String totalbillamount;
	private String totalpayamount;
	private Date startTime;
	private Date endTime;
	private Date signTime;
	private String outcontractContent;
	private String checkContent;
	private String billPayAmount;
	private String surplusamount;
	private String outcontractBillId;
	private String outcontractCheckId;


	public String getOutcontractCheckId() {
		return outcontractCheckId;
	}

	public void setOutcontractCheckId(String outcontractCheckId) {
		this.outcontractCheckId = outcontractCheckId;
	}

	public String getOutcontractBillId() {
		return outcontractBillId;
	}

	public void setOutcontractBillId(String outcontractBillId) {
		this.outcontractBillId = outcontractBillId;
	}

	public String getSurplusamount() {
		return surplusamount;
	}

	public void setSurplusamount(String surplusamount) {
		this.surplusamount = surplusamount;
	}

	public String getBillPayAmount() {
		return billPayAmount;
	}

	public void setBillPayAmount(String billPayAmount) {
		this.billPayAmount = billPayAmount;
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

	public String getTotalbillamount() {
		return totalbillamount;
	}

	public void setTotalbillamount(String totalbillamount) {
		this.totalbillamount = totalbillamount;
	}

	public String getTotalpayamount() {
		return totalpayamount;
	}

	public void setTotalpayamount(String totalpayamount) {
		this.totalpayamount = totalpayamount;
	}

	public String getOutcontractPayId() {
		return outcontractPayId;
	}

	public void setOutcontractPayId(String outcontractPayId) {
		this.outcontractPayId = outcontractPayId;
	}

	public OutcontractPay getOutcontractPay() {
		return outcontractPay;
	}

	public void setOutcontractPay(OutcontractPay outcontractPay) {
		this.outcontractPay = outcontractPay;
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

	public String getOutcontractPredictPay() {
		return outcontractPredictPay;
	}

	public void setOutcontractPredictPay(String outcontractPredictPay) {
		this.outcontractPredictPay = outcontractPredictPay;
	}

	public Date getOutcontractPayDate() {
		return outcontractPayDate;
	}

	public void setOutcontractPayDate(Date outcontractPayDate) {
		this.outcontractPayDate = outcontractPayDate;
	}

	public OutcontractCheck() {
		super();
	}

	public OutcontractCheck(String id){
		super(id);
	}

	public Integer getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(Integer outcontractId) {
		this.outcontractId = outcontractId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=1000, message="验收说明长度必须介于 0 和 1000 之间")
	public String getCheckRemarks() {
		return checkRemarks;
	}

	public void setCheckRemarks(String checkRemarks) {
		this.checkRemarks = checkRemarks;
	}
	
	@Length(min=0, max=10, message="验收情况，状态长度必须介于 0 和 10 之间")
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public Double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	
	public Date getBeginCheckDate() {
		return beginCheckDate;
	}

	public void setBeginCheckDate(Date beginCheckDate) {
		this.beginCheckDate = beginCheckDate;
	}
	
	public Date getEndCheckDate() {
		return endCheckDate;
	}

	public void setEndCheckDate(Date endCheckDate) {
		this.endCheckDate = endCheckDate;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
		
}