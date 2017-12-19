/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.dao.EmployeeTypeDao;

/**
 * 员工类型Service
 * @author cdoublej
 * @version 2016-10-09
 */
@Service
@Transactional(readOnly = true)
public class EmployeeTypeService extends CrudService<EmployeeTypeDao, EmployeeType> {

	public EmployeeType get(String id) {
		return super.get(id);
	}
	
	public List<EmployeeType> findList(EmployeeType employeeType) {
		return super.findList(employeeType);
	}
	
	public Page<EmployeeType> findPage(Page<EmployeeType> page, EmployeeType employeeType) {
		return super.findPage(page, employeeType);
	}
	
	@Transactional(readOnly = false)
	public void save(EmployeeType employeeType) {
		super.save(employeeType);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmployeeType employeeType) {
		super.delete(employeeType);
	}
	
}