/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.SalaryStandardDetail;
import com.thinkgem.jeesite.modules.fin.dao.SalaryStandardDetailDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2016-10-23
 */
@Service
@Transactional(readOnly = true)
public class SalaryStandardDetailService extends CrudService<SalaryStandardDetailDao, SalaryStandardDetail> {

	@Autowired
	private CurrentSalaryStandardService currentSalaryStandardService;

	public SalaryStandardDetail get(String id) {
		return super.get(id);
	}
	
	public List<SalaryStandardDetail> findList(SalaryStandardDetail salaryStandardDetail) {
		return super.findList(salaryStandardDetail);
	}
	
	public Page<SalaryStandardDetail> findPage(Page<SalaryStandardDetail> page, SalaryStandardDetail salaryStandardDetail) {
		return super.findPage(page, salaryStandardDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(SalaryStandardDetail salaryStandardDetail) {
		if (salaryStandardDetail.getIsNewRecord()) {
			dao.insert(salaryStandardDetail);
			salaryStandardDetail.preInsert();
			updateToCurrentSalaryStandard(salaryStandardDetail);
		} else {
			salaryStandardDetail.preUpdate();
			dao.update(salaryStandardDetail);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SalaryStandardDetail salaryStandardDetail) {
		super.delete(salaryStandardDetail);
	}

	public void updateToCurrentSalaryStandard(SalaryStandardDetail salaryStandeardDetail) {

		//############################   向Current_salary_standard更新数据   ###########################//
		CurrentSalaryStandard currentSalaryStandard = new CurrentSalaryStandard();
		currentSalaryStandard.setEmployeeId(salaryStandeardDetail.getEmployeeId());
		List<CurrentSalaryStandard> currentSalaryStandardList = currentSalaryStandardService.findList(currentSalaryStandard);
		if (!currentSalaryStandardList.isEmpty()) {
			currentSalaryStandardList.get(currentSalaryStandardList.size() - 1).setSalaryStandardDetail(salaryStandeardDetail);
			currentSalaryStandardService.save(currentSalaryStandardList.get(currentSalaryStandardList.size() - 1));
		} else {
			currentSalaryStandard.setSalaryStandardDetail(salaryStandeardDetail);
			currentSalaryStandard.setSysAreaName("");
			currentSalaryStandard.setCurrentSalaryStandard(0.0);
			currentSalaryStandardService.save(currentSalaryStandard);
		}
		//#############################################################################################//
	}
}