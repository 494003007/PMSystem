/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;
import com.thinkgem.jeesite.modules.sys.dao.CustomerUserDao;
import com.thinkgem.jeesite.modules.sys.dao.CustomerUserDao;

/**
 * 客户Service
 * @author cdoublejj
 * @version 2016-11-07
 */
@Service
@Transactional(readOnly = true)
public class CustomerUserService extends CrudService<CustomerUserDao, CustomerUser> {
	@Autowired
	private CustomerUserDao customerDao;
	public CustomerUser get(String id) {
		return super.get(id);
	}
	
	public List<CustomerUser> findList(CustomerUser customer) {
		return super.findList(customer);
	}
	
	public Page<CustomerUser> findPage(Page<CustomerUser> page, CustomerUser customer) {
		return super.findPage(page, customer);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerUser customer) {
		super.save(customer);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerUser customer) {
		super.delete(customer);
	}
	@Transactional(readOnly = false)
	public void deleteAll(String customerid) {
		customerDao.deleteAll(customerid);
		
	}

 
	
}