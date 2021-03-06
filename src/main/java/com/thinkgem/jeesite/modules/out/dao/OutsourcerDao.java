/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;

/**
 * 外包商管理DAO接口
 * @author LKY
 * @version 2016-11-16
 */
@MyBatisDao
public interface OutsourcerDao extends CrudDao<Outsourcer> {
	
}