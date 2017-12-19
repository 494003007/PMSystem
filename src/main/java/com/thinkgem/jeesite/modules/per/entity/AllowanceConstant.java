/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 补贴常量Entity
 * @author cdoublej
 * @version 2016-10-11
 */
public class AllowanceConstant extends DataEntity<AllowanceConstant> {

	private static final long serialVersionUID = 1L;
	private String name;		// 补贴名称
	private String allowance;		// 补贴金额
	private Date beginExpiredTime;		// 开始 begin_expired_time
	private Date endExpiredTime;		// 结束 end_expired_time

	public Date getBeginExpiredTime() {
		return beginExpiredTime;
	}

	public void setBeginExpiredTime(Date beginExpiredTime) {
		this.beginExpiredTime = beginExpiredTime;
	}

	public Date getEndExpiredTime() {
		return endExpiredTime;
	}

	public void setEndExpiredTime(Date endExpiredTime) {
		this.endExpiredTime = endExpiredTime;
	}


	public AllowanceConstant() {
		super();
	}

	public AllowanceConstant(String id){
		super(id);
	}

	@Length(min=1, max=30, message="补贴名称长度必须介于 1 和 30 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=10, message="补贴金额长度必须介于 1 和 10 之间")
	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

}