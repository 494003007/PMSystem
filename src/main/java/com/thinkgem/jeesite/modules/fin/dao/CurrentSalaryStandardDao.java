/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;

/**
 * current_salary_standardDAO接口
 * @author czy
 * @version 2016-10-22
 */
@MyBatisDao
public interface CurrentSalaryStandardDao extends CrudDao<CurrentSalaryStandard> {
	
}