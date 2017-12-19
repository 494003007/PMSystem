/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.OutcontractAcceptance;
import com.thinkgem.jeesite.modules.out.dao.OutcontractAcceptanceDao;

/**
 * 外包验收Service
 * @author LKY
 * @version 2016-11-10
 */
@Service
@Transactional(readOnly = true)
public class OutcontractAcceptanceService extends CrudService<OutcontractAcceptanceDao, OutcontractAcceptance> {

	public OutcontractAcceptance get(String id) {
		return super.get(id);
	}
	
	public List<OutcontractAcceptance> findList(OutcontractAcceptance outcontractAcceptance) {
		return super.findList(outcontractAcceptance);
	}
	
	public Page<OutcontractAcceptance> findPage(Page<OutcontractAcceptance> page, OutcontractAcceptance outcontractAcceptance) {
		return super.findPage(page, outcontractAcceptance);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractAcceptance outcontractAcceptance) {
		super.save(outcontractAcceptance);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractAcceptance outcontractAcceptance) {
		super.delete(outcontractAcceptance);
	}
	
}