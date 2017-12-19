/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;

/**
 * busDAO接口
 * @author cdoublej
 * @version 2016-12-20
 */
@MyBatisDao
public interface ContractInvoiceConfirmDao extends CrudDao<ContractInvoiceConfirm> {

	void deleteAll(@Param("invoiceId")String invoiceId);
	
}