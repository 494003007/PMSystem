/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;

/**
 * 项目进度DAO接口
 * @author cdoublej
 * @version 2016-11-30
 */
@MyBatisDao
public interface ContractConfirmDao extends CrudDao<ContractConfirm> {

	ContractConfirm getconfirm(ContractConfirm confirm);

	void deleteconfirm(ContractConfirm confirm);

	ArrayList<ContractConfirm> getAllconfirm(ContractConfirm contractConfirm);

	int getConfirmCount(@Param("tdate") String tdate);

	void updateExamine(ContractConfirm contractConfirm);

	int getExamineConfirmCount();

	List<ContractConfirm> getAllProjectConfirm(ContractConfirm contractConfirm);
	
}