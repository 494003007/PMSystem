/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import com.thinkgem.jeesite.modules.fin.entity.PositionSubsidyDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.LifeSubsidyDetail;
import com.thinkgem.jeesite.modules.fin.dao.LifeSubsidyDetailDao;

/**
 * life_subsidyService
 *
 * @author czy
 * @version 2016-10-21
 */
@Service
@Transactional(readOnly = true)
public class LifeSubsidyDetailService extends CrudService<LifeSubsidyDetailDao, LifeSubsidyDetail> {

    @Autowired
    private CurrentSalaryStandardService currentSalaryStandardService;

    public LifeSubsidyDetail get(String id) {
        return super.get(id);
    }

    public List<LifeSubsidyDetail> findList(LifeSubsidyDetail lifeSubsidyDetail) {
        return super.findList(lifeSubsidyDetail);
    }

    public Page<LifeSubsidyDetail> findPage(Page<LifeSubsidyDetail> page, LifeSubsidyDetail lifeSubsidyDetail) {

        return super.findPage(page, lifeSubsidyDetail);
    }

    @Transactional(readOnly = false)
    public void save(LifeSubsidyDetail lifeSubsidyDetail) {
        if (lifeSubsidyDetail.getIsNewRecord()) {
            dao.insert(lifeSubsidyDetail);
            lifeSubsidyDetail.preInsert();
            updateToCurrentSalaryStandard(lifeSubsidyDetail);
        } else {
            lifeSubsidyDetail.preUpdate();
            dao.update(lifeSubsidyDetail);
        }
    }

    @Transactional(readOnly = false)
    public void delete(LifeSubsidyDetail lifeSubsidyDetail) {
        super.delete(lifeSubsidyDetail);
    }

    public void updateToCurrentSalaryStandard(LifeSubsidyDetail lifeSubsidyDetail) {
        //############################   向Current_salary_standard更新数据   ###########################//
        CurrentSalaryStandard currentSalaryStandard = new CurrentSalaryStandard();
        currentSalaryStandard.setEmployeeId(lifeSubsidyDetail.getEmployeeId());
        List<CurrentSalaryStandard> currentSalaryStandardList = currentSalaryStandardService.findList(currentSalaryStandard);
        if (!currentSalaryStandardList.isEmpty()) {
            currentSalaryStandardList.get(currentSalaryStandardList.size() - 1).setLifeSubsidyDetail(lifeSubsidyDetail);
            currentSalaryStandardService.save(currentSalaryStandardList.get(currentSalaryStandardList.size() - 1));
        } else {
            currentSalaryStandard.setLifeSubsidyDetail(lifeSubsidyDetail);
            currentSalaryStandard.setSysAreaName("");
            currentSalaryStandard.setCurrentSalaryStandard(0.0);
            currentSalaryStandardService.save(currentSalaryStandard);
        }
        //#############################################################################################//
    }
}