/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 外包附件Entity
 * @author LKY
 * @version 2016-11-30
 */
public class OutcontractAccessory extends DataEntity<OutcontractAccessory> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// 附件地址
	private Date creatDate;		// creat_date
	private Integer outcontractId;		// 外包合同编号
	
	public OutcontractAccessory() {
		super();
	}

	public OutcontractAccessory(String id){
		super(id);
	}

	@Length(min=0, max=50, message="附件地址长度必须介于 0 和 50 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	
	public Integer getOutcontractId() {
		return outcontractId;
	}

	public void setOutcontractId(Integer outcontractId) {
		this.outcontractId = outcontractId;
	}
	
}