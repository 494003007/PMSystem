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
 * @author czy
 * @version 2016-12-10
 */
public class OutcontractBill extends DataEntity<OutcontractBill> {
	
	private static final long serialVersionUID = 1L;
	private Date billDate;		// 计提时间
	private String outContractBillId;//id 用于分页。存的是outcontract的id outContractBillId 才是实际outContractBill表的id
	private String billAmount;		// 计提金额
	private String outcontractId;		// 所属外包合同编号
	private String billExamine;		// 审核状态
	private String files;		// 附件地址
	private Outcontract outcontract;
	private OutcontractPay outcontractPay;
	private OutcontractCheck outcontractCheck;
	private String outcontractCheckId;
	private String remarks;
	private String payId;
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
	private String payAmount;
	private Date payDate;
	private String checkFiles;
	private String payFiles;
	private String surplusamount;
	private String printName;
	private Date firstPrint;
	private int printTimes;
	private String billNumber;
	private String outcontractPayId;
	private String outcontractInvoice;
	private String lastId;
	private Double billPay;

	public Double getBillPay() {
		return billPay;
	}

	public void setBillPay(Double billPay) {
		this.billPay = billPay;
	}

	public String getLastId() {
		return lastId;
	}

	public void setLastId(String lastId) {
		this.lastId = lastId;
	}

	public String getOutcontractPayRegisterId() {
		return outcontractPayRegisterId;
	}

	public void setOutcontractPayRegisterId(String outcontractPayRegisterId) {
		this.outcontractPayRegisterId = outcontractPayRegisterId;
	}

	private String outcontractPayRegisterId;

	public String getOutcontractInvoice() {
		return outcontractInvoice;
	}

	public void setOutcontractInvoice(String outcontractInvoice) {
		this.outcontractInvoice = outcontractInvoice;
	}

	public String getOutcontractPayId() {
		return outcontractPayId;
	}

	public void setOutcontractPayId(String outcontractPayId) {
		this.outcontractPayId = outcontractPayId;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public int getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}

	public Date getFirstPrint() {
		return firstPrint;
	}

	public void setFirstPrint(Date firstPrint) {
		this.firstPrint = firstPrint;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getSurplusamount() {
		return surplusamount;
	}

	public void setSurplusamount(String surplusamount) {
		this.surplusamount = surplusamount;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOutcontractCheckId() {
		return outcontractCheckId;
	}

	public void setOutcontractCheckId(String outcontractCheckId) {
		this.outcontractCheckId = outcontractCheckId;
	}

	public String getCheckFiles() {
		return checkFiles;
	}

	public void setCheckFiles(String checkFiles) {
		this.checkFiles = checkFiles;
	}

	public String getPayFiles() {
		return payFiles;
	}

	public void setPayFiles(String payFiles) {
		this.payFiles = payFiles;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getTotalpayamount() {
		return totalpayamount;
	}

	public void setTotalpayamount(String totalpayamount) {
		this.totalpayamount = totalpayamount;
	}

	public String getTotalbillamount() {
		return totalbillamount;
	}

	public void setTotalbillamount(String totalbillamount) {
		this.totalbillamount = totalbillamount;
	}

	public String getTotalcheckamount() {
		return totalcheckamount;
	}

	public void setTotalcheckamount(String totalcheckamount) {
		this.totalcheckamount = totalcheckamount;
	}

	public String getCheckRemarks() {
		return checkRemarks;
	}

	public void setCheckRemarks(String checkRemarks) {
		this.checkRemarks = checkRemarks;
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

	public String getOutContractBillId() {
		return outContractBillId;
	}

	public void setOutContractBillId(String outContractBillId) {
		this.outContractBillId = outContractBillId;
	}

	public OutcontractBill() {
		super();
	}

	public OutcontractBill(String id){
		super(id);
	}

	 
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	
	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	

	public String getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(String outcontractId) {
		this.outcontractId = outcontractId;
	}
	

	public String getBillExamine() {
		return billExamine;
	}

	public void setBillExamine(String billExamine) {
		this.billExamine = billExamine;
	}
	

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Outcontract getOutcontract() {
		return outcontract;
	}

	public void setOutcontract(Outcontract outcontract) {
		this.outcontract = outcontract;
	}

	public OutcontractPay getOutcontractPay() {
		return outcontractPay;
	}

	public void setOutcontractPay(OutcontractPay outcontractPay) {
		this.outcontractPay = outcontractPay;
	}

	public OutcontractCheck getOutcontractCheck() {
		return outcontractCheck;
	}

	public void setOutcontractCheck(OutcontractCheck outcontractCheck) {
		this.outcontractCheck = outcontractCheck;
	}
}