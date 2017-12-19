/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤纠正Entity
 * @author cdoublej
 * @version 2016-10-28
 */
public class AttendanceStatus extends DataEntity<AttendanceStatus> {
	private int id;
	int anInt;
	private static final long serialVersionUID = 1L;
	private String finIsCorrect;		// 是否纠正
	private Date correctDate;		// 纠正时间
	private String correctReason;		// 纠正原因
	private String gmoIsExamine;		// 是否审核
	private Date examineDate;		// 审核时间
	private String examineReason;		// 审核原因
	
	public AttendanceStatus() {
		super();
	}

	public AttendanceStatus(String id){
		super(id);
	}

	@Length(min=0, max=10, message="是否纠正长度必须介于 0 和 10 之间")
	public String getFinIsCorrect() {
		return finIsCorrect;
	}

	public void setFinIsCorrect(String finIsCorrect) {
		this.finIsCorrect = finIsCorrect;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCorrectDate() {
		return correctDate;
	}

	public void setCorrectDate(Date correctDate) {
		this.correctDate = correctDate;
	}
	
	@Length(min=0, max=255, message="纠正原因长度必须介于 0 和 255 之间")
	public String getCorrectReason() {
		return correctReason;
	}

	public void setCorrectReason(String correctReason) {
		this.correctReason = correctReason;
	}
	
	@Length(min=0, max=10, message="是否审核长度必须介于 0 和 10 之间")
	public String getGmoIsExamine() {
		return gmoIsExamine;
	}

	public void setGmoIsExamine(String gmoIsExamine) {
		this.gmoIsExamine = gmoIsExamine;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}
	
	@Length(min=0, max=255, message="审核原因长度必须介于 0 和 255 之间")
	public String getExamineReason() {
		return examineReason;
	}

	public void setExamineReason(String examineReason) {
		this.examineReason = examineReason;
	}
	
}