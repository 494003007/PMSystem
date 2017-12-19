/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.dao;

import java.util.ArrayList;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.gmo.entity.ExamineAttendance;


/**
 * 考勤DAO接口
 * @author cdoublej
 * @version 2016-10-19
 */
@MyBatisDao
public interface ExamineAttendanceDao extends CrudDao<ExamineAttendance> {

	public ArrayList findCoeifficientList(int projectId, String statr_date, String end_date);
	
}