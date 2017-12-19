/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 合同类型Entity
 * @author cdoublej
 * @version 2016-10-08
 */
public class ContractType extends DataEntity<ContractType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类型名称
	
	public ContractType() {
		super();
	}

	public ContractType(String id){
		super(id);
	}

	@Length(min=1, max=40, message="类型名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}