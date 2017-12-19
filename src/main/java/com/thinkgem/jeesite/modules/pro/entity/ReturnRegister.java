/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 回款登记Entity
 * @author cdoublej
 * @version 2016-11-25
 */
public class ReturnRegister extends DataEntity<ReturnRegister> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 回款单号
	private Date returnDate;		// 回款时间
	private String returnCompany;		// 回款单位
	private String amount;		// 回款总额
	private String isTrueCompany;		// 回款单位是否一致
	private String surplusAmount;		// 剩余金额
	private String status;		// 状态
	private Date startDate;//回款开始时间，查询
	private Date endDate;//回款结束时间，查询
	private String is_surplusAmount;//是否有剩余金额 查询  1 否 2 是
	private String templeAmount;//存临时存放金额
	
	public String getTempleAmount() {
		return templeAmount;
	}

	public void setTempleAmount(String templeAmount) {
		this.templeAmount = templeAmount;
	}

	public String getIs_surplusAmount() {
		return is_surplusAmount;
	}

	public void setIs_surplusAmount(String is_surplusAmount) {
		this.is_surplusAmount = is_surplusAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ReturnRegister() {
		super();
	}

	public ReturnRegister(String id){
		super(id);
	}

	@Length(min=0, max=30, message="回款单号长度必须介于 0 和 30 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	@ExcelField(title="回款时间", align=2, sort=10)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	@ExcelField(title="回款单位", align=2, sort=20)
	@Length(min=0, max=50, message="回款单位长度必须介于 0 和 50 之间")
	public String getReturnCompany() {
		return returnCompany;
	}

	public void setReturnCompany(String returnCompany) {
		this.returnCompany = returnCompany;
	}
	@ExcelField(title="回款金额", align=2, sort=30)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=10, message="回款单位是否一致长度必须介于 0 和 10 之间")
	public String getIsTrueCompany() {
		return isTrueCompany;
	}

	public void setIsTrueCompany(String isTrueCompany) {
		this.isTrueCompany = isTrueCompany;
	}
	
	public String getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(String surplusAmount) {
		this.surplusAmount = surplusAmount;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}