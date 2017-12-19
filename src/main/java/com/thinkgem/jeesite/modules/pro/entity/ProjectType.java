/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目类型Entity
 * @author cdoublej
 * @version 2016-10-13
 */
public class ProjectType extends DataEntity<ProjectType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目类型名称
	
	public ProjectType() {
		super();
	}

	public ProjectType(String id){
		super(id);
	}

	@Length(min=1, max=40, message="项目类型名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}