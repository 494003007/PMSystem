/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.AllowanceConstant;
import com.thinkgem.jeesite.modules.per.dao.AllowanceConstantDao;

/**
 * 补贴常量Service
 * @author cdoublej
 * @version 2016-10-11
 */
@Service
@Transactional(readOnly = true)
public class AllowanceConstantService extends CrudService<AllowanceConstantDao, AllowanceConstant> {

	public AllowanceConstant get(String id) {
		return super.get(id);
	}

	public List<AllowanceConstant> findList(AllowanceConstant allowanceConstant) {
		return super.findList(allowanceConstant);
	}

	public Page<AllowanceConstant> findPage(Page<AllowanceConstant> page, AllowanceConstant allowanceConstant) {
		return super.findPage(page, allowanceConstant);
	}

	@Transactional(readOnly = false)
	public void save(AllowanceConstant allowanceConstant) {
		super.save(allowanceConstant);
	}

	@Transactional(readOnly = false)
	public void delete(AllowanceConstant allowanceConstant) {
		super.delete(allowanceConstant);
	}

}