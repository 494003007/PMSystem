/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.bus.dao.InvoiceDao;
import com.thinkgem.jeesite.modules.bus.dao.InvoiceReturnRegisterDao;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.bus.entity.InvoiceReturnRegister;

/**
 * 发票Service
 * @author fy
 * @version 2016-11-22
 * @param <InvoiceReturnRegister>
 */
@Service
@Transactional(readOnly = true)
public class InvoiceReturnRegisterService extends CrudService<InvoiceReturnRegisterDao, InvoiceReturnRegister> {

	public InvoiceReturnRegister get(String id) {
		return super.get(id);
	}
	
	public List<InvoiceReturnRegister> findList(InvoiceReturnRegister invoice) {
		return super.findList(invoice);
	}
	
	public Page<InvoiceReturnRegister> findPage(Page<InvoiceReturnRegister> page, InvoiceReturnRegister invoice) {
		return super.findPage(page, invoice);
	}
	
	@Transactional(readOnly = false)
	public void save(InvoiceReturnRegister invoice) {
		super.save(invoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(InvoiceReturnRegister invoice) {
		super.delete(invoice);
	}
	@Transactional(readOnly = false)
	public void delectAllRelation(String invoiceId) {
		dao.delectAllRelation(invoiceId);
		
	}
	@Transactional(readOnly = false)
	public void insert(InvoiceReturnRegister invoiceReturnRegister) {
		dao.insert(invoiceReturnRegister);
		
	}

 
}