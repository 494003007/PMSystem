/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 预算信息生成Entity
 * @author ThinkGem
 * @version 2016-11-29
 */
public class ContractBudget extends DataEntity<ContractBudget> {
	
	private static final long serialVersionUID = 1L;
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Double amount;		// 预算金额
	private String contractId;		// 所属合同编号
	private String files;		// 附件地址
	
	public ContractBudget() {
		super();
	}

	public ContractBudget(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=11, message="所属合同编号长度必须介于 0 和 11 之间")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@Length(min=0, max=1000, message="附件地址长度必须介于 0 和 1000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
}