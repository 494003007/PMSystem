/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.bus.entity.ContractInvoiceConfirm;
import com.thinkgem.jeesite.modules.bus.dao.ContractInvoiceConfirmDao;

/**
 * busService
 * @author cdoublej
 * @version 2016-12-20
 */
@Service
@Transactional(readOnly = true)
public class ContractInvoiceConfirmService extends CrudService<ContractInvoiceConfirmDao, ContractInvoiceConfirm> {

	public ContractInvoiceConfirm get(String id) {
		return super.get(id);
	}
	
	public List<ContractInvoiceConfirm> findList(ContractInvoiceConfirm contractInvoiceConfirm) {
		return super.findList(contractInvoiceConfirm);
	}
	
	public Page<ContractInvoiceConfirm> findPage(Page<ContractInvoiceConfirm> page, ContractInvoiceConfirm contractInvoiceConfirm) {
		return super.findPage(page, contractInvoiceConfirm);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractInvoiceConfirm contractInvoiceConfirm) {
		super.save(contractInvoiceConfirm);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractInvoiceConfirm contractInvoiceConfirm) {
		super.delete(contractInvoiceConfirm);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String invoiceId) {
		dao.deleteAll(invoiceId);
		
	}
	
}