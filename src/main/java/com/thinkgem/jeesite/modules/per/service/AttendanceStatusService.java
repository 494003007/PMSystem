/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.AttendanceStatus;
import com.thinkgem.jeesite.modules.per.dao.AttendanceStatusDao;

/**
 * 考勤纠正Service
 * @author cdoublej
 * @version 2016-10-28
 */
@Service
@Transactional(readOnly = true)
public class AttendanceStatusService extends CrudService<AttendanceStatusDao, AttendanceStatus> {
	@Autowired
	public AttendanceStatusDao attendanceStatusDao;
	
	public AttendanceStatus get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceStatus> findList(AttendanceStatus attendanceStatus) {
		return super.findList(attendanceStatus);
	}
	
	public Page<AttendanceStatus> findPage(Page<AttendanceStatus> page, AttendanceStatus attendanceStatus) {
		return super.findPage(page, attendanceStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceStatus attendanceStatus) {
		super.save(attendanceStatus);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendanceStatus attendanceStatus) {
		super.delete(attendanceStatus);
	}
	@Transactional(readOnly = false)
	public int getPrimaryId(AttendanceStatus attendanceStatus) {
		// TODO 自动生成的方法存根
		return attendanceStatusDao.getPrimaryId(attendanceStatus);
	}
	
}