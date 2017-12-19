/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.Credentials;

/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2016-10-25
 */
@MyBatisDao
public interface CredentialsDao extends CrudDao<Credentials> {
	
}