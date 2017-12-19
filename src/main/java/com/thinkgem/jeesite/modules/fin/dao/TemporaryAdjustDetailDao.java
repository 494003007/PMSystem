/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.fin.entity.TemporaryAdjustDetail;

/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2016-10-18
 */
@MyBatisDao
public interface TemporaryAdjustDetailDao extends CrudDao<TemporaryAdjustDetail> {
	
}