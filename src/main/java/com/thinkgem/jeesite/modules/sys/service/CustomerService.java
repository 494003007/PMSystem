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
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.dao.CustomerDao;

/**
 * 客户Service
 * @author cdoublejj
 * @version 2016-11-07
 */
@Service
@Transactional(readOnly = true)
public class CustomerService extends CrudService<CustomerDao, Customer> {
	@Autowired
	private CustomerDao customerDao;
	public Customer get(String id) {
		return super.get(id);
	}
	
	public List<Customer> findList(Customer customer) {
		return super.findList(customer);
	}
	
	public Page<Customer> findPage(Page<Customer> page, Customer customer) {
		return super.findPage(page, customer);
	}
	
	@Transactional(readOnly = false)
	public void save(Customer customer) {
		super.save(customer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Customer customer) {
		super.delete(customer);
	}

	public List<Customer> isTrueCompany(String returnCompany) {
		// TODO 自动生成的方法存根
		return customerDao.isTrueCompany(returnCompany);
	}
	
}