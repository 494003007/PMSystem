/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.dao.AttendenceTypeDao;

/**
 * 出勤类型Service
 * @author cdoublej
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class AttendenceTypeService extends CrudService<AttendenceTypeDao, AttendenceType> {

	public AttendenceType get(String id) {
		return super.get(id);
	}
	
	public List<AttendenceType> findList(AttendenceType attendenceType) {
		return super.findList(attendenceType);
	}
	
	public Page<AttendenceType> findPage(Page<AttendenceType> page, AttendenceType attendenceType) {
		return super.findPage(page, attendenceType);
	}
	
	@Transactional(readOnly = false)
	public void save(AttendenceType attendenceType) {
		super.save(attendenceType);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttendenceType attendenceType){
		super.delete(attendenceType);
	}
	
}