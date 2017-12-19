/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractCost;
import com.thinkgem.jeesite.modules.pro.dao.ContractCostDao;

/**
 * 成本录入Service
 * @author ThinkGem
 * @version 2016-12-05
 */
@Service
@Transactional(readOnly = true)
public class ContractCostService extends CrudService<ContractCostDao, ContractCost> {

	public ContractCost get(String id) {
		return super.get(id);
	}
	
	public List<ContractCost> findList(ContractCost contractCost) {
		return super.findList(contractCost);
	}
	
	public Page<ContractCost> findPage(Page<ContractCost> page, ContractCost contractCost) {
		return super.findPage(page, contractCost);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractCost contractCost) {
		super.save(contractCost);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractCost contractCost) {
		super.delete(contractCost);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String string, String projectId) {
		dao.deleteAll(string,projectId);
		
	}
	@Transactional(readOnly = false)
	public void insert(ContractCost contractCost) {
		dao.insert(contractCost);
		
	}
	
}