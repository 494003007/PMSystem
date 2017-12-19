/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractAccessory;
import com.thinkgem.jeesite.modules.pro.dao.ContractAccessoryDao;

/**
 * 合同附件Service
 * @author cdoublej
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class ContractAccessoryService extends CrudService<ContractAccessoryDao, ContractAccessory> {

	public ContractAccessory get(String id) {
		return super.get(id);
	}
	
	public List<ContractAccessory> findList(ContractAccessory contractAccessory) {
		return super.findList(contractAccessory);
	}
	
	public Page<ContractAccessory> findPage(Page<ContractAccessory> page, ContractAccessory contractAccessory) {
		return super.findPage(page, contractAccessory);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractAccessory contractAccessory) {
		super.save(contractAccessory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractAccessory contractAccessory) {
		super.delete(contractAccessory);
	}
	
}