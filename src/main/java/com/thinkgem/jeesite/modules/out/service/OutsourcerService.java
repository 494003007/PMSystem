/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.Outsourcer;
import com.thinkgem.jeesite.modules.out.dao.OutsourcerDao;

/**
 * 外包商管理Service
 * @author LKY
 * @version 2016-11-16
 */
@Service
@Transactional(readOnly = true)
public class OutsourcerService extends CrudService<OutsourcerDao, Outsourcer> {

	public Outsourcer get(String id) {
		return super.get(id);
	}
	
	public List<Outsourcer> findList(Outsourcer outsourcer) {
		return super.findList(outsourcer);
	}
	
	public Page<Outsourcer> findPage(Page<Outsourcer> page, Outsourcer outsourcer) {
		return super.findPage(page, outsourcer);
	}
	
	@Transactional(readOnly = false)
	public void save(Outsourcer outsourcer) {
		super.save(outsourcer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Outsourcer outsourcer) {
		super.delete(outsourcer);
	}
	
}