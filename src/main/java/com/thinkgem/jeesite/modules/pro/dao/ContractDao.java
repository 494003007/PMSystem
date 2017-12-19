/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.Contract;

/**
 * 合同DAO接口
 * @author cdoublej
 * @version 2016-11-20
 */
@MyBatisDao
public interface ContractDao extends CrudDao<Contract> {

	List<Contract> findFileList(Contract contract);

	List<Contract> findworkconfirm(Contract contract);

	Contract getconfirm(Contract contract);

	int getCount(@Param("projectid")String projectid);

	int getContractFileCount();

	List<Contract> findexamineworkconfirm(Contract contract);

 
}