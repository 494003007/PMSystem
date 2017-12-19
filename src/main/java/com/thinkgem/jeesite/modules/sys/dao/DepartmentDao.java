/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.entity.Role;

/**
 * 部门设置DAO接口
 * @author cdoublej
 * @version 2016-10-10
 */
@MyBatisDao
public interface DepartmentDao extends CrudDao<Department> {
	public List<Department> findAllList();
}