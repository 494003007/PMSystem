/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.excel.entity.AttendanceReport;
 

/**
 * 工资报表DAO接口
 * @author cdoublej
 * @version 2016-10-26
 */
@MyBatisDao
public interface AttendanceReportDao extends CrudDao<AttendanceReport> {
	
}