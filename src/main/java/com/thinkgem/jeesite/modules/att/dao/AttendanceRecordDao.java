/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;

/**
 * 考勤DAO接口
 * @author cdoublej
 * @version 2016-10-18
 */
@MyBatisDao
public interface AttendanceRecordDao extends CrudDao<AttendanceRecord> {
 
	public List<AttendanceRecord> findAttendanceList(String porject_allowance_detail_id);

	public AttendanceRecord isAttendanceRecord(@Param("employeeID") String employeeID,@Param("attendance_date")  String attendance_date, @Param("projectid") int projectid);

}