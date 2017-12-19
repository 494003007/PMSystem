/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发票类型Entity
 * @author cdoublej
 * @version 2016-11-20
 */
public class InvoiceType extends DataEntity<InvoiceType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 发票类型名称
	
	public InvoiceType() {
		super();
	}

	public InvoiceType(String id){
		super(id);
	}

	@Length(min=0, max=50, message="发票类型名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}