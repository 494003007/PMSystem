/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ProjectType;
import com.thinkgem.jeesite.modules.pro.dao.ProjectTypeDao;

/**
 * 项目类型Service
 * @author cdoublej
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class ProjectTypeService extends CrudService<ProjectTypeDao, ProjectType> {

	public ProjectType get(String id) {
		return super.get(id);
	}
	
	public List<ProjectType> findList(ProjectType projectType) {
		return super.findList(projectType);
	}
	
	public Page<ProjectType> findPage(Page<ProjectType> page, ProjectType projectType) {
		return super.findPage(page, projectType);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectType projectType) {
		super.save(projectType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectType projectType) {
		super.delete(projectType);
	}
	
}