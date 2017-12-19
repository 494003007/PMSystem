/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ContractType;

/**
 * 合同类型DAO接口
 * @author cdoublej
 * @version 2016-10-08
 */
@MyBatisDao
public interface ContractTypeDao extends CrudDao<ContractType> {
	
}