/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.pro.entity.ContractCredentials;

/**
 * 合同证件DAO接口
 * @author czy
 * @version 2016-11-05
 */
@MyBatisDao
public interface ContractCredentialsDao extends CrudDao<ContractCredentials> {
	
}