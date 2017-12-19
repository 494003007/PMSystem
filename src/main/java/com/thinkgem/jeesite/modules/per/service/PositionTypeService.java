/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.dao.PositionTypeDao;

/**
 * 岗位Service
 * @author cdoublej
 * @version 2016-10-18
 */
@Service
@Transactional(readOnly = true)
public class PositionTypeService extends CrudService<PositionTypeDao, PositionType> {

	public PositionType get(String id) {
		return super.get(id);
	}
	
	public List<PositionType> findList(PositionType positionType) {
		return super.findList(positionType);
	}
	
	public Page<PositionType> findPage(Page<PositionType> page, PositionType positionType) {
		return super.findPage(page, positionType);
	}
	
	@Transactional(readOnly = false)
	public void save(PositionType positionType) {
		super.save(positionType);
	}
	
	@Transactional(readOnly = false)
	public void delete(PositionType positionType) {
		super.delete(positionType);
	}
	
}