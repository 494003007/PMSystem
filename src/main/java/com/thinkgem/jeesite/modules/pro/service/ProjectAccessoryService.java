/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.pro.entity.ProjectAccessory;
import com.thinkgem.jeesite.modules.pro.dao.ProjectAccessoryDao;

/**
 * 项目附件Service
 * @author cdoublej
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectAccessoryService extends CrudService<ProjectAccessoryDao, ProjectAccessory> {

	public ProjectAccessory get(String id) {
		return super.get(id);
	}
	
	public List<ProjectAccessory> findList(ProjectAccessory projectAccessory) {
		return super.findList(projectAccessory);
	}
	
	public Page<ProjectAccessory> findPage(Page<ProjectAccessory> page, ProjectAccessory projectAccessory) {
		return super.findPage(page, projectAccessory);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectAccessory projectAccessory) {
		super.save(projectAccessory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectAccessory projectAccessory) {
		super.delete(projectAccessory);
	}
	
}