/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.dao.EmployeeDao;

/**
 * 员工Service
 * @author cdoublej
 * @version 2016-10-15
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService extends CrudService<EmployeeDao, Employee> {
	@Autowired
	private EmployeeDao employeeDao;
	public Employee get(String id) {
		return super.get(id);
	}

	public List<Employee> findList(Employee employee) {
		return super.findList(employee);
	}

	public Page<Employee> findPage(Page<Employee> page, Employee employee) {
		return super.findPage(page, employee);
	}

	public Page<Employee> findEmployeeTemple(Page<Employee> page, Employee employee) {
		employee.setPage(page);
		page.setList(employeeDao.employeeChoose(employee));
		return page;
	}


	@Transactional(readOnly = false)
	public void save(Employee employee) {
		super.save(employee);
	}

	@Transactional(readOnly = false)
	public void delete(Employee employee) {
		super.delete(employee);
	}

	public Page<Employee> findquitPage(Page<Employee> page, Employee employee) {
		employee.setPage(page);
		page.setList(employeeDao.findQuitList(employee));
		return page;
	}
	@Transactional(readOnly = false)
	public void quitUpdate(Employee employee) {
		dao.quitUpdate(employee);
		
	}

	public Page<Employee> findexaminequitPage(Page<Employee> page,
			Employee employee) {
		employee.setPage(page);
		page.setList(employeeDao.findExamineQuitList(employee));
		return page;
	}
	@Transactional(readOnly = false)
	public void examineQuitUpdate(Employee employee) {
		dao.examineQuitUpdate(employee);
		
	}

	public Page<Employee> findexamineentryPage(Page<Employee> page,
			Employee employee) {
		employee.setPage(page);
		page.setList(employeeDao.findExamineEntryList(employee));
		return page;
	}
	@Transactional(readOnly = false)
	public void examineEntryUpdate(Employee employee) {
	    dao.examineEntryUpdate(employee);
		
	}
	public int getExamineEntryemployeeCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineEntryemployeeCount();
	}

	public int getExamineQuitemployeeCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineQuitemployeeCount();
	}

	public int getOACodeCount(Employee employee) {
		// TODO 自动生成的方法存根
		return dao.getOACodeCount(employee);
	}

	public int getDACodeCount(Employee employee) {
		// TODO 自动生成的方法存根
		return dao.getDACodeCount(employee);
	}
}