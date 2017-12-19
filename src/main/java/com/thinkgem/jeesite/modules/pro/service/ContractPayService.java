/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.dao.ContractPayDao;

/**
 * 付款Service
 * @author cdoublej
 * @version 2016-11-23
 */
@Service
@Transactional(readOnly = true)
public class ContractPayService extends CrudService<ContractPayDao, ContractPay> {
	@Autowired
	private ContractPayDao contractPayDao;
	public ContractPay get(String id) {
		return super.get(id);
	}
	
	public List<ContractPay> findList(ContractPay contractPay) {
		return super.findList(contractPay);
	}
	
	public Page<ContractPay> findPage(Page<ContractPay> page, ContractPay contractPay) {
		return super.findPage(page, contractPay);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractPay contractPay) {
		super.save(contractPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractPay contractPay) {
		super.delete(contractPay);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String contractPayId) {
		 contractPayDao.deleteAll(contractPayId);	
	}
	@Transactional(readOnly = false)
	public void insert(ContractPay contractPay) {
		contractPayDao.insert(contractPay);
	}

	public List<ContractPay> getconfirmdata(ContractPay contractPay) {
		// TODO 自动生成的方法存根
		return contractPayDao.getconfirmdata(contractPay);
	}
	@Transactional(readOnly = false)
	public void updateConfirm(ContractPay contractPay) {
		 contractPayDao.updateConfirm(contractPay);
		
	}
	@Transactional(readOnly = false)
	public void updateOutConfirmStatus(String contractConfirmid2) {
		dao.updateOutConfirmStatus(contractConfirmid2);
		
	}
	
}