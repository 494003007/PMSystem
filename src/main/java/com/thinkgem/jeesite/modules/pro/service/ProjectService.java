/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.dao.ProjectDao;

/**
 * 项目Service
 * @author cdoublej
 * @version 2016-11-03
 */
@Service
@Transactional(readOnly = true)
public class ProjectService extends CrudService<ProjectDao, Project> {
@Autowired
private ProjectDao projectDao;
	public Project get(String id) {
		return super.get(id);
	}
	
	public List<Project> findList(Project project) {
		return super.findList(project);
	}
	
	public Page<Project> findPage(Page<Project> page, Project project) {
		return super.findPage(page, project);
	}
	
	@Transactional(readOnly = false)
	public void save(Project project) {
		super.save(project);
	}
	
	@Transactional(readOnly = false)
	public void delete(Project project) {
		super.delete(project);
	}

	public Page<Employee> chooseList(Page<Employee> page,Employee employee) {	 
		employee.setPage(page);
		page.setList(chooseList(employee));
		return page;
	}
	public List<Employee> chooseList(Employee employee) {
		return projectDao.chooseList(employee);
	}

	public Page<Project> findDetailPage(Page<Project> page, Project project) {
		project.setPage(page);
		page.setList(projectDao.findDetailPage(project));
		return page;
	}

	public Page<Project> findExamineDetailPage(Page<Project> page,
			Project project) {
		project.setPage(page);
		page.setList(projectDao.findExamineDetailPage(project));
		return page;
	}

	public Page<Project> findExaminePage(Page<Project> page, Project project) {
		project.setPage(page);
		page.setList(projectDao.findExamineList(project));
		return page;
	}

	public int getExamineProjectCount() {
		// TODO 自动生成的方法存根
		return dao.getExamineProjectCount();
	}

	public Project getProjectDetail(String projectId) {
		// TODO 自动生成的方法存根
		return dao.getProjectDetail(projectId);
	}

	public Page<Project> findWorkerDetailPage(Page<Project> page,
			Project project) {
		project.setPage(page);
		page.setList(projectDao.findWorkerDetail(project));
		return page;
	}

	public List<Map<String, String>> getProjectConfirmList(String id) {
		// TODO 自动生成的方法存根
		return dao.getProjectConfirmList(id);
	}

	public Page<Project> findInfDetailPage(Page<Project> page, Project project) {
		project.setPage(page);
		page.setList(projectDao.findInfDetailPage(project));
		return page;
	}

	public Page<Project> findOutDetailPage(Page<Project> page, Project project) {
		project.setPage(page);
		page.setList(projectDao.findOutDetailPage(project));
		return page;
	}
 
}