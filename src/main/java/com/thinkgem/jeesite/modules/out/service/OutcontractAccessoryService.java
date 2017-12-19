/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.OutcontractAccessory;
import com.thinkgem.jeesite.modules.out.dao.OutcontractAccessoryDao;

/**
 * 外包附件Service
 * @author LKY
 * @version 2016-11-30
 */
@Service
@Transactional(readOnly = true)
public class OutcontractAccessoryService extends CrudService<OutcontractAccessoryDao, OutcontractAccessory> {

	public OutcontractAccessory get(String id) {
		return super.get(id);
	}
	
	public List<OutcontractAccessory> findList(OutcontractAccessory outcontractAccessory) {
		return super.findList(outcontractAccessory);
	}
	
	public Page<OutcontractAccessory> findPage(Page<OutcontractAccessory> page, OutcontractAccessory outcontractAccessory) {
		return super.findPage(page, outcontractAccessory);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractAccessory outcontractAccessory) {
		super.save(outcontractAccessory);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractAccessory outcontractAccessory) {
		super.delete(outcontractAccessory);
	}
	
}