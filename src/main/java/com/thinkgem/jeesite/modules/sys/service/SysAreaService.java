/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.SysAreaDao;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;


/**
 * 树结构生成Service
 * @author ThinkGem
 * @version 2015-04-06
 */
@Service
@Transactional(readOnly = true)
public class SysAreaService extends TreeService<SysAreaDao, SysArea> {
@Autowired 
private SysAreaDao sysAreaDao;
	public SysArea get(String id) {
		return super.get(id);
	}
	
	public List<SysArea> findList(SysArea sysArea) {
		if (StringUtils.isNotBlank(sysArea.getParentIds())){
			sysArea.setParentIds(","+sysArea.getParentIds()+",");
		}
		return super.findList(sysArea);
	}
	
	@Transactional(readOnly = false)
	public void save(SysArea sysArea) {
		super.save(sysArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysArea sysArea) {
		super.delete(sysArea);
	}

	public ArrayList<SysArea> findAllchildren(SysArea s) {
		// TODO 自动生成的方法存根
		return sysAreaDao.findAllchildren(s) ;
	}

	public List<SysArea> projectSysAreaTree(String type, String employeeId) {
		// TODO 自动生成的方法存根
		return dao.projectSysAreaTree(type,employeeId);
	}

	public ArrayList<SysArea> allSysAreaTree(String type, String employeeId) {
		// TODO 自动生成的方法存根
		return dao.allSysAreaTree(type,employeeId);
	}

	public List<SysArea> allProjectSysAreaTree() {
		// TODO 自动生成的方法存根
		return dao.allProjectSysAreaTree();
	}

 
	
}