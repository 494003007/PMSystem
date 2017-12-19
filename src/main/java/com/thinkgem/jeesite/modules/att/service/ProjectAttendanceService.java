/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.dao.ProjectAttendanceDao;

/**
 * 考勤Service
 * @author cdoublej
 * @version 2016-10-19
 */
@Service
@Transactional(readOnly = true)
public class ProjectAttendanceService extends CrudService<ProjectAttendanceDao, ProjectAttendance> {

	@Autowired
	ProjectAttendanceDao projectAttendanceDao;
	public ProjectAttendance get(String id) {
		return super.get(id);
	}
	
	public List<ProjectAttendance> findList(ProjectAttendance projectAttendance) {
		return super.findList(projectAttendance);
	}
	
	public Page<ProjectAttendance> findPage(Page<ProjectAttendance> page, ProjectAttendance projectAttendance) {
		projectAttendance.setPage(page);
		ArrayList<ProjectAttendance> a = (ArrayList<ProjectAttendance>) dao.findList(projectAttendance);
		ArrayList<ProjectAttendance> aa = new ArrayList<ProjectAttendance> ();
		for(int i = 0 ;i < a.size();i++){
			ProjectAttendance projectAttendance2 = a.get(i);
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			Integer startdate1 = Integer.parseInt(date.format(projectAttendance2.getStartDate()).split("-")[2]);	
			String startdate = startdate1.toString();
			Integer enddate1 =Integer.parseInt(date.format(projectAttendance2.getEndDate()).split("-")[2]);
			String enddate = enddate1.toString();
			String year = date.format(projectAttendance2.getStartDate()).split("-")[0];
			Integer month1 = Integer.parseInt(date.format(projectAttendance2.getStartDate()).split("-")[1]);
			String month = month1.toString();	
			boolean isattendance = false;	
			String thisdate = year+"/"+month+"/"+enddate+" 00:00:01";
			Date date1 = new Date(thisdate);
			Date date2 = new Date();
			long date3 = date2.getTime()-date1.getTime();
			double days=Math.floor(date3/(24*3600*1000));
			isattendance =(days - projectAttendance2.getEffectiveDate() >= 0);
			projectAttendance2.setIsattendance(isattendance);
			aa.add(projectAttendance2);
		}
	
		page.setList(aa);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectAttendance projectAttendance) {
		super.save(projectAttendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectAttendance projectAttendance) {
		super.delete(projectAttendance);
	}

	public ArrayList findCoeifficientList(int projectId, String statr_date, String end_date) {
		// TODO 自动生成的方法存根
		return projectAttendanceDao.findCoeifficientList(projectId,statr_date,end_date);
	}
	@Transactional(readOnly = false)
	public void addThreeAttendance(ProjectAttendance projectAttendance) {
		projectAttendanceDao.addThreeAttendance(projectAttendance);
		
	}

	public int getAttendanceCount(ProjectAttendance projectAttendance) {
		// TODO 自动生成的方法存根
		return projectAttendanceDao.getAttendanceCount(projectAttendance);
	}
	@Transactional(readOnly = false)
	public void updatePerformanceCoefficient(String projectAttendanceId, String performanceCoefficient) {
		// TODO 自动生成的方法存根
		dao.updatePerformanceCoefficient(projectAttendanceId,performanceCoefficient);
	}
	@Transactional(readOnly = false)
	public void updateStatus(String projectAttendanceId,String status) {
		// TODO 自动生成的方法存根
		dao.updateStatus(projectAttendanceId,status);
	}
	@Transactional(readOnly = false)
	public void updateAddAttendance(String string, String string2) {
		dao.updateAddAttendance(string,string2);
		
	}
	
}