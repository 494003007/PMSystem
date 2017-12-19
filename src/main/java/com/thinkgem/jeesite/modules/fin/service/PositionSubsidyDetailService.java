/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import com.thinkgem.jeesite.modules.fin.entity.CurrentSalaryStandard;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.PositionSubsidyDetail;
import com.thinkgem.jeesite.modules.fin.dao.PositionSubsidyDetailDao;

/**
 * position_subsidyService
 * @author czy
 * @version 2016-10-21
 */
@Service
@Transactional(readOnly = true)
public class PositionSubsidyDetailService extends CrudService<PositionSubsidyDetailDao, PositionSubsidyDetail> {

	@Autowired
	private CurrentSalaryStandardService currentSalaryStandardService;
	@Autowired
	private EmployeeService employeeService;
	
	public PositionSubsidyDetail get(String id) {
		return super.get(id);
	}
	
	public List<PositionSubsidyDetail> findList(PositionSubsidyDetail positionSubsidyDetail) {
		return super.findList(positionSubsidyDetail);
	}
	
	public Page<PositionSubsidyDetail> findPage(Page<PositionSubsidyDetail> page, PositionSubsidyDetail positionSubsidyDetail) {
		return super.findPage(page, positionSubsidyDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(PositionSubsidyDetail positionSubsidyDetail) {
		if (positionSubsidyDetail.getIsNewRecord()) {
			dao.insert(positionSubsidyDetail);
			positionSubsidyDetail.preInsert();
			updateToCurrentSalaryStandard(positionSubsidyDetail);
		} else {
			positionSubsidyDetail.preUpdate();
			updateToCurrentSalaryStandard(positionSubsidyDetail);
			dao.update(positionSubsidyDetail);
		}
	}

	
	@Transactional(readOnly = false)
	public void delete(PositionSubsidyDetail positionSubsidyDetail) {
		super.delete(positionSubsidyDetail);
	}

	//############################   向Current_salary_standard更新数据   ###########################//
	public void updateToCurrentSalaryStandard(PositionSubsidyDetail positionSubsidyDetail) {

		Employee employee = new Employee();
		employee.setQuarters(positionSubsidyDetail.getPositionTypeId().toString());
		List<Employee> employeeList = employeeService.findList(employee);
		if (!employeeList.isEmpty()) {
			for (Employee employee0 : employeeList) {
				CurrentSalaryStandard currentSalaryStandard = new CurrentSalaryStandard();
				currentSalaryStandard.setEmployeeId(Integer.valueOf(employee0.getId()));
				List<CurrentSalaryStandard> currentSalaryStandardList = currentSalaryStandardService.findList(currentSalaryStandard);
				if (!currentSalaryStandardList.isEmpty()) {
					for (CurrentSalaryStandard currentSalaryStandard0 : currentSalaryStandardList) {
						currentSalaryStandard0.setPositionSubsidyDetail(positionSubsidyDetail);
						currentSalaryStandardService.save(currentSalaryStandard0);
					}
				} else {
					//不存在该岗位类型情况处理
				}
			}
			//#############################################################################################//
		}
	}
}