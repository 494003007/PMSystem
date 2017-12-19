/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ContractCredentials;
import com.thinkgem.jeesite.modules.pro.dao.ContractCredentialsDao;

/**
 * 合同证件Service
 * @author czy
 * @version 2016-11-05
 */
@Service
@Transactional(readOnly = true)
public class ContractCredentialsService extends CrudService<ContractCredentialsDao, ContractCredentials> {

	public ContractCredentials get(String id) {
		return super.get(id);
	}
	
	public List<ContractCredentials> findList(ContractCredentials contractCredentials) {
		return super.findList(contractCredentials);
	}
	
	public Page<ContractCredentials> findPage(Page<ContractCredentials> page, ContractCredentials contractCredentials) {
		return super.findPage(page, contractCredentials);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractCredentials contractCredentials) {
		super.save(contractCredentials);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractCredentials contractCredentials) {
		super.delete(contractCredentials);
	}
	
}