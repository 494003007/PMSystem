/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;

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
public class InvoiceReturnRegister extends DataEntity<InvoiceReturnRegister> {
	private String returnAmount;
	private String InvoiceId;
	private String returnRegisterId;
	public String getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(String returnAmount) {
		this.returnAmount = returnAmount;
	}
	public String getInvoiceId() {
		return InvoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		InvoiceId = invoiceId;
	}
	public String getReturnRegisterId() {
		return returnRegisterId;
	}
	public void setReturnRegisterId(String returnRegisterId) {
		this.returnRegisterId = returnRegisterId;
	}
	
 
}