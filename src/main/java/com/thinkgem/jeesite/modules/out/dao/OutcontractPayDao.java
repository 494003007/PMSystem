/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;

/**
 * 外包付款DAO接口
 * @author LKY
 * @version 2016-11-30
 */
@MyBatisDao
public interface OutcontractPayDao extends CrudDao<OutcontractPay> {

	List<OutcontractPay> findSelect(OutcontractPay outcontractPay);

	OutcontractPay getAjaxDate(@Param("outcontractPayId")String outcontractPayId);

	void deleteAll(String orderId);
	void delete2(Outcontract outcontract);
	void delete3(Outcontract outcontract);
	
}