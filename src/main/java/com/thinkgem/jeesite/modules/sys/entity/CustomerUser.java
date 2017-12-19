/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户Entity
 * @author cdoublej
 * @version 2016-12-03
 */
public class CustomerUser extends DataEntity<CustomerUser> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String moblie;		// moblie
	private String sex;		// sex
	private Integer customerId;
	public CustomerUser() {
		super();
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public CustomerUser(String id){
		super(id);
	}

	@Length(min=0, max=20, message="name长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="moblie长度必须介于 0 和 20 之间")
	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
	@Length(min=0, max=10, message="sex长度必须介于 0 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}