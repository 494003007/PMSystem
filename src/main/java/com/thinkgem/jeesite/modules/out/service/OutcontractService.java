/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.dao.OutcontractDao;

/**
 * 外包登记Service
 * 
 * @author LKY
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class OutcontractService extends
		TreeService<OutcontractDao, Outcontract> {

	@Autowired
	private OutcontractDao outcontractDao;

	@Transactional(readOnly = false)
	public void updatestatus1(Outcontract outcontract) {
		outcontractDao.updatestatus1(outcontract);
	}

	public List<Outcontract> findnewList(Outcontract outcontract) {
		return outcontractDao.findnewList(outcontract);
	}

	public List<Outcontract> findList1(Outcontract outcontract) {
		return outcontractDao.findList1(outcontract);
	}

	public List<Outcontract> find111List(Outcontract outcontract) {
		// 查找出当前主外包合同及其以下的二级菜单

		return outcontractDao.find111List(outcontract);
	}

	public Outcontract get(String id) {
		return super.get(id);
	}

	public List<Outcontract> findList(Outcontract outcontract) {
		if (StringUtils.isNotBlank(outcontract.getParentIds())) {
			outcontract.setParentIds("," + outcontract.getParentIds() + ",");
		}
		return super.findList(outcontract);
	}

	@Transactional(readOnly = false)
	public void save(Outcontract outcontract) {
		super.save(outcontract);
	}

	@Transactional(readOnly = false)
	public void delete(Outcontract outcontract) {
		super.delete(outcontract);
	}

	public Page<Outcontract> findCheckListPage(Page<Outcontract> page,Outcontract outcontract) {
		// TODO 自动生成的方法存根
		outcontract.setPage(page);
		page.setList(dao.findCheckList(outcontract));
		return page;
	}

}