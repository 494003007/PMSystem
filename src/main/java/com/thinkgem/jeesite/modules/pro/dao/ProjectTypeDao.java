/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ProjectType;

/**
 * 项目类型DAO接口
 * @author cdoublej
 * @version 2016-10-13
 */
@MyBatisDao
public interface ProjectTypeDao extends CrudDao<ProjectType> {
	
}