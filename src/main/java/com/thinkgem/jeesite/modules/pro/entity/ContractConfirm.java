/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;

/**
 * 项目进度Entity
 * @author cdoublej
 * @version 2016-11-30
 */
public class ContractConfirm extends DataEntity<ContractConfirm> {
	
	private static final long serialVersionUID = 1L;
	private Date month;		// 月份
	
	private String confirmAmount;// 外部确认总金额
	private String confirmRemarks; //外部确认说明
	private String projectId;
	private String contractPayIds;// 所有合同付款id
	private Date predictInvoiceDate;//预计开票时间
	private String isExistInvoice;//是否已开发票
	private Integer contractId;		// 所属合同编号
	private String inconfirmAmount;// 内部确认总额
	private String appointmentAmount;//约定付款总额而
	private String countAppointmentAmount;//按量付款约定总额
	private String files;
	private String examineStatus;//审核状态
	private String examineRemarks;//审核备注
	private ContractInvoiceConfirm contractInvoiceConfirm;
	private String hasInvoice;//是否已经匹配发票
	private Contract contract;
	 

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(String hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public ContractInvoiceConfirm getContractInvoiceConfirm() {
		return contractInvoiceConfirm;
	}

	public void setContractInvoiceConfirm(
			ContractInvoiceConfirm contractInvoiceConfirm) {
		this.contractInvoiceConfirm = contractInvoiceConfirm;
	}

	public String getExamineRemarks() {
		return examineRemarks;
	}

	public void setExamineRemarks(String examineRemarks) {
		this.examineRemarks = examineRemarks;
	}

	public String getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}

	public String getCountAppointmentAmount() {
		return countAppointmentAmount;
	}

	public void setCountAppointmentAmount(String countAppointmentAmount) {
		this.countAppointmentAmount = countAppointmentAmount;
	}

	public String getAppointmentAmount() {
		return appointmentAmount;
	}

	public void setAppointmentAmount(String appointmentAmount) {
		this.appointmentAmount = appointmentAmount;
	}

	public String getInconfirmAmount() {
		return inconfirmAmount;
	}

	public void setInconfirmAmount(String inconfirmAmount) {
		this.inconfirmAmount = inconfirmAmount;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public ContractConfirm() {
		super();
	}

	public ContractConfirm(String id){
		super(id);
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public String getConfirmAmount() {
		return confirmAmount;
	}

	public void setConfirmAmount(String confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	public String getConfirmRemarks() {
		return confirmRemarks;
	}

	public void setConfirmRemarks(String confirmRemarks) {
		this.confirmRemarks = confirmRemarks;
	}

	public String getContractPayIds() {
		return contractPayIds;
	}

	public void setContractPayIds(String contractPayIds) {
		this.contractPayIds = contractPayIds;
	}

	public Date getPredictInvoiceDate() {
		return predictInvoiceDate;
	}

	public void setPredictInvoiceDate(Date predictInvoiceDate) {
		this.predictInvoiceDate = predictInvoiceDate;
	}

	public String getIsExistInvoice() {
		return isExistInvoice;
	}

	public void setIsExistInvoice(String isExistInvoice) {
		this.isExistInvoice = isExistInvoice;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	 
	
}