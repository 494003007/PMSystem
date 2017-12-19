/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同证件Entity
 * @author czy
 * @version 2016-11-05
 */
public class ContractCredentials extends DataEntity<ContractCredentials> {
	
	private static final long serialVersionUID = 1L;
	private String contractId;		// contract_id
	private String url;		// url
	private Date createTime;		// create_time
	private Date beginCreateTime;		// 开始 create_time
	private Date endCreateTime;		// 结束 create_time
	
	public ContractCredentials() {
		super();
	}

	public ContractCredentials(String id){
		super(id);
	}

	@Length(min=1, max=11, message="contract_id长度必须介于 1 和 11 之间")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
}