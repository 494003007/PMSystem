/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractType;
import com.thinkgem.jeesite.modules.pro.dao.ContractTypeDao;

/**
 * 合同类型Service
 * @author cdoublej
 * @version 2016-10-08
 */
@Service
@Transactional(readOnly = true)
public class ContractTypeService extends CrudService<ContractTypeDao, ContractType> {

	public ContractType get(String id) {
		return super.get(id);
	}
	
	public List<ContractType> findList(ContractType contractType) {
		return super.findList(contractType);
	}
	
	public Page<ContractType> findPage(Page<ContractType> page, ContractType contractType) {
		return super.findPage(page, contractType);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractType contractType) {
		super.save(contractType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractType contractType) {
		super.delete(contractType);
	}
	
}