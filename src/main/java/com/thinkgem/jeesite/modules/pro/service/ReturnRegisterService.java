/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.pro.dao.ReturnRegisterDao;

/**
 * 回款登记Service
 * @author cdoublej
 * @version 2016-11-25
 */
@Service
@Transactional(readOnly = true)
public class ReturnRegisterService extends CrudService<ReturnRegisterDao, ReturnRegister> {
	@Autowired
	private ReturnRegisterDao registerDao;
	public ReturnRegister get(String id) {
		return super.get(id);
	}
	
	public List<ReturnRegister> findList(ReturnRegister returnRegister) {
		return super.findList(returnRegister);
	}
	
	public Page<ReturnRegister> findPage(Page<ReturnRegister> page, ReturnRegister returnRegister) {
		return super.findPage(page, returnRegister);
	}
	
	@Transactional(readOnly = false)
	public void save(ReturnRegister returnRegister) {
		super.save(returnRegister);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReturnRegister returnRegister) {
		super.delete(returnRegister);
	}

	public int isExit(ReturnRegister returnRegister) {
		// TODO 自动生成的方法存根
		return registerDao.isExit(returnRegister);
	}

	public List<Map<String, String>> findAllInvoice(ReturnRegister returnRegister) {
		// TODO 自动生成的方法存根
		return dao.findAllInvoice(returnRegister);
	}

	public ArrayList<ReturnRegister> findAllToInvoice(Invoice invoice) {
		// TODO 自动生成的方法存根
		return dao.findAllToInvoice(invoice);
	}
	
}