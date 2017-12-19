/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.GetName;

/**
 * 员工类型Entity
 * @author cdoublej
 * @version 2016-10-09
 */
public class EmployeeType extends DataEntity<EmployeeType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类型名称
	private String remarks;		// 描述
	
	public EmployeeType() {
		super();
	}

	public EmployeeType(String id){
		super(id);
	}
	@ExcelField(title="员工类型", align=2)
	@Length(min=1, max=20, message="类型名称长度必须介于 1 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="描述长度必须介于 0 和 50 之间")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}