/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包商管理Entity
 * @author LKY
 * @version 2016-11-16
 */
public class Outsourcer extends DataEntity<Outsourcer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 联系人名字
	private String sex;		// 联系人性别
	private String email;		// 邮箱
	private String moblie;		// 联系号码
	 
	private String companyName;		// 公司名称
	private String companyAddress;		// 公司地址
	private Date registerDate;		// 注册时间
	private String legalRepresentative;		// 法人代表
	private Double registerMoney;		// 注册资金
	private String scope;		// 经营范围
	private String bankType;		// 开户行
	private String bankAccount;		// 开户账号
	private String bankIdentityCard;		// 开户身份证
	private String bankIdentityName;		// 开户名
	private Date createTime;		// 创建时间
	private String tax;
	

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Outsourcer() {
		super();
	}

	public Outsourcer(String id){
		super(id);
	}

	@Length(min=0, max=50, message="联系人名字长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="联系人性别长度必须介于 0 和 50 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=50, message="邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=50, message="联系号码长度必须介于 0 和 50 之间")
	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
 
	@Length(min=0, max=50, message="公司名称长度必须介于 0 和 50 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=50, message="公司地址长度必须介于 0 和 50 之间")
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Length(min=0, max=50, message="法人代表长度必须介于 0 和 50 之间")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	public Double getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(Double registerMoney) {
		this.registerMoney = registerMoney;
	}
	
	@Length(min=0, max=50, message="经营范围长度必须介于 0 和 50 之间")
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=0, max=50, message="开户行长度必须介于 0 和 50 之间")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	@Length(min=0, max=50, message="开户账号长度必须介于 0 和 50 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=50, message="开户身份证长度必须介于 0 和 50 之间")
	public String getBankIdentityCard() {
		return bankIdentityCard;
	}

	public void setBankIdentityCard(String bankIdentityCard) {
		this.bankIdentityCard = bankIdentityCard;
	}
	
	@Length(min=0, max=50, message="开户名长度必须介于 0 和 50 之间")
	public String getBankIdentityName() {
		return bankIdentityName;
	}

	public void setBankIdentityName(String bankIdentityName) {
		this.bankIdentityName = bankIdentityName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}