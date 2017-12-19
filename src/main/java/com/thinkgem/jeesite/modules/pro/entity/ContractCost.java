/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.*;

/**
 * 成本录入Entity
 * @author ThinkGem
 * @version 2016-12-12
 */
public class ContractCost extends DataEntity<ContractCost> {
	
	private static final long serialVersionUID = 1L;
	private Date costMonth;		// 成本归属月份
	private Double cost;		// 月成本金额
	private String projectId;		// 所属项目编号
	private String contractId;		// 所属合同编号
	private Date beginCostMonth;		// 开始 成本归属月份
	private Date endCostMonth;		// 结束 成本归属月份
	private String files;
	public ContractCost() {
		super();
	}

	
	public String getFiles() {
		return files;
	}


	public void setFiles(String files) {
		this.files = files;
	}


	public ContractCost(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "时间不能为空")
	public Date getCostMonth() {
		return costMonth;
	}


	public void setCostMonth(Date costMonth) {
		this.costMonth = costMonth;
	}

	@Min(value = 0,message = "成本范围错误")
	@Max(value = 2100000000,message = "成本范围错误")
	@NotNull(message = "成本值不可为空")
	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	@Length(min=0, max=11, message="所属项目编号长度必须介于 0 和 11 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=11, message="所属合同编号长度必须介于 0 和 11 之间")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public Date getBeginCostMonth() {
		return beginCostMonth;
	}

	public void setBeginCostMonth(Date beginCostMonth) {
		this.beginCostMonth = beginCostMonth;
	}
	
	public Date getEndCostMonth() {
		return endCostMonth;
	}

	public void setEndCostMonth(Date endCostMonth) {
		this.endCostMonth = endCostMonth;
	}
		
}