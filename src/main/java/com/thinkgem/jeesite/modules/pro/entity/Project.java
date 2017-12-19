/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 项目Entity
 * @author cdoublej
 * @version 2016-11-03
 */
public class Project extends DataEntity<Project> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private Integer employeeId;		// 项目经理编号
	private Integer bpmemployeeId;     //大区经理编号
	private Integer spmemployeeId;     //大区经理编号
	private String projectTypeId;		// 项目类型编号
	private String sysAreaId;		// 区域编号
	private String ispaycontract; 	//	是否纯支出类项目
	private String iscostcontract; //成本是否分合同
	private String isframecontract; //是否是框架合同
	private String examinestatus;		// 项目状态	
	private Employee employee;
	private Employee bpmemployee;
	private Employee spmemployee;
	private List<Map<String,String>> projectConfirmList;//用于存放工作量确认项目列表12个月的确认信息
	private SysArea sysArea;
	private ProjectType projectType;
	private String files;
	private String totalcost;//总成本
	private String totalbudget;//总预算
	private String totalconfirm;//总确认
	private String totalamount;//总金额
	private String totalinvoice;//总开票金额
	private String totalreturn;//总回款金额
	private String totalpredict;//总预开金额
	private String invoiceCount;//开票总次数 
	private String projectCode;//项目编号
	private String billamount;//计提总额
	private String realpayamount;//外包支付总额
	private String checkamount;//外包验收总额
	private String pridictamount;//外包约定付款总额
	private Customer customer;
	private String isExamine;//是否需要审核
	private Date templeDate;//临时字段，用于查询
	private String authorityEmployeeId;//权限控制.存
	

	public String getPridictamount() {
		return pridictamount;
	}
	public void setPridictamount(String pridictamount) {
		this.pridictamount = pridictamount;
	}
	public String getBillamount() {
		return billamount;
	}
	public void setBillamount(String billamount) {
		this.billamount = billamount;
	}
	public String getRealpayamount() {
		return realpayamount;
	}
	public void setRealpayamount(String realpayamount) {
		this.realpayamount = realpayamount;
	}
	public String getCheckamount() {
		return checkamount;
	}
	public void setCheckamount(String checkamount) {
		this.checkamount = checkamount;
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
	public List<Map<String, String>> getProjectConfirmList() {
		return projectConfirmList;
	}
	public void setProjectConfirmList(List<Map<String, String>> projectConfirmList) {
		this.projectConfirmList = projectConfirmList;
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
	public Employee getSpmemployee() {
		return spmemployee;
	}
	public void setSpmemployee(Employee spmemployee) {
		this.spmemployee = spmemployee;
	}
	public Integer getSpmemployeeId() {
		return spmemployeeId;
	}
	public void setSpmemployeeId(Integer spmemployeeId) {
		this.spmemployeeId = spmemployeeId;
	}
	 
	 
	public String getAuthorityEmployeeId() {
		return authorityEmployeeId;
	}
	public void setAuthorityEmployeeId(String authorityEmployeeId) {
		this.authorityEmployeeId = authorityEmployeeId;
	}
	public Date getTempleDate() {
		return templeDate;
	}
	public void setTempleDate(Date templeDate) {
		this.templeDate = templeDate;
	}
	public String getIsExamine() {
		return isExamine;
	}
	public void setIsExamine(String isExamine) {
		this.isExamine = isExamine;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(String totalcost) {
		this.totalcost = totalcost;
	}
	public String getTotalbudget() {
		return totalbudget;
	}
	public void setTotalbudget(String totalbudget) {
		this.totalbudget = totalbudget;
	}
	public String getTotalconfirm() {
		return totalconfirm;
	}
	public void setTotalconfirm(String totalconfirm) {
		this.totalconfirm = totalconfirm;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public ProjectType getProjectType() {
		return projectType;
	}
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getBpmemployeeId() {
		return bpmemployeeId;
	}
	public void setBpmemployeeId(Integer bpmemployeeId) {
		this.bpmemployeeId = bpmemployeeId;
	}
	public String getProjectTypeId() {
		return projectTypeId;
	}
	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	public String getIspaycontract() {
		return ispaycontract;
	}
	public void setIspaycontract(String ispaycontract) {
		this.ispaycontract = ispaycontract;
	}
	public String getIscostcontract() {
		return iscostcontract;
	}
	public void setIscostcontract(String iscostcontract) {
		this.iscostcontract = iscostcontract;
	}
	public String getIsframecontract() {
		return isframecontract;
	}
	public void setIsframecontract(String isframecontract) {
		this.isframecontract = isframecontract;
	}
	public String getExaminestatus() {
		return examinestatus;
	}
	public void setExaminestatus(String examinestatus) {
		this.examinestatus = examinestatus;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Employee getBpmemployee() {
		return bpmemployee;
	}
	public void setBpmemployee(Employee bpmemployee) {
		this.bpmemployee = bpmemployee;
	}
	public SysArea getSysArea() {
		return sysArea;
	}
	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	 
 
	
}