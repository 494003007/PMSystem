/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.dao;

 

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bus.entity.InvoiceReturnRegister;
 

/**
 * 发票DAO接口
 * @author fy
 * @version 2016-11-22
 */
@MyBatisDao
public interface InvoiceReturnRegisterDao extends CrudDao<InvoiceReturnRegister> {

	void delectAllRelation(String invoiceId);

	 
	
}