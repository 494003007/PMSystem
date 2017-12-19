/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.CertificatesType;
import com.thinkgem.jeesite.modules.per.dao.CertificatesTypeDao;

/**
 * 证书类型Service
 * @author cdoublej
 * @version 2016-10-09
 */
@Service
@Transactional(readOnly = true)
public class CertificatesTypeService extends CrudService<CertificatesTypeDao, CertificatesType> {

	public CertificatesType get(String id) {
		return super.get(id);
	}
	
	public List<CertificatesType> findList(CertificatesType certificatesType) {
		return super.findList(certificatesType);
	}
	
	public Page<CertificatesType> findPage(Page<CertificatesType> page, CertificatesType certificatesType) {
		return super.findPage(page, certificatesType);
	}
	
	@Transactional(readOnly = false)
	public void save(CertificatesType certificatesType) {
		super.save(certificatesType);
	}
	
	@Transactional(readOnly = false)
	public void delete(CertificatesType certificatesType) {
		super.delete(certificatesType);
	}
	
}