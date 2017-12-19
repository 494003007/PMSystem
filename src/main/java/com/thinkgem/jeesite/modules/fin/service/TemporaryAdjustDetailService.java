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
import com.thinkgem.jeesite.modules.fin.entity.TemporaryAdjustDetail;
import com.thinkgem.jeesite.modules.fin.dao.TemporaryAdjustDetailDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2016-10-18
 */
@Service
@Transactional(readOnly = true)
public class TemporaryAdjustDetailService extends CrudService<TemporaryAdjustDetailDao, TemporaryAdjustDetail> {

	@Autowired
	private CurrentSalaryStandardService currentSalaryStandardService;

	public TemporaryAdjustDetail get(String id) {
		return super.get(id);
	}
	
	public List<TemporaryAdjustDetail> findList(TemporaryAdjustDetail temporaryAdjustDetail) {
		return super.findList(temporaryAdjustDetail);
	}
	
	public Page<TemporaryAdjustDetail> findPage(Page<TemporaryAdjustDetail> page, TemporaryAdjustDetail temporaryAdjustDetail) {
		return super.findPage(page, temporaryAdjustDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(TemporaryAdjustDetail temporaryAdjustDetail) {
		if (temporaryAdjustDetail.getIsNewRecord()) {
			dao.insert(temporaryAdjustDetail);
			temporaryAdjustDetail.preInsert();
			updateToCurrentSalaryStandard(temporaryAdjustDetail);
		} else {
			temporaryAdjustDetail.preUpdate();
			dao.update(temporaryAdjustDetail);
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(TemporaryAdjustDetail temporaryAdjustDetail) {
		super.delete(temporaryAdjustDetail);
	}

	public void updateToCurrentSalaryStandard(TemporaryAdjustDetail temporaryAdjustDetail) {
		//############################   向Current_salary_standard更新数据   ###########################//
		CurrentSalaryStandard currentSalaryStandard = new CurrentSalaryStandard();
		currentSalaryStandard.setEmployeeId(temporaryAdjustDetail.getEmployeeId());
		List<CurrentSalaryStandard> currentSalaryStandardList = currentSalaryStandardService.findList(currentSalaryStandard);
		if (!currentSalaryStandardList.isEmpty()) {
			currentSalaryStandardList.get(currentSalaryStandardList.size() - 1).setTemporaryAdjustDetail(temporaryAdjustDetail);
			currentSalaryStandardService.save(currentSalaryStandardList.get(currentSalaryStandardList.size() - 1));
		} else {
			currentSalaryStandard.setTemporaryAdjustDetail(temporaryAdjustDetail);
			currentSalaryStandard.setSysAreaName("");
			currentSalaryStandard.setCurrentSalaryStandard(0.0);
			currentSalaryStandardService.save(currentSalaryStandard);
		}
		//#############################################################################################//
	}
}