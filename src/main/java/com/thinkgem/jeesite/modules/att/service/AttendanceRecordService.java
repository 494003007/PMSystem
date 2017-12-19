/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.dao.AttendanceRecordDao;

/**
 * 考勤Service
 * @author cdoublej
 * @version 2016-10-18
 */
@Service
@Transactional(readOnly = true)
public class AttendanceRecordService extends CrudService<AttendanceRecordDao, AttendanceRecord> {

	@Autowired
	AttendanceRecordDao attendanceRecordDao;
	public AttendanceRecord get(String id) {
		return super.get(id);
	}
	
	public List<AttendanceRecord> findList(AttendanceRecord attendanceRecord) {
		return super.findList(attendanceRecord);
	}
	
	public Page<AttendanceRecord> findPage(Page<AttendanceRecord> page, AttendanceRecord attendanceRecord) {
		return super.findPage(page, attendanceRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendanceRecord attendanceRecord) {
		super.save(attendanceRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendanceRecord attendanceRecord) {
		super.delete(attendanceRecord);
	}
	@Transactional(readOnly = false)
	public List<AttendanceRecord> findAttendanceList(String porject_allowance_detail_id) {
		return attendanceRecordDao.findAttendanceList(porject_allowance_detail_id);
		
	}
	@Transactional(readOnly = false)
	public void addList(String employeeID, String attendance_date, String attendance_type_id, String projectAttendanceID1,int projectid) {
		AttendanceRecord attendanceRecord = attendanceRecordDao.isAttendanceRecord(employeeID,attendance_date,projectid);//是否有考勤记录
		if (attendanceRecord == null){
			attendanceRecord = new AttendanceRecord();
			attendanceRecord.setEmployeeId(Integer.parseInt(employeeID));
		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				attendanceRecord.setAttendanceDate(sdf.parse(attendance_date));
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			attendanceRecord.setProjectId(projectid);
		}
		attendanceRecord.setProject_allowance_detail_id(Integer.parseInt(projectAttendanceID1));
		attendanceRecord.setAttendanceTypeId(Integer.parseInt(attendance_type_id));	
		attendanceRecord.setBefore_attendance_type(Integer.parseInt(attendance_type_id));
		super.save(attendanceRecord);
	}
	@Transactional(readOnly = false)
	public void finaddList(int attendanceStatusId,String employeeID, String attendance_date, String attendance_type_id, String projectAttendanceID1,int projectid) {
		AttendanceRecord attendanceRecord = attendanceRecordDao.isAttendanceRecord(employeeID,attendance_date,projectid);//是否有考勤记录
		if (attendanceRecord == null){
			attendanceRecord = new AttendanceRecord();
			attendanceRecord.setEmployeeId(Integer.parseInt(employeeID));
		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				attendanceRecord.setAttendanceDate(sdf.parse(attendance_date));
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			attendanceRecord.setProjectId(projectid);
		//	attendanceRecord.setBefore_attendance_type(Integer.parseInt(attendance_type_id));
		}
		
		attendanceRecord.setProject_allowance_detail_id(Integer.parseInt(projectAttendanceID1));
		attendanceRecord.setFinattendanceTypeId(Integer.parseInt(attendance_type_id));	
		attendanceRecord.setCorrectDate(new Date());
		attendanceRecord.setFinIsCorrect("2");
		attendanceRecord.setAttendanceStatusId(attendanceStatusId);
		super.save(attendanceRecord);
		
	}


}