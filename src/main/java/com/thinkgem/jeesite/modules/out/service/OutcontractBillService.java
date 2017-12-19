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
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;
import com.thinkgem.jeesite.modules.out.dao.OutcontractBillDao;

/**
 * outcontract_billService
 * @author czy
 * @version 2016-12-10
 */
@Service
@Transactional(readOnly = true)
public class OutcontractBillService extends CrudService<OutcontractBillDao, OutcontractBill> {
	@Autowired
	private OutcontractBillDao  outcontractBillDao;

	public OutcontractBill get(String id) {
		return super.get(id);
	}
	
	public List<OutcontractBill> findList(OutcontractBill outcontractBill) {
		return super.findList(outcontractBill);
	}
	
	public Page<OutcontractBill> findPage(Page<OutcontractBill> page, OutcontractBill outcontractBill) {
		return super.findPage(page, outcontractBill);
	}
	
	@Transactional(readOnly = false)
	public void save(OutcontractBill outcontractBill) {
		super.save(outcontractBill);
	}
	
	@Transactional(readOnly = false)
	public void delete(OutcontractBill outcontractBill) {
		super.delete(outcontractBill);
	}

	public String getSumBill(String subId) {
		return outcontractBillDao.getSumBill(subId);
	}

	public String getCountBill(String subId) {
		return outcontractBillDao.getCountBill(subId);
	}

	public List<OutcontractBill> findListBysubIds(String subIds) {
			return outcontractBillDao.findListBysubIds(subIds);
	}

	public List<OutcontractBill> findListByprojectId(String projectId) {
			return outcontractBillDao.findListByprojectId(projectId);
	}

	public Map<String, String> getInfo(Outcontract outcontract) {
		// TODO 自动生成的方法存根
		return dao.getInfo(outcontract);
	}

	public OutcontractBill get2(OutcontractBill outcontractBill) {
		// TODO 自动生成的方法存根
		return dao.get2(outcontractBill);
	}
	@Transactional(readOnly = false)
	public void updateBillAmount(String outcontractBillId, Double payAmount) {
		dao.updateBillAmount(outcontractBillId,payAmount);
		
	}

	public Map<String, String> getprintBill(String projectId,
			String outcontractId) {
		// TODO 自动生成的方法存根
		return dao.getprintBill(projectId,outcontractId);
	}

	public Map<String, String> getprintPay(String projectId,
			String outcontractId) {
		// TODO 自动生成的方法存根
		return dao.getprintPay(projectId,outcontractId);
	}
	public List<OutcontractBill> findBillList(String outcontractId){
		return dao.findBillList(outcontractId);
	}
}