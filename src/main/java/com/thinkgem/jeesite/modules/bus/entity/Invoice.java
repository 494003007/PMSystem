/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.entity;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceContent;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceType;

/**
 * 发票Entity
 * @author fy
 * @version 2016-11-22
 */
public class Invoice extends DataEntity<Invoice> {
	
	private static long serialVersionUID = 1L;
	
	
	private Double invoiceAmount;		// 开票金额
	private String attentionItem;		// 注意事项
	private String invoiceCode;		// 发票号码
	private Integer contractId;		// 所属合同编号
	private String isContainRevenue;		// 是否含税
	private Double revenuePay;		// 税款
	private Double valuePay;		// 价款
	private Double revenueAmount;		// 税价合计
	private Integer customerId;		// 客户编号
	
	private Integer invoiceTypeId;		// 发票类型编号
	private Integer invoiceContentId;		// 开票内容编号
	private String invoiceRevenue;		// 发票税收
	private String invoiceRemarks;
	
	private String files;
	private String files2;
	private Contract contract;
	private InvoiceContent invoiceContent;
	private InvoiceType invoiceType;
	private Customer customer;
	private Project project;
	private Integer projectId;
	private String confirmDates;
	private String confirmAmount;
	private List<ContractConfirm> confirmList;
	private String confirmIds;
	private String ismatch;//发票是否被匹配
	private String returnAmount;//回款总额
	private String returnRegisterIds;//回款单号s
	private List<ReturnRegister> returnRegister;
	private String status;
	
	public String getFiles2() {
		return files2;
	}

	public void setFiles2(String files2) {
		this.files2 = files2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ReturnRegister> getReturnRegister() {
		return returnRegister;
	}

	public void setReturnRegister(List<ReturnRegister> returnRegister) {
		this.returnRegister = returnRegister;
	}

	public String getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(String returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getReturnRegisterIds() {
		returnRegisterIds="";
	 
		if(returnRegister != null && returnRegister.size() > 0)
		for(ReturnRegister c:returnRegister){
			returnRegisterIds +="," +  c.getId();
		}	
 
		 
		return returnRegisterIds;
	}

	public void setReturnRegisterIds(String returnRegisterIds) {
		this.returnRegisterIds = returnRegisterIds;
	}

	public String getIsmatch() {
		return ismatch;
	}

	public void setIsmatch(String ismatch) {
		this.ismatch = ismatch;
	}

	public List<ContractConfirm> getConfirmList() {
		return confirmList;
	}

	public void setConfirmList(List<ContractConfirm> confirmList) {
		this.confirmList = confirmList;
	}

	public String getConfirmDates() {
		confirmDates="";
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
		if(confirmList != null && confirmList.size() > 0)
		for(ContractConfirm c:confirmList){
			confirmDates +="," + s.format(c.getMonth());
		}	
 
		return confirmDates;
	}

	public void setConfirmDates(String confirmDates) {
		this.confirmDates = confirmDates;
	}

	public String getConfirmIds() {
		
		confirmIds="";
		 
		if(confirmList != null && confirmList.size() > 0)
		for(int i = 0;i < confirmList.size();i++){
		    ContractConfirm c = confirmList.get(i);
		    if(i == confirmList.size() - 1)
		    	confirmIds +=c.getId();
		    else
		    	confirmIds +=c.getId()+",";
		}	
		return confirmIds;
	}

	public void setConfirmIds(String confirmIds) {
		this.confirmIds = confirmIds;
	}

	public String getConfirmAmount() {
		
		Double confirmAmount2 = 0.00;
	 
		if(confirmList != null && confirmList.size() > 0)
		for(ContractConfirm c:confirmList){
			confirmAmount2 += Double.parseDouble(c.getConfirmAmount());
		}	
		return confirmAmount2.toString();
	}

	public void setConfirmAmount(String confirmAmount) {
		this.confirmAmount = confirmAmount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public InvoiceContent getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(InvoiceContent invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	private String isAdvanceCharge;
	public String getIsAdvanceCharge() {
		return isAdvanceCharge;
	}

	public void setIsAdvanceCharge(String isAdvanceCharge) {
		this.isAdvanceCharge = isAdvanceCharge;
	}

	public String getInvoiceRemarks() {
		return invoiceRemarks;
	}

	public void setInvoiceRemarks(String invoiceRemarks) {
		this.invoiceRemarks = invoiceRemarks;
	}

	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Invoice() {
		super();
	}

	public Invoice(String id){
		super(id);
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	@Length(min=0, max=500, message="注意事项长度必须介于 0 和 500 之间")
	public String getAttentionItem() {
		return attentionItem;
	}

	public void setAttentionItem(String attentionItem) {
		this.attentionItem = attentionItem;
	}
	
	@Length(min=0, max=30, message="发票号码长度必须介于 0 和 30 之间")
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
	@Length(min=0, max=10, message="是否含税长度必须介于 0 和 10 之间")
	public String getIsContainRevenue() {
		return isContainRevenue;
	}

	public void setIsContainRevenue(String isContainRevenue) {
		this.isContainRevenue = isContainRevenue;
	}
	
	public Double getRevenuePay() {
		return revenuePay;
	}

	public void setRevenuePay(Double revenuePay) {
		this.revenuePay = revenuePay;
	}
	
	public Double getValuePay() {
		return valuePay;
	}

	public void setValuePay(Double valuePay) {
		this.valuePay = valuePay;
	}
	
	public Double getRevenueAmount() {
		return revenueAmount;
	}

	public void setRevenueAmount(Double revenueAmount) {
		this.revenueAmount = revenueAmount;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
}