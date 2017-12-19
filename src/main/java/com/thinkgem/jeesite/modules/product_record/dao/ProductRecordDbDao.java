/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecordDb;

/**
 * 数据库更改记录DAO接口
 * @author cdoublej
 * @version 2016-10-10
 */
@MyBatisDao
public interface ProductRecordDbDao extends CrudDao<ProductRecordDb> {
	
}