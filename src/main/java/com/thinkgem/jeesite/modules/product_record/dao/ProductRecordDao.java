/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecord;

/**
 * product_recordDAO接口
 * @author cdoublej
 * @version 2016-09-30
 */
@MyBatisDao
public interface ProductRecordDao extends CrudDao<ProductRecord> {
	
}