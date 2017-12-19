/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;

/**
 * 补贴名单DAO接口
 * @author cdoublej
 * @version 2016-10-12
 */
@MyBatisDao
public interface AllowanceListDao extends CrudDao<AllowanceList> {

}