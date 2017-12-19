/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;

/**
 * 考勤DAO接口
 * @author cdoublej
 * @version 2016-10-19
 */
@MyBatisDao
public interface ProjectAttendanceDao extends CrudDao<ProjectAttendance> {

	public ArrayList findCoeifficientList(int projectId, String statr_date, String end_date);

	public void addThreeAttendance(ProjectAttendance projectAttendance);

	public int getAttendanceCount(ProjectAttendance projectAttendance);

	public void updatePerformanceCoefficient(@Param("projectAttendanceId")String projectAttendanceId,
			@Param("performanceCoefficient")String performanceCoefficient);

	public void updateStatus(@Param("projectAttendanceId")String projectAttendanceId,@Param("status")String status);

	public void updateAddAttendance(@Param("projectAttendanceId")String string,@Param("addAttendanceSum")String string2);
	
}