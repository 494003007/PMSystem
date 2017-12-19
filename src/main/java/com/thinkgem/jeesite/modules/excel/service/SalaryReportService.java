/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;
import com.thinkgem.jeesite.modules.excel.dao.SalaryReportDao;

/**
 * 工资报表Service
 * @author cdoublej
 * @version 2016-10-26
 */
@Service
@Transactional(readOnly = true)
public class SalaryReportService extends CrudService<SalaryReportDao, SalaryReport> {

	public SalaryReport get(String id) {
		return super.get(id);
	}
	
	public List<SalaryReport> findList(SalaryReport salaryReport) {
		return super.findList(salaryReport);
	}
	
	public Page<SalaryReport> findPage(Page<SalaryReport> page, SalaryReport salaryReport) {
		return super.findPage(page, salaryReport);
	}
	
	@Transactional(readOnly = false)
	public void save(SalaryReport salaryReport) {
		super.save(salaryReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(SalaryReport salaryReport) {
		super.delete(salaryReport);
	}

	public Page<SalaryReport> findTemplePage(Page<SalaryReport> page, SalaryReport salary) {
		salary.setPage(page);
		page.setList(dao.findTempleList(salary));
		return page;
	}

	public List<SalaryReport> salaryExport(SalaryReport salaryReport) {
		// TODO 自动生成的方法存根
		return dao.salaryExport(salaryReport);
	}
	
}