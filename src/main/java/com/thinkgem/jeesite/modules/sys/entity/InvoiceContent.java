/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 开票内容Entity
 * @author cdoublejj
 * @version 2016-11-20
 */
public class InvoiceContent extends DataEntity<InvoiceContent> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 开票内容
	
	public InvoiceContent() {
		super();
	}

	public InvoiceContent(String id){
		super(id);
	}

	@Length(min=0, max=255, message="开票内容长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}