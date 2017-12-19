/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;

/**
 * 工资报表DAO接口
 * @author cdoublej
 * @version 2016-10-26
 */
@MyBatisDao
public interface SalaryReportDao extends CrudDao<SalaryReport> {

	List<SalaryReport> findTempleList(SalaryReport salary);

	List<SalaryReport> salaryExport(SalaryReport salaryReport);
	
}