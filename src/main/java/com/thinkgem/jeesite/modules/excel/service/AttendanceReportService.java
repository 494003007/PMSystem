/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.excel.entity.AttendanceReport;
 
import com.thinkgem.jeesite.modules.excel.dao.AttendanceReportDao;
 

/**
 * 工资报表Service
 * @author cdoublej
 * @version 2016-10-26
 */
@Service
@Transactional(readOnly = true)
public class AttendanceReportService extends CrudService<AttendanceReportDao, AttendanceReport> {

	public AttendanceReport get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceReport> findList(AttendanceReport attendanceReport) {
		return super.findList(attendanceReport);
	}
	
	public Page<AttendanceReport> findPage(Page<AttendanceReport> page, AttendanceReport attendanceReport) {
		return super.findPage(page, attendanceReport);
	}
}