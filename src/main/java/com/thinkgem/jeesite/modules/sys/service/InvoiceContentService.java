/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceContent;
import com.thinkgem.jeesite.modules.sys.dao.InvoiceContentDao;

/**
 * 开票内容Service
 * @author cdoublejj
 * @version 2016-11-20
 */
@Service
@Transactional(readOnly = true)
public class InvoiceContentService extends CrudService<InvoiceContentDao, InvoiceContent> {

	public InvoiceContent get(String id) {
		return super.get(id);
	}
	
	public List<InvoiceContent> findList(InvoiceContent invoiceContent) {
		return super.findList(invoiceContent);
	}
	
	public Page<InvoiceContent> findPage(Page<InvoiceContent> page, InvoiceContent invoiceContent) {
		return super.findPage(page, invoiceContent);
	}
	
	@Transactional(readOnly = false)
	public void save(InvoiceContent invoiceContent) {
		super.save(invoiceContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(InvoiceContent invoiceContent) {
		super.delete(invoiceContent);
	}
	
}