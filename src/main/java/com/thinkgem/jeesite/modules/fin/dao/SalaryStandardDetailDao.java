/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.fin.entity.SalaryStandardDetail;

/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2016-10-23
 */
@MyBatisDao
public interface SalaryStandardDetailDao extends CrudDao<SalaryStandardDetail> {
	
}