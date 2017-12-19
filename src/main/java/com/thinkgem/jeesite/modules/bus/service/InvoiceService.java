/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.bus.entity.Invoice;
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.bus.dao.InvoiceDao;

/**
 * 发票Service
 * @author fy
 * @version 2016-11-22
 * @param <InvoiceReturnRegister>
 */
@Service
@Transactional(readOnly = true)
public class InvoiceService<InvoiceReturnRegister> extends CrudService<InvoiceDao, Invoice> {

	public Invoice get(String id) {
		return super.get(id);
	}
	
	public List<Invoice> findList(Invoice invoice) {
		return super.findList(invoice);
	}
	
	public Page<Invoice> findPage(Page<Invoice> page, Invoice invoice) {
		return super.findPage(page, invoice);
	}
	
	@Transactional(readOnly = false)
	public void save(Invoice invoice) {
		super.save(invoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(Invoice invoice) {
		super.delete(invoice);
	}

	public Contract getajaxinformatin(String contractid) {
		// TODO 自动生成的方法存根
		return dao.getajaxinformatin(contractid);
	}

	public Page<Invoice> findPannelPage(Page<Invoice> page, Invoice invoice) {
		invoice.setPage(page);
		page.setList(dao.findPannel(invoice));
		return page;
	}

	public Page<Invoice> findSelectPage(Page<Invoice> page, Invoice invoice) {
		invoice.setPage(page);
		page.setList(dao.findSelect(invoice));
		return page;
	}
	@Transactional(readOnly = false)
	public void registerUpdate(Invoice invoice) {
		dao.registerUpdate(invoice);
		
	}
	@Transactional(readOnly = false)
	public void updateStatus(String invoiceId) {
		dao.updateStatus(invoiceId);
		
	}

	public int getInvoiceNotifyCount(String string) {
		// TODO 自动生成的方法存根
		return dao.getInvoiceNotifyCount(string);
	}

	
}