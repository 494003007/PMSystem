/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.per.entity.CertificatesType;

/**
 * 证书类型DAO接口
 * @author cdoublej
 * @version 2016-10-09
 */
@MyBatisDao
public interface CertificatesTypeDao extends CrudDao<CertificatesType> {
	
}