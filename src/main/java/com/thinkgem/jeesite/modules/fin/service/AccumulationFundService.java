/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import com.thinkgem.jeesite.modules.fin.dao.AccumulationFundDao;

/**
 * accumulation_fundService
 * @author czy
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class AccumulationFundService extends CrudService<AccumulationFundDao, AccumulationFund> {

	public AccumulationFund get(String id) {
		return super.get(id);
	}
	
	public List<AccumulationFund> findList(AccumulationFund accumulationFund) {
		return super.findList(accumulationFund);
	}
	
	public Page<AccumulationFund> findPage(Page<AccumulationFund> page, AccumulationFund accumulationFund) {
		return super.findPage(page, accumulationFund);
	}
	
	@Transactional(readOnly = false)
	public void save(AccumulationFund accumulationFund) {
		super.save(accumulationFund);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccumulationFund accumulationFund) {
		super.delete(accumulationFund);
	}
	
}