/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.gmo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.gmo.dao.ExamineAttendanceDao;
import com.thinkgem.jeesite.modules.gmo.entity.ExamineAttendance;


/**
 * 考勤Service
 * @author cdoublej
 * @version 2016-10-19
 */
@Service
@Transactional(readOnly = true)
public class ExamineAttendanceService extends CrudService<ExamineAttendanceDao,ExamineAttendance> {

	@Autowired
	ExamineAttendanceDao examineAttendanceDao;
	public ExamineAttendance get(String id) {
		return super.get(id);
	}
	
	public List<ExamineAttendance> findList(ExamineAttendance examineAttendance) {
		return super.findList(examineAttendance);
	}
	
	public Page<ExamineAttendance> findPage(Page<ExamineAttendance> page, ExamineAttendance examineAttendance) {
		return super.findPage(page, examineAttendance);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamineAttendance examineAttendance) {
		super.save(examineAttendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamineAttendance examineAttendance) {
		super.delete(examineAttendance);
	}

}