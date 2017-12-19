/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product_record.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.product_record.entity.ProductRecordDb;
import com.thinkgem.jeesite.modules.product_record.dao.ProductRecordDbDao;

/**
 * 数据库更改记录Service
 * @author cdoublej
 * @version 2016-10-10
 */
@Service
@Transactional(readOnly = true)
public class ProductRecordDbService extends CrudService<ProductRecordDbDao, ProductRecordDb> {

	public ProductRecordDb get(String id) {
		return super.get(id);
	}
	
	public List<ProductRecordDb> findList(ProductRecordDb productRecordDb) {
		return super.findList(productRecordDb);
	}
	
	public Page<ProductRecordDb> findPage(Page<ProductRecordDb> page, ProductRecordDb productRecordDb) {
		return super.findPage(page, productRecordDb);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductRecordDb productRecordDb) {
		super.save(productRecordDb);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductRecordDb productRecordDb) {
		super.delete(productRecordDb);
	}
	
}