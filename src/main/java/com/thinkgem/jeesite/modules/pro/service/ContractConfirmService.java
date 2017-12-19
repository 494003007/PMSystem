/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractConfirm;
import com.thinkgem.jeesite.modules.pro.dao.ContractConfirmDao;

/**
 * 项目进度Service
 * @author cdoublej
 * @version 2016-11-30
 */
@Service
@Transactional(readOnly = true)
public class ContractConfirmService extends CrudService<ContractConfirmDao, ContractConfirm> {
	@Autowired
	private ContractConfirmDao confirmDao;
	public ContractConfirm get(String id) {
		return super.get(id);
	}
	
	public List<ContractConfirm> findList(ContractConfirm contractConfirm) {
		return super.findList(contractConfirm);
	}
	
	public Page<ContractConfirm> findPage(Page<ContractConfirm> page, ContractConfirm contractConfirm) {
		return super.findPage(page, contractConfirm);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractConfirm contractConfirm) {
		super.save(contractConfirm);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractConfirm contractConfirm) {
		super.delete(contractConfirm);
	}

	public ContractConfirm getconfirm(ContractConfirm confirm) {
		 
		return confirmDao.getconfirm(confirm);
	}
	@Transactional(readOnly = false)
	public void deleteconfirm(ContractConfirm confirm) {
		confirmDao.deleteconfirm(confirm);
		
	}

	public ArrayList<ContractConfirm> getAllconfirm(
			ContractConfirm contractConfirm) {
		// TODO 自动生成的方法存根
		return 	confirmDao.getAllconfirm(contractConfirm);
	}

	public int getConfirmCount(String templeDate) {
		// TODO 自动生成的方法存根
		return dao.getConfirmCount(templeDate);
	}
	@Transactional(readOnly = false)
	public void updateExamine(ContractConfirm contractConfirm) {
		dao.updateExamine(contractConfirm) ;
		
	}

	public int getExamineConfirmCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineConfirmCount();
	}
	@Transactional(readOnly = false)
	public void insert(ContractConfirm contractConfirm) {
		dao.insert(contractConfirm);
		
	}
	@Transactional(readOnly = false)
	public void update(ContractConfirm contractConfirm) {
		dao.update(contractConfirm);
		
	}

	public Page<ContractConfirm> findAllProjectConfirmPage(
			Page<ContractConfirm> page, ContractConfirm contractConfirm) {
		contractConfirm.setPage(page);
		page.setList(dao.getAllProjectConfirm(contractConfirm));
		return page;
	}
 
}