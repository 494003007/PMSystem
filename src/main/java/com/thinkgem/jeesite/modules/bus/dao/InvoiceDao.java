/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.pro.entity.Contract;

/**
 * 发票DAO接口
 * @author fy
 * @version 2016-11-22
 */
@MyBatisDao
public interface InvoiceDao extends CrudDao<Invoice> {

	Contract getajaxinformatin(@Param("contractid")String contractid);


	List<Invoice> findPannel(Invoice invoice);

	List<Invoice> findSelect(Invoice invoice);

	void registerUpdate(Invoice invoice);

	void delectAllRelation(@Param("invoiceId")String invoiceId);

	void updateStatus(String invoiceId);

	int getInvoiceNotifyCount(String status);
	
}