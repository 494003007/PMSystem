/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;

/**
 * 回款登记DAO接口
 * @author cdoublej
 * @version 2016-11-25
 */
@MyBatisDao
public interface ReturnRegisterDao extends CrudDao<ReturnRegister> {

	int isExit(ReturnRegister returnRegister);

	List<Map<String, String>> findAllInvoice(ReturnRegister returnRegister);

	ArrayList<ReturnRegister> findAllToInvoice(Invoice invoice);
	
}