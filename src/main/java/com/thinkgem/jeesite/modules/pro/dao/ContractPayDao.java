/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;

/**
 * 付款DAO接口
 * @author cdoublej
 * @version 2016-11-23
 */
@MyBatisDao
public interface ContractPayDao extends CrudDao<ContractPay> {

	void deleteAll(@Param("contractPayId") String contractPayId);

	List<ContractPay> getconfirmdata(ContractPay contractPay);

	void updateConfirm(ContractPay contractPay);

	void updateOutConfirmStatus(@Param("contractConfirmid2")String contractConfirmid2);
	
}