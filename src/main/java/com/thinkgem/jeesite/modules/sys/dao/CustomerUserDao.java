/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;

/**
 * 客户DAO接口
 * @author cdoublej
 * @version 2016-12-03
 */
@MyBatisDao
public interface CustomerUserDao extends CrudDao<CustomerUser> {

	void deleteAll(@Param("customerid")String customerid);
	
}