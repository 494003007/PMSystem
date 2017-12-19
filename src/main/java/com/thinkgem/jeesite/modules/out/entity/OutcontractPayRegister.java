/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * outcontract_billEntity
 * @author cdoublej
 * @version 2016-12-10
 */
public class OutcontractPayRegister extends DataEntity<OutcontractPayRegister> {

	private Double payAmount;
	private Date payDate;
	private String remarks;
	private String files;
	private String payStatus; 
	private String outcontractId;		// 所属外包合同编号
	private String outContractPayRegisterId;
	private OutcontractCheck outcontractCheck;
	private String outcontractCheckId;
	private Integer isInvoice;
	private String surplusamount;
	private String totalpayamount;
	private String totalbillamount;
	private String totalcheckamount;
	private String checkRemarks;
	private Date predictDate;
	private String outcontractName;
	private String outcontractCode;
	private String predictPay;
	private String outcontractPredictPay;
	private Date  outcontractPayDate;
	private String outcontractContent;
	private String checkContent;
	private String checkAmount;
	private Date checkDate;
	private Date startTime;
	private Date endTime;
	private Date signTime;
	private String outcontractBillId;
	private String printName;
	private int payType;
	private int printTimes;
	private int projectId;
	private double billAmount;
	private Date billDate;
	private Double billPay;

	public Double getBillPay() {
		return billPay;
	}

	public void setBillPay(Double billPay) {
		this.billPay = billPay;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}


	public int getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}



	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}


	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
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
	public String getOutcontractCheckId() {
		return outcontractCheckId;
	}
	public void setOutcontractCheckId(String outcontractCheckId) {
		this.outcontractCheckId = outcontractCheckId;
	}
	public String getTotalbillamount() {
		return totalbillamount;
	}
	public void setTotalbillamount(String totalbillamount) {
		this.totalbillamount = totalbillamount;
	}
	public String getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(String checkAmount) {
		this.checkAmount = checkAmount;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	 
	public String getCheckRemarks() {
		return checkRemarks;
	}
	public void setCheckRemarks(String checkRemarks) {
		this.checkRemarks = checkRemarks;
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
	public Date getPredictDate() {
		return predictDate;
	}
	public void setPredictDate(Date predictDate) {
		this.predictDate = predictDate;
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
	public String getPredictPay() {
		return predictPay;
	}
	public void setPredictPay(String predictPay) {
		this.predictPay = predictPay;
	}
	public String getTotalpayamount() {
		return totalpayamount;
	}
	public void setTotalpayamount(String totalpayamount) {
		this.totalpayamount = totalpayamount;
	}
	public String getTotalcheckamount() {
		return totalcheckamount;
	}
	public void setTotalcheckamount(String totalcheckamount) {
		this.totalcheckamount = totalcheckamount;
	}
	 
	public Integer getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}
	 
	public String getOutcontractId() {
		return outcontractId;
	}
	public void setOutcontractId(String outcontractId) {
		this.outcontractId = outcontractId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getOutContractPayRegisterId() {
		return outContractPayRegisterId;
	}
	public void setOutContractPayRegisterId(String outContractPayRegisterId) {
		this.outContractPayRegisterId = outContractPayRegisterId;
	}


	public OutcontractCheck getOutcontractCheck() {
		return outcontractCheck;
	}
	public void setOutcontractCheck(OutcontractCheck outcontractCheck) {
		this.outcontractCheck = outcontractCheck;
	}

	
	 
}