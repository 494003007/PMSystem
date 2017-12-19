/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包验收Entity
 * @author LKY
 * @version 2016-11-10
 */
public class OutcontractAcceptance extends DataEntity<OutcontractAcceptance> {
	
	private static final long serialVersionUID = 1L;
	private Integer  outcontractId;		// 外包合同编号
	private Date acceptanceTime;		// 验收时间
	private String acceptanceUrl;		// 验收单地址
	private Date beginAcceptanceTime;		// 开始 验收时间
	private Date endAcceptanceTime;		// 结束 验收时间
	private String outcontractName;
	public String getOutcontractName() {
		return outcontractName;
	}

	public void setOutcontractName(String outcontractName) {
		this.outcontractName = outcontractName;
	}

	public OutcontractAcceptance() {
		super();
	}

	public OutcontractAcceptance(String id){
		super(id);
	}

	
	public Integer getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(Integer outcontractId) {
		this.outcontractId = outcontractId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Date acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}
	
	@Length(min=0, max=255, message="验收单地址长度必须介于 0 和 255 之间")
	public String getAcceptanceUrl() {
		return acceptanceUrl;
	}

	public void setAcceptanceUrl(String acceptanceUrl) {
		this.acceptanceUrl = acceptanceUrl;
	}
	
	public Date getBeginAcceptanceTime() {
		return beginAcceptanceTime;
	}

	public void setBeginAcceptanceTime(Date beginAcceptanceTime) {
		this.beginAcceptanceTime = beginAcceptanceTime;
	}
	
	public Date getEndAcceptanceTime() {
		return endAcceptanceTime;
	}

	public void setEndAcceptanceTime(Date endAcceptanceTime) {
		this.endAcceptanceTime = endAcceptanceTime;
	}
		
}