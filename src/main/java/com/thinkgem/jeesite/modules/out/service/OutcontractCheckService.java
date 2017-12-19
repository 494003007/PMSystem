/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.OutcontractCheck;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;
import com.thinkgem.jeesite.modules.out.dao.OutcontractCheckDao;
import com.thinkgem.jeesite.modules.out.dao.OutcontractDao;


/**
 * 外包验收Service
 * @author LKY
 * @version 2016-12-02
 */
@Service
@Transactional(readOnly = true)
public class OutcontractCheckService extends CrudService<OutcontractCheckDao, OutcontractCheck> {
	@Autowired
	private OutcontractCheckDao outcontractCheckDao;
	public int getnumber(String string){
		return outcontractCheckDao.getnumber(string);
	}
	public OutcontractCheck get(String id) {
		return super.get(id);
	}
 
	
	public List<OutcontractCheck> findList(OutcontractCheck outcontractCheck) {
		return super.findList(outcontractCheck);
	}
	
	public Page<OutcontractCheck> findPage(Page<OutcontractCheck> page, OutcontractCheck outcontractCheck) {
		return super.findPage(page, outcontractCheck);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractCheck outcontractCheck) {
		super.save(outcontractCheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractCheck outcontractCheck) {
		super.delete(outcontractCheck);
	}
	public Page<OutcontractCheck> findCheckPage(Page<OutcontractCheck> page,
			OutcontractCheck outcontractCheck) {
		// TODO 自动生成的方法存根
		outcontractCheck.setPage(page);
		page.setList(dao.findCheckList(outcontractCheck));
		return page;
	}
	public Page<OutcontractCheck> findSelectPage(Page<OutcontractCheck> page,
			OutcontractCheck outcontractCheck) {
		// TODO 自动生成的方法存根
		outcontractCheck.setPage(page);
		page.setList(dao.findSelectPage(outcontractCheck));
		return page;
	}
	public OutcontractCheck getAjaxDate(String outcontractCheckId) {
		// TODO 自动生成的方法存根
		return dao.getAjaxDate(outcontractCheckId);
	}



	
}