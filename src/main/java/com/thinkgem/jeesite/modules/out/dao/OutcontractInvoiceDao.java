/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;
import com.thinkgem.jeesite.modules.out.entity.OutcontractInvoice;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 发票登记DAO接口
 * @author czy
 * @version 2016-12-19
 */
@MyBatisDao
public interface OutcontractInvoiceDao extends CrudDao<OutcontractInvoice> {
    String getSumInvoice(@Param("subId") String subId);

    String getCountInvoice(@Param("subId") String subId);

    List<OutcontractInvoice> findListBysubIds(@Param("subIds") String subIds);

	Map<String, String> getInfo(Outcontract outcontract);
}