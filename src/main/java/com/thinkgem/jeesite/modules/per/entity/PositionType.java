/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 岗位Entity
 * @author cdoublej
 * @version 2016-10-18
 */
public class PositionType extends DataEntity<PositionType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 岗位名称
	
	public PositionType() {
		super();
	}

	public PositionType(String id){
		super(id);
	}

	@Length(min=1, max=40, message="岗位名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}