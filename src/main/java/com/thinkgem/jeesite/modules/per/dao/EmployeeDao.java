/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.Employee;

/**
 * 员工DAO接口
 * @author cdoublej
 * @version 2016-10-15
 */
@MyBatisDao
public interface EmployeeDao extends CrudDao<Employee> {

	public List<Employee> employeeChoose(Employee employee);

	public List<Employee> findQuitList(Employee employee);

	public void quitUpdate(Employee employee);

	public List<Employee> findExamineQuitList(Employee employee);

	public void examineQuitUpdate(Employee employee);

	public List<Employee> findExamineEntryList(Employee employee);

	public void examineEntryUpdate(Employee employee);

	public int getExamineEntryemployeeCount();

	public int getExamineQuitemployeeCount();

	public int getOACodeCount(Employee employee);
	public int getDACodeCount(Employee employee);
}