/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.dao.DepartmentDao;

/**
 * 部门设置Service
 * @author cdoublej
 * @version 2016-10-10
 */
@Service
@Transactional(readOnly = true)
public class DepartmentService extends CrudService<DepartmentDao, Department> {

	public Department get(String id) {
		return super.get(id);
	}
	
	public List<Department> findList(Department department) {
		return super.findList(department);
	}
	
	public Page<Department> findPage(Page<Department> page, Department department) {
		return super.findPage(page, department);
	}
	
	@Transactional(readOnly = false)
	public void save(Department department) {
		super.save(department);
	}
	
	@Transactional(readOnly = false)
	public void delete(Department department) {
		super.delete(department);
	}
	
}