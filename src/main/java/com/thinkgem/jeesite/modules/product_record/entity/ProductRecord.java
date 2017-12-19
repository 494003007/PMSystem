/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * product_recordEntity
 * @author cdoublej
 * @version 2016-09-30
 */
public class ProductRecord extends DataEntity<ProductRecord> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 修改人姓名
	private Date time;		// 时间
	private Date createtime;		// 时间
	private String descript;		// 描述
	
	public ProductRecord() {
		super();
	}

	public ProductRecord(String id){
		super(id);
	}

	@Length(min=0, max=20, message="修改人姓名长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	
}