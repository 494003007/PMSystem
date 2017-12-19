/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;

/**
 * 员工薪资基准DAO接口
 * @author cdoublej
 * @version 2016-12-03
 */
@MyBatisDao
public interface SalaryDao extends CrudDao<Salary> {

	int getEmptyCount(Salary salary);

	List<Salary> findExamineList(Salary salary);

	int getExamineSalayCount();

	void examineUpdate(Salary salary);
 
}