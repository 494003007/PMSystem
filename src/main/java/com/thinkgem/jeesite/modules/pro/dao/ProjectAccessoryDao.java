/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ProjectAccessory;

/**
 * 项目附件DAO接口
 * @author cdoublej
 * @version 2016-11-24
 */
@MyBatisDao
public interface ProjectAccessoryDao extends CrudDao<ProjectAccessory> {
	
}