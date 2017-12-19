/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.sys.entity.Role;

/**
 * 员工类型DAO接口
 * @author cdoublej
 * @version 2016-10-09
 */
@MyBatisDao
public interface EmployeeTypeDao extends CrudDao<EmployeeType> {

	public List<EmployeeType> findAllList();
	
}