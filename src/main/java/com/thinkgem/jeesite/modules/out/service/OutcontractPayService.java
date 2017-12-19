/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;
import com.thinkgem.jeesite.modules.out.dao.OutcontractPayDao;

/**
 * 外包付款Service
 * @author LKY
 * @version 2016-11-30
 */
@Service
@Transactional(readOnly = true)
public class OutcontractPayService extends CrudService<OutcontractPayDao, OutcontractPay> {

	public OutcontractPay get(String id) {
		return super.get(id);
	}
	
	public List<OutcontractPay> findList(OutcontractPay outcontractPay) {
		return super.findList(outcontractPay);
	}
	
	public Page<OutcontractPay> findPage(Page<OutcontractPay> page, OutcontractPay outcontractPay) {
		return super.findPage(page, outcontractPay);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractPay outcontractPay) {
		super.save(outcontractPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractPay outcontractPay) {
		super.delete(outcontractPay);
	}

	public Page<OutcontractPay> findSelectPage( Page<OutcontractPay> page, OutcontractPay outcontractPay) {
		// TODO 自动生成的方法存根
		outcontractPay.setPage(page);
		page.setList(dao.findSelect(outcontractPay));
		return page;
	}

	public OutcontractPay getAjaxDate(String outcontractPayId) {
		// TODO 自动生成的方法存根
		return dao.getAjaxDate(outcontractPayId);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String orderId) {
		dao.deleteAll(orderId);
		
	}
	@Transactional(readOnly = false)
	public void insert(OutcontractPay outcontractPay) {
		dao.insert(outcontractPay);
		
	}
	@Transactional(readOnly = false)
	public void delete2(Outcontract outcontract) {
		dao.delete2(outcontract);
		
	}
	@Transactional(readOnly = false)
	public void delete3(Outcontract outcontract) {
		dao.delete3(outcontract);
		
	}
	
}