/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecord;
import com.thinkgem.jeesite.modules.product_record.dao.ProductRecordDao;

/**
 * product_recordService
 * @author cdoublej
 * @version 2016-09-30
 */
@Service
@Transactional(readOnly = true)
public class ProductRecordService extends CrudService<ProductRecordDao, ProductRecord> {

	public ProductRecord get(String id) {
		return super.get(id);
	}
	
	public List<ProductRecord> findList(ProductRecord productRecord) {
		return super.findList(productRecord);
	}
	
	public Page<ProductRecord> findPage(Page<ProductRecord> page, ProductRecord productRecord) {
		return super.findPage(page, productRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductRecord productRecord) {
		super.save(productRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductRecord productRecord) {
		super.delete(productRecord);
	}
	
}