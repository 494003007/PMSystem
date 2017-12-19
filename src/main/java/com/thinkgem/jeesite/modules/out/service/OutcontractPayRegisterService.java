/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.out.entity.Outcontract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPayRegister;
import com.thinkgem.jeesite.modules.out.dao.OutcontractPayRegisterDao;

/**
 * outcontract_billService
 * @author czy
 * @version 2016-12-10
 */
@Service
@Transactional(readOnly = true)
public class OutcontractPayRegisterService extends CrudService<OutcontractPayRegisterDao, OutcontractPayRegister> {
	@Autowired
	private OutcontractPayRegisterDao  outcontractBillDao;

	public OutcontractPayRegister get(String id) {
		return super.get(id);
	}
	
	public List<OutcontractPayRegister> findList(OutcontractPayRegister outcontractBill) {
		return super.findList(outcontractBill);
	}
	
	public Page<OutcontractPayRegister> findPage(Page<OutcontractPayRegister> page, OutcontractPayRegister outcontractBill) {
		return super.findPage(page, outcontractBill);
	}

	public Page<OutcontractPayRegister> findBillPayPage(Page<OutcontractPayRegister> page, OutcontractPayRegister outcontractPayRegister) {
		outcontractPayRegister.setPage(page);
		page.setList(dao.findBillPayList(outcontractPayRegister));
		return page;
	}

	public Page<OutcontractPayRegister> findPayPage(Page<OutcontractPayRegister> page, OutcontractPayRegister outcontractPayRegister) {
		outcontractPayRegister.setPage(page);
		page.setList(dao.findPayList(outcontractPayRegister));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractPayRegister outcontractBill) {
		super.save(outcontractBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractPayRegister outcontractBill) {
		super.delete(outcontractBill);
	}

	public String getSumBill(String subId) {
		return outcontractBillDao.getSumBill(subId);
	}

	public String getCountBill(String subId) {
		return outcontractBillDao.getCountBill(subId);
	}

	public List<OutcontractPayRegister> findListBysubIds(String subIds) {
			return outcontractBillDao.findListBysubIds(subIds);
	}

	public Map<String, String> getInfo(Outcontract outcontract) {
		// TODO 自动生成的方法存根
		return dao.getInfo(outcontract);
	}

	public Page<OutcontractPayRegister> findExaminePage(Page<OutcontractPayRegister> page, OutcontractPayRegister outcontractPayRegister) {
		outcontractPayRegister.setPage(page);
		page.setList(dao.findExamineList(outcontractPayRegister));
		return page;
	}
	@Transactional(readOnly = false)
	public void updateRegister(OutcontractPayRegister outcontractPayRegister) {
		dao.updateRegister(outcontractPayRegister);
		
	}

	public OutcontractPayRegister get2(String outcontractCheckId) {
		// TODO 自动生成的方法存根
		return dao.get2(outcontractCheckId);
	}

	public OutcontractPayRegister getPrint(String outcontractCheckId) {
		// TODO 自动生成的方法存根
		return dao.getPrint(outcontractCheckId);
	}

	public List<OutcontractPayRegister> getPayList(String outcontractId){
		return dao.getPayList(outcontractId);
	}

}