/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 树结构生成Entity
 * @author ThinkGem
 * @version 2015-04-06
 */
public class SysArea extends TreeEntity<SysArea> {
	
	private static final long serialVersionUID = 1L;
	private SysArea parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private Integer sort;		// 排序
	
	public SysArea() {
		super();
	}

	public SysArea(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public SysArea getParent() {
		return parent;
	}

	public void setParent(SysArea parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	@ExcelField(title="所属区域", align=2, sort=10)
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}