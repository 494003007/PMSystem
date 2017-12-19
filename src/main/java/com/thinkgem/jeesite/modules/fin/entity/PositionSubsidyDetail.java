/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * position_subsidyEntity
 * @author czy
 * @version 2016-10-21
 */
public class PositionSubsidyDetail extends DataEntity<PositionSubsidyDetail> {
	
	private static final long serialVersionUID = 1L;
	private Integer positionTypeId;		// position_type_id
	private Double money;		// money
	private Date createTime;		// create_time
	private String createReason;		// create_reason
	private Date beginCreateTime;		// 开始 create_time
	private Date endCreateTime;		// 结束 create_time
	private PositionType positionType;
	private SysArea sysArea;
	private String sysAreaName;		// sys_area_name

	public PositionSubsidyDetail() {
		super();
	}

	public PositionSubsidyDetail(String id){
		super(id);
	}

	@NotNull(message="position_type_id不能为空")
	public Integer getPositionTypeId() {
		return positionTypeId;
	}

	public void setPositionTypeId(Integer positionTypeId) {
		this.positionTypeId = positionTypeId;
	}
	
	@NotNull(message="money不能为空")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="create_reason长度必须介于 0 和 255 之间")
	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public PositionType getPositionType() {
		return positionType;
	}

	public void setPositionType(PositionType positionType) {
		this.positionType = positionType;
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

	public String getSysAreaName() {
		return sysAreaName;
	}

	public void setSysAreaName(String sysAreaName) {
		this.sysAreaName = sysAreaName;
	}
}