/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractBudget;
import com.thinkgem.jeesite.modules.pro.dao.ContractBudgetDao;

/**
 * 预算信息生成Service
 * @author ThinkGem
 * @version 2016-11-29
 */
@Service
@Transactional(readOnly = true)
public class ContractBudgetService extends CrudService<ContractBudgetDao, ContractBudget> {

	public ContractBudget get(String id) {
		return super.get(id);
	}
	
	public List<ContractBudget> findList(ContractBudget contractBudget) {
		return super.findList(contractBudget);
	}
	
	public Page<ContractBudget> findPage(Page<ContractBudget> page, ContractBudget contractBudget) {
		return super.findPage(page, contractBudget);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractBudget contractBudget) {
		super.save(contractBudget);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractBudget contractBudget) {
		super.delete(contractBudget);
	}
	@Transactional(readOnly = false)
	public void insert(ContractBudget budget) {
		dao.insert(budget);
		
	}
	@Transactional(readOnly = false)
	public void deleteAll(String contractId) {
		dao.deleteAll(contractId);
		
	}
	
}