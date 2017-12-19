/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.dao.AttendanceListDao;

/**
 * 考勤人员Service
 * @author cdoublej
 * @version 2016-11-03
 */
@Service
@Transactional(readOnly = true)
public class AttendanceListService extends CrudService<AttendanceListDao, AttendanceList> {
@Autowired
private AttendanceListDao attendanceListDao;
	public AttendanceList get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceList> findList(AttendanceList attendanceList) {
		return super.findList(attendanceList);
	}
	
	public Page<AttendanceList> findPage(Page<AttendanceList> page, AttendanceList attendanceList) {
		return super.findPage(page, attendanceList);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceList attendanceList) {
		super.save(attendanceList);
	}
	
	
	@Transactional(readOnly = false)
	public int insert(AttendanceList attendanceList) {
		return attendanceListDao.insert(attendanceList);
	}
	@Transactional(readOnly = false)
	public void delete(AttendanceList attendanceList) {
		super.delete(attendanceList);
	}

	public List<AttendanceList> findAllEmployee(AttendanceList a) {
		// TODO 自动生成的方法存根
		return attendanceListDao.findAllEmployee(a);
	}

	public int isExist(AttendanceList attendanceList) {
		// TODO 自动生成的方法存根
		return attendanceListDao.isExist(attendanceList);
	}
	@Transactional(readOnly = false)
	public void updateDelFlag(AttendanceList attendanceList) {
		attendanceListDao.updateDelFlag(attendanceList);
		
	}

	public int getEmployeeAttendanceCount(Date month) {
		// TODO 自动生成的方法存根
		return attendanceListDao.getEmployeeAttendanceCount(month);
	}
	
}