/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Customer;

/**
 * 客户DAO接口
 * @author cdoublejj
 * @version 2016-11-07
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {

	List<Customer> isTrueCompany(@Param("returnCompany")String returnCompany);
	
}