/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 证书类型Entity
 * @author cdoublej
 * @version 2016-10-09
 */
public class CertificatesType extends DataEntity<CertificatesType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 证书类型名称
	private Integer allowance;		// 补贴标准
	private String description;		// 描述
	
	public CertificatesType() {
		super();
	}

	public CertificatesType(String id){
		super(id);
	}

	@Length(min=1, max=80, message="证书类型名称长度必须介于 1 和 80 之间")
	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	@NotNull(message="补贴标准不能为空")
	public Integer getAllowance() {
		return allowance;
	}

	public void setAllowance(Integer allowance) {
		this.allowance = allowance;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}
	
}