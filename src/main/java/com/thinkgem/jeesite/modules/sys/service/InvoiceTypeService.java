/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceType;
import com.thinkgem.jeesite.modules.sys.dao.InvoiceTypeDao;

/**
 * 发票类型Service
 * @author cdoublej
 * @version 2016-11-20
 */
@Service
@Transactional(readOnly = true)
public class InvoiceTypeService extends CrudService<InvoiceTypeDao, InvoiceType> {

	public InvoiceType get(String id) {
		return super.get(id);
	}
	
	public List<InvoiceType> findList(InvoiceType invoiceType) {
		return super.findList(invoiceType);
	}
	
	public Page<InvoiceType> findPage(Page<InvoiceType> page, InvoiceType invoiceType) {
		return super.findPage(page, invoiceType);
	}
	
	@Transactional(readOnly = false)
	public void save(InvoiceType invoiceType) {
		super.save(invoiceType);
	}
	
	@Transactional(readOnly = false)
	public void delete(InvoiceType invoiceType) {
		super.delete(invoiceType);
	}
	
}