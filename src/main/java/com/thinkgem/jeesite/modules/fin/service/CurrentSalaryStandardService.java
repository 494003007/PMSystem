/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import com.thinkgem.jeesite.modules.fin.dao.CurrentSalaryStandardDao;

/**
 * current_salary_standardService
 * @author czy
 * @version 2016-10-22
 */
@Service
@Transactional(readOnly = true)
public class CurrentSalaryStandardService extends CrudService<CurrentSalaryStandardDao, CurrentSalaryStandard> {

	public CurrentSalaryStandard get(String id) {
		return super.get(id);
	}
	
	public List<CurrentSalaryStandard> findList(CurrentSalaryStandard currentSalaryStandard) {
		return super.findList(currentSalaryStandard);
	}
	
	public Page<CurrentSalaryStandard> findPage(Page<CurrentSalaryStandard> page, CurrentSalaryStandard currentSalaryStandard) {
		return super.findPage(page, currentSalaryStandard);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentSalaryStandard currentSalaryStandard) {
		super.save(currentSalaryStandard);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentSalaryStandard currentSalaryStandard) {
		super.delete(currentSalaryStandard);
	}
	
}