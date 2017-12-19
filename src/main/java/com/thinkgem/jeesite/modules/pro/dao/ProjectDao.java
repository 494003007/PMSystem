/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.pro.entity.Project;

/**
 * 项目DAO接口
 * @author cdoublej
 * @version 2016-11-03
 */
@MyBatisDao
public interface ProjectDao extends CrudDao<Project> {

	public ArrayList chooseList(Employee employee);

	public List<Project> findDetailPage(Project project);

	public List<Project> findExamineDetailPage(Project project);

	public List<Project> findExamineList(Project project);

	public int getExamineProjectCount();

	public Project getProjectDetail(@Param("id")String projectId);

	public List<Project> findWorkerDetail(Project project);

	public List<Map<String, String>> getProjectConfirmList(@Param("id")String id);

	public List<Project> findInfDetailPage(Project project);

	public List<Project> findOutDetailPage(Project project);

		
}