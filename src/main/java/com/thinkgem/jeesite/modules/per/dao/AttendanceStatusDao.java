/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.dao;

import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;

/**
 * 考勤纠正DAO接口
 * @author cdoublej
 * @version 2016-10-28
 */
@MyBatisDao
public interface AttendanceStatusDao extends CrudDao<AttendanceStatus> {
 
	public int getPrimaryId(AttendanceStatus attendanceStatus);
	
}