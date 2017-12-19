/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;

/**
 * 考勤人员DAO接口
 * @author cdoublej
 * @version 2016-11-03
 */
@MyBatisDao
public interface AttendanceListDao extends CrudDao<AttendanceList> {

	List<AttendanceList> findAllEmployee(AttendanceList attendanceList);

	int isExist(AttendanceList attendanceList);

	void updateDelFlag(AttendanceList attendanceList);

	int getEmployeeAttendanceCount(@Param("month")Date month);
	
}