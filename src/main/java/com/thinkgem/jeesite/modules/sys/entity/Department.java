/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 部门设置Entity
 * @author cdoublej
 * @version 2016-10-10
 */
public class Department extends DataEntity<Department> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 部门名称
	
	public Department() {
		super();
	}

	public Department(String id){
		super(id);
	}

	@Length(min=1, max=40, message="部门名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}