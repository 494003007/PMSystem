/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.Credentials;
import com.thinkgem.jeesite.modules.per.dao.CredentialsDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2016-10-25
 */
@Service
@Transactional(readOnly = true)
public class CredentialsService extends CrudService<CredentialsDao, Credentials> {

	public Credentials get(String id) {
		return super.get(id);
	}
	
	public List<Credentials> findList(Credentials credentials) {
		return super.findList(credentials);
	}
	
	public Page<Credentials> findPage(Page<Credentials> page, Credentials credentials) {
		return super.findPage(page, credentials);
	}
	
	@Transactional(readOnly = false)
	public void save(Credentials credentials) {
		super.save(credentials);
	}
	
	@Transactional(readOnly = false)
	public void delete(Credentials credentials) {
		super.delete(credentials);
	}
	
}