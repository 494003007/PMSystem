/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发票登记Entity
 * @author czy
 * @version 2016-12-19
 */
public class OutcontractInvoice extends DataEntity<OutcontractInvoice> {
	
	private static final long serialVersionUID = 1L;
	private String invoiceAmount;		// invoice_amount
	private Date invoiceDate;		// 发票日期
	private String invoiceRevenue;		// 开票税率
	private String invoiceCode;		// 发票号码
	private String outcontractId;		// 所属外包合同编号
	private String invoiceType;		// 发票类型
	private String examineStatus;		// 审核状态
	private String files;		// 附件地址
	private Outcontract outcontract;
	private OutcontractPay outcontractPay;
	private OutcontractCheck outcontractCheck;
	private String outContractInvoiceId;
	
	 

	public String getOutContractInvoiceId() {
		return outContractInvoiceId;
	}

	public void setOutContractInvoiceId(String outContractInvoiceId) {
		this.outContractInvoiceId = outContractInvoiceId;
	}

	public OutcontractInvoice() {
		super();
	}

	public OutcontractInvoice(String id){
		super(id);
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	public String getInvoiceRevenue() {
		return invoiceRevenue;
	}

	public void setInvoiceRevenue(String invoiceRevenue) {
		this.invoiceRevenue = invoiceRevenue;
	}
	
	@Length(min=0, max=30, message="发票号码长度必须介于 0 和 30 之间")
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	@Length(min=0, max=11, message="所属外包合同编号长度必须介于 0 和 11 之间")
	public String getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(String outcontractId) {
		this.outcontractId = outcontractId;
	}
	
	@Length(min=0, max=20, message="发票类型长度必须介于 0 和 20 之间")
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	@Length(min=0, max=10, message="审核状态长度必须介于 0 和 10 之间")
	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}
	
	@Length(min=0, max=500, message="附件地址长度必须介于 0 和 500 之间")
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