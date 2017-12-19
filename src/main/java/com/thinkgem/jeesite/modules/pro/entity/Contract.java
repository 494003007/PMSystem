/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceContent;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceType;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.web.InvoiceTypeController;

/**
 * 合同Entity
 * @author cdoublej
 * @version 2016-11-20
 */
public class Contract extends DataEntity<Contract> {
	
	private static final long serialVersionUID = 1L;
	private String contractCode;		// 合同编号
	private String name;		// 合同名称
	private String acontractCode;		// 甲方合同编号
	private Integer projectId;		// 所属项目编号
	private Double amount;		// 合同总金额
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private Date signTime;		// 签订时间
	private Date realStartTime;		// 实际开工时间
	private Integer customerId;		// 客户编号
	private Integer invoiceTypeId;		// 发票类型编号
	private Integer invoiceContentId;		// 开票内容编号
	private String invoiceRevenue;		// 发票税收
	private Date predictRecoverDate;		// 预计合同回收时间
	private String workRemarks;		// 合同工作量描述
	private Date TempleDate;
	private String checkContent;		// 验收内容
	private String workconfirmid; //工作量确定方法
	private Project project;
	private List<ContractPay> contractPayList = Lists.newArrayList(); // 约定付款列表
	private List<ContractConfirm> contractConfirmList = Lists.newArrayList(); // 确认列表
	private Customer customer;
	private String files;
	private ContractConfirm contractConfirm;
	private InvoiceContent invoiceContent;
	private InvoiceType invoiceType;
	private Double totalamount;//总金额
	private Double budgettotalamount;//总预算
	private String totalconfirm;//总确认
	private String totalinvoice;//总开票金额
	private String totalreturn;//总回款金额
	private String totalcost;//总成本
	private String totalpredict;//总预开金额
	private String invoiceCount;//开票总次数 
	private String isExamine;//是否需要审核。申报
	private String customerName; 
	private String customerMobile;
	private String big_sysAreaId;
	private String checkClause;
	private String authorityEmployeeId;
	
	
	public String getAuthorityEmployeeId() {
		return authorityEmployeeId;
	}

	public void setAuthorityEmployeeId(String authorityEmployeeId) {
		this.authorityEmployeeId = authorityEmployeeId;
	}

	public String getTotalpredict() {
		return totalpredict;
	}

	public void setTotalpredict(String totalpredict) {
		this.totalpredict = totalpredict;
	}

	public String getInvoiceCount() {
		return invoiceCount;
	}

	public void setInvoiceCount(String invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

	public String getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(String totalcost) {
		this.totalcost = totalcost;
	}

	public String getTotalconfirm() {
		return totalconfirm;
	}

	public void setTotalconfirm(String totalconfirm) {
		this.totalconfirm = totalconfirm;
	}

	public String getTotalinvoice() {
		return totalinvoice;
	}

	public void setTotalinvoice(String totalinvoice) {
		this.totalinvoice = totalinvoice;
	}

	public String getTotalreturn() {
		return totalreturn;
	}

	public void setTotalreturn(String totalreturn) {
		this.totalreturn = totalreturn;
	}

	public Double getBudgettotalamount() {
		return budgettotalamount;
	}

	public void setBudgettotalamount(Double budgettotalamount) {
		this.budgettotalamount = budgettotalamount;
	}

	public String getCheckClause() {
		return checkClause;
	}

	public void setCheckClause(String checkClause) {
		this.checkClause = checkClause;
	}

	public String getBig_sysAreaId() {
		return big_sysAreaId;
	}

	public void setBig_sysAreaId(String big_sysAreaId) {
		this.big_sysAreaId = big_sysAreaId;
	}

	public String getIsExamine() {
		return isExamine;
	}

	public void setIsExamine(String isExamine) {
		this.isExamine = isExamine;
	}

	public Date getTempleDate() {
		return TempleDate;
	}

	public void setTempleDate(Date templeDate) {
		TempleDate = templeDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public List<ContractConfirm> getContractConfirmList() {
		return contractConfirmList;
	}

	public void setContractConfirmList(List<ContractConfirm> contractConfirmList) {
		this.contractConfirmList = contractConfirmList;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public InvoiceContent getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(InvoiceContent invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public ContractConfirm getContractConfirm() {
		return contractConfirm;
	}

	public void setContractConfirm(ContractConfirm contractConfirm) {
		this.contractConfirm = contractConfirm;
	}

	public List<ContractPay> getContractPayList() {
		return contractPayList;
	}

	public void setContractPayList(List<ContractPay> contractPayList) {
		this.contractPayList = contractPayList;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getWorkconfirmid() {
		return workconfirmid;
	}

	public void setWorkconfirmid(String workconfirmid) {
		this.workconfirmid = workconfirmid;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Contract() {
		super();
	}

	public Contract(String id){
		super(id);
	}


	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getAcontractCode() {
		return acontractCode;
	}

	public void setAcontractCode(String acontractCode) {
		this.acontractCode = acontractCode;
	}
	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Double getAmount() {
 
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getInvoiceTypeId() {
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(Integer invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}
	
	public Integer getInvoiceContentId() {
		return invoiceContentId;
	}

	public void setInvoiceContentId(Integer invoiceContentId) {
		this.invoiceContentId = invoiceContentId;
	}
	
	public String getInvoiceRevenue() {
		return invoiceRevenue;
	}

	public void setInvoiceRevenue(String invoiceRevenue) {
		this.invoiceRevenue = invoiceRevenue;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPredictRecoverDate() {
		return predictRecoverDate;
	}

	public void setPredictRecoverDate(Date predictRecoverDate) {
		this.predictRecoverDate = predictRecoverDate;
	}
	
	@Length(min=0, max=1000, message="合同工作量描述长度必须介于 0 和 1000 之间")
	public String getWorkRemarks() {
		return workRemarks;
	}

	public void setWorkRemarks(String workRemarks) {
		this.workRemarks = workRemarks;
	}
	
 
	@Length(min=0, max=1000, message="验收内容长度必须介于 0 和 1000 之间")
	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
 
	
}