/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.InvoiceContent;

/**
 * 开票内容DAO接口
 * @author cdoublejj
 * @version 2016-11-20
 */
@MyBatisDao
public interface InvoiceContentDao extends CrudDao<InvoiceContent> {
	
}