/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.OutcontractAcceptance;

/**
 * 外包验收DAO接口
 * @author LKY
 * @version 2016-11-10
 */
@MyBatisDao
public interface OutcontractAcceptanceDao extends CrudDao<OutcontractAcceptance> {
	
}