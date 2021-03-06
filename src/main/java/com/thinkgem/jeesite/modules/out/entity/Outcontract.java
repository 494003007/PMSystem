/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.modules.pro.entity.Project;

/**
 * 外包登记Entity
 * @author LKY
 * @version 2016-11-22
 */
public class Outcontract extends TreeEntity<Outcontract> {
	
	private static final long serialVersionUID = 1L;
	private String outcontractCode;		// 外包合同编号
	private Outcontract parent;		// 父级合同编号
	private String parentIds;		// 所有父级编号
	private String name;		// 外包合同名称
	private Integer projectId;		// 所属项目编号
	private Integer contractId;		// 所属合同编号
	private Integer outcontractTypeId;		// 外包合同类型编号
	private Double amount;		// 合同金额(订单金额)(补充合同金额)
	private Date startTime;		// 合同开始时间(订单开始时间)(补充合同开始时间)
	private Integer outsourcerId;		// 外包商编号
	private Date signTime;		// 签订时间
	private Date endTime;		// 合同结束时间(订单结束时间)(补充合同结束时间)
	private String payWay;		// 付款方式
	private String settlementWay;		// 结算方式
	private String outcontractContent;		// 合同内容(订单内容)(补充合同内容)
	private String checkContent;		// 验收内容
	private String checkExamineStatus;		// 验收审核状态
	private String projectName;
	private String contractName;
	private String  outcontractTypeName;
	private String outsourcerName;
	private String  checkStatus;
	private int checkamounts=0;//已验收金额
	private String orderId;
	private String checkremarks="无";
	private String files;
	private String amountcopy;
	private String checkamountcopy;
	private String billamount;//计提总额
	private String realpayamount;//外包支付总额
	private String checkamount;//外包验收总额
	private String pridictamount;//外包约定付款总额
	private Outsourcer outsourcer;
	private Project project;
	private String payPromise;

	public String getPayPromise() {
		return payPromise;
	}

	public void setPayPromise(String payPromise) {
		this.payPromise = payPromise;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Outsourcer getOutsourcer() {
		return outsourcer;
	}

	public void setOutsourcer(Outsourcer outsourcer) {
		this.outsourcer = outsourcer;
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

	public String getPridictamount() {
		return pridictamount;
	}

	public void setPridictamount(String pridictamount) {
		this.pridictamount = pridictamount;
	}

	public String getOutcontractTypeName() {
		return outcontractTypeName;
	}

	public void setOutcontractTypeName(String outcontractTypeName) {
		this.outcontractTypeName = outcontractTypeName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Outcontract() {
		super();
	}

	public Outcontract(String id){
		super(id);
	}

	@Length(min=0, max=64, message="外包合同编号长度必须介于 0 和 64 之间")
	public String getOutcontractCode() {
		return outcontractCode;
	}

	public void setOutcontractCode(String outcontractCode) {
		this.outcontractCode = outcontractCode;
	}
	
	@JsonBackReference
	public Outcontract getParent() {
		return parent;
	}

	public void setParent(Outcontract parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=200, message="所有父级编号长度必须介于 0 和 200 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=50, message="外包合同名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	
	public Integer getOutcontractTypeId() {
		return outcontractTypeId;
	}

	public void setOutcontractTypeId(Integer outcontractTypeId) {
		this.outcontractTypeId = outcontractTypeId;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Integer getOutsourcerId() {
		return outsourcerId;
	}

	public void setOutsourcerId(Integer outsourcerId) {
		this.outsourcerId = outsourcerId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=10, message="付款方式长度必须介于 0 和 10 之间")
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	@Length(min=0, max=10, message="结算方式长度必须介于 0 和 10 之间")
	public String getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		this.settlementWay = settlementWay;
	}
	
	@Length(min=0, max=500, message="合同内容(订单内容)(补充合同内容)长度必须介于 0 和 500 之间")
	public String getOutcontractContent() {
		return outcontractContent;
	}

	public void setOutcontractContent(String outcontractContent) {
		this.outcontractContent = outcontractContent;
	}
	
	@Length(min=0, max=500, message="验收内容长度必须介于 0 和 500 之间")
	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	@Length(min=0, max=10, message="验收审核状态长度必须介于 0 和 10 之间")
	public String getCheckExamineStatus() {
		return checkExamineStatus;
	}

	public void setCheckExamineStatus(String checkExamineStatus) {
		this.checkExamineStatus = checkExamineStatus;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public String getOutsourcerName() {
		return outsourcerName;
	}

	public void setOutsourcerName(String outsourcerName) {
		this.outsourcerName = outsourcerName;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public int getCheckamounts() {
		return checkamounts;
	}

	public void setCheckamounts(int checkamounts) {
		this.checkamounts = checkamounts;
	}

	public String getCheckremarks() {
		return checkremarks;
	}

	public void setCheckremarks(String checkremarks) {
		this.checkremarks = checkremarks;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getAmountcopy() {
		return amountcopy;
	}

	public void setAmountcopy(String amountcopy) {
		this.amountcopy = amountcopy;
	}

	public String getCheckamountcopy() {
		return checkamountcopy;
	}

	public void setCheckamountcopy(String checkamountcopy) {
		this.checkamountcopy = checkamountcopy;
	}
}