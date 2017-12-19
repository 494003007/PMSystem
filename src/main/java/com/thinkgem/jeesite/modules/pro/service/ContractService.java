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
import com.thinkgem.jeesite.modules.pro.entity.Contract;
import com.thinkgem.jeesite.modules.pro.dao.ContractDao;

/**
 * 合同Service
 * @author cdoublej
 * @version 2016-11-20
 */
@Service
@Transactional(readOnly = true)
public class ContractService extends CrudService<ContractDao, Contract> {
	@Autowired
	ContractDao contractDao;
	public Contract get(String id) {
		return super.get(id);
	}
	
	public List<Contract> findList(Contract contract) {
		return super.findList(contract);
	}
	
	public Page<Contract> findPage(Page<Contract> page, Contract contract) {
		return super.findPage(page, contract);
	}
	
	@Transactional(readOnly = false)
	public void save(Contract contract) {
		super.save(contract);
	}
	
	@Transactional(readOnly = false)
	public void delete(Contract contract) {
		super.delete(contract);
	}

	public Page<Contract> findFilePage(Page<Contract> page, Contract contract) {
		contract.setPage(page);
		page.setList(contractDao.findFileList(contract));
		return page;
	}

	public Page<Contract> findworkconfirmPage(Page<Contract> page,Contract contract) {
		contract.setPage(page);
		page.setList(contractDao.findworkconfirm(contract));
		return page;
	}

	public Contract getconfirm(Contract contract) {
		// TODO 自动生成的方法存根
		return contractDao.getconfirm(contract);
	}

	public  int getCount(String projectid) {
		// TODO 自动生成的方法存根
		return contractDao.getCount(projectid);
	}

	public int getContractFileCount() {
		// TODO 自动生成的方法存根
		return dao.getContractFileCount();
	}

	public Page<Contract> findexamineworkconfirmPage(Page<Contract> page,
			Contract contract) {
		contract.setPage(page);
		page.setList(contractDao.findexamineworkconfirm(contract));
		return page;
	}

 

}