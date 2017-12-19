/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.OutcontractInvoice;
import com.thinkgem.jeesite.modules.out.dao.OutcontractInvoiceDao;

/**
 * 发票登记Service
 * @author czy
 * @version 2016-12-19
 */
@Service
@Transactional(readOnly = true)
public class OutcontractInvoiceService extends CrudService<OutcontractInvoiceDao, OutcontractInvoice> {
	@Autowired
	private OutcontractInvoiceDao outcontractInvoiceDao;

	public OutcontractInvoice get(String id) {
		return super.get(id);
	}


	
	public List<OutcontractInvoice> findList(OutcontractInvoice outcontractInvoice) {
		return super.findList(outcontractInvoice);
	}
	
	public Page<OutcontractInvoice> findPage(Page<OutcontractInvoice> page, OutcontractInvoice outcontractInvoice) {
		return super.findPage(page, outcontractInvoice);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractInvoice outcontractInvoice) {
		super.save(outcontractInvoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractInvoice outcontractInvoice) {
		super.delete(outcontractInvoice);
	}


	public String getSumInvoice(String subId) {
		return outcontractInvoiceDao.getSumInvoice(subId);
	}

	public String getCountInvoice(String subId) {
		return outcontractInvoiceDao.getCountInvoice(subId);
	}

	public List<OutcontractInvoice> findListBysubIds(String subIds) {
		return outcontractInvoiceDao.findListBysubIds(subIds);
	}

	public Map<String, String> getInfo(Outcontract outcontract) {
		// TODO 自动生成的方法存根
		return dao.getInfo(outcontract);
	}
}