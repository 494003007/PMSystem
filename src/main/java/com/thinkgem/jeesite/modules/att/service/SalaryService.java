/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.att.dao.SalaryDao;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;

/**
 * 员工薪资基准Service
 * @author cdoublej
 * @version 2016-12-03
 */
@Service
@Transactional(readOnly = true)
public class SalaryService extends CrudService<SalaryDao, Salary> {
	 
	public Salary get(String id) {
		return super.get(id);
	}
	
	public List<Salary> findList(Salary salary) {
		return super.findList(salary);
	}
	
	public Page<Salary> findPage(Page<Salary> page, Salary salary) {
		return super.findPage(page, salary);
	}
	
	@Transactional(readOnly = false)
	public void save(Salary salary) {
		super.save(salary);
	}
	
	@Transactional(readOnly = false)
	public void delete(Salary salary) {
		super.delete(salary);
	}

	public int getEmptyCount(Salary salary) {
		// TODO 自动生成的方法存根
		return dao.getEmptyCount(salary);
	}

	public Page<Salary> findExaminePage(Page<Salary> page, Salary salary) {
		salary.setPage(page);
		page.setList(dao.findExamineList(salary));
		return page;
	}

	public int getExamineSalayCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineSalayCount();
	}
	@Transactional(readOnly = false)
	public void examineUpdate(Salary salary) {
		dao.examineUpdate(salary);
		
	}
 
}