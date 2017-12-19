/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;

/**
 * 员工考勤DAO接口
 * @author cdoublej
 * @version 2016-11-04
 */
@MyBatisDao
public interface AttendanceListDetailDao extends CrudDao<AttendanceListDetail> {

	ArrayList<AttendanceListDetail> isexist(AttendanceListDetail attendanceListDetail2);
	 
	void attendanceupdate(AttendanceListDetail attendanceListDetail);

	List<AttendanceListDetail> findAllAttendance( AttendanceListDetail attendanceListDetail);

	List<AttendanceListDetail> findExaminAttendance(AttendanceListDetail attendanceListDetail);

	int getExamineAttendanceCount();
	
}