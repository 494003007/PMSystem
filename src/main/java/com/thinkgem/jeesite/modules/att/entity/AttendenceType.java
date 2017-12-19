/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 出勤类型Entity
 * @author cdoublej
 * @version 2016-10-13
 */
public class AttendenceType extends DataEntity<AttendenceType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 出勤类型名称
	
	public AttendenceType() {
		super();
	}

	public AttendenceType(String id){
		super(id);
	}

	@Length(min=1, max=40, message="出勤类型名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}