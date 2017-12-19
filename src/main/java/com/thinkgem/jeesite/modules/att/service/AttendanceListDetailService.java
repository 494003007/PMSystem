/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.dao.AttendanceListDetailDao;

/**
 * 员工考勤Service
 * @author cdoublej
 * @version 2016-11-04
 */
@Service
@Transactional(readOnly = true)
public class AttendanceListDetailService extends CrudService<AttendanceListDetailDao, AttendanceListDetail> {
	@Autowired
	private  AttendanceListDetailDao attendanceListDetailDao;
	public AttendanceListDetail get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceListDetail> findList(AttendanceListDetail attendanceListDetail) {
		return super.findList(attendanceListDetail);
	}
	
	public Page<AttendanceListDetail> findPage(Page<AttendanceListDetail> page, AttendanceListDetail attendanceListDetail) {
		return super.findPage(page, attendanceListDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceListDetail attendanceListDetail) {
		super.save(attendanceListDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendanceListDetail attendanceListDetail) {
		super.delete(attendanceListDetail);
	}

	public ArrayList<AttendanceListDetail> isexist(AttendanceListDetail attendanceListDetail2) {
		// TODO 自动生成的方法存根
		return attendanceListDetailDao.isexist(attendanceListDetail2);
	}
	@Transactional(readOnly = false)
	public void attendanceupdate(AttendanceListDetail attendanceListDetail) {
		// TODO 自动生成的方法存根
		attendanceListDetailDao.attendanceupdate(attendanceListDetail);
	}

	public Page<AttendanceListDetail> findAllAttendance(Page<AttendanceListDetail> page, AttendanceListDetail attendanceListDetail) {
		// TODO 自动生成的方法存根	
		attendanceListDetail.setPage(page);
		page.setList(dao.findAllAttendance(attendanceListDetail));
		return page;
	}

	public Page<AttendanceListDetail> findExaminAttendance( Page<AttendanceListDetail> page, AttendanceListDetail attendanceListDetail) {
		// TODO 自动生成的方法存根	
		attendanceListDetail.setPage(page);
		page.setList(dao.findExaminAttendance(attendanceListDetail));
		return page;
	}

	public int getExamineAttendanceCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineAttendanceCount();
	}
	
}