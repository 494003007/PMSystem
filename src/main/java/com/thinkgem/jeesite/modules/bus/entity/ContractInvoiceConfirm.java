/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.pro.entity.Contract;

/**
 * busEntity
 * @author cdoublej
 * @version 2016-12-20
 */
public class ContractInvoiceConfirm extends DataEntity<ContractInvoiceConfirm> {
	
	private static final long serialVersionUID = 1L;
	private String contractConfirmId;		// 所属合同确认表编号
	private String invoiceId;		// 发票编号
	private Date matchDate;		// 关联时间

	private String names;//关联的所有发票的合同名称
	private List<Contract> contractlist;
	
	 
	
	public String getNames() {
		names="";
		if(contractlist != null && contractlist.size() > 0)
		for(Contract c:contractlist){
			names +="," + c.getName();
		}
	
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public List<Contract> getContractlist() {
		return contractlist;
	}

	public void setContractlist(List<Contract> contractlist) {
		this.contractlist = contractlist;
	}
	public ContractInvoiceConfirm() {
		super();
	}

	public ContractInvoiceConfirm(String id){
		super(id);
	}

	public String getContractConfirmId() {
		return contractConfirmId;
	}

	public void setContractConfirmId(String contractConfirmId) {
		this.contractConfirmId = contractConfirmId;
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	
}