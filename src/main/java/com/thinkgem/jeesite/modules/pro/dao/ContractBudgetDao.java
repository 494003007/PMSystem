/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ContractBudget;

/**
 * 预算信息生成DAO接口
 * @author ThinkGem
 * @version 2016-11-29
 */
@MyBatisDao
public interface ContractBudgetDao extends CrudDao<ContractBudget> {

	void deleteAll(@Param("contractId")String contractId);
	
}