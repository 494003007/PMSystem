/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.dao.CorrectAttendanceDao;
import com.thinkgem.jeesite.modules.per.entity.CorrectAttendance;

/**
 * 考勤Service
 * @author cdoublej
 * @version 2016-10-19
 */
@Service
@Transactional(readOnly = true)
public class CorrectAttendanceService extends CrudService<CorrectAttendanceDao,CorrectAttendance> {
 
	public CorrectAttendance get(String id) {
		return super.get(id);
	}
	
	public List<CorrectAttendance> findList(CorrectAttendance correctAttendance) {
		return super.findList(correctAttendance);
	}
	
	public Page<CorrectAttendance> findPage(Page<CorrectAttendance> page, CorrectAttendance correctAttendance) {
		return super.findPage(page, correctAttendance);
	}
	
	@Transactional(readOnly = false)
	public void save(CorrectAttendance correctAttendance) {
		super.save(correctAttendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(CorrectAttendance correctAttendance) {
		super.delete(correctAttendance);
	}

	public ArrayList findCoeifficientList(int projectId, String statr_date, String end_date) {
		// TODO 自动生成的方法存根
		return dao.findCoeifficientList(projectId,statr_date,end_date);
	}
	@Transactional(readOnly = false)
	public void updatePerformanceCoefficient(String string, String string2) {
	
		dao.updatePerformanceCoefficient(string,string2);
	}
	@Transactional(readOnly = false)
	public void updateStatus(String string, String status) {
		dao.updateStatus(string,status);
		
	}
	@Transactional(readOnly = false)
	public void updateAddAttendance(String string, String string2) {
		dao.updateAddAttendance(string,string2);
		
	}
	
}