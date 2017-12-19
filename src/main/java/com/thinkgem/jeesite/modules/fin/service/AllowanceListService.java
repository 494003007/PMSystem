/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;
import com.thinkgem.jeesite.modules.fin.dao.AllowanceListDao;

/**
 * 补贴名单Service
 * @author cdoublej
 * @version 2016-10-12
 */
@Service
@Transactional(readOnly = true)
public class AllowanceListService extends CrudService<AllowanceListDao, AllowanceList> {


	public AllowanceList get(String id) {
		AllowanceList allowanceList = super.get(id);
		return allowanceList;
	}

	public List<AllowanceList> findList(AllowanceList allowanceList) {
		return super.findList(allowanceList);
	}

	public Page<AllowanceList> findPage(Page<AllowanceList> page, AllowanceList allowanceList) {
		return super.findPage(page, allowanceList);
	}

	@Transactional(readOnly = false)
	public void save(AllowanceList allowanceList) {
		super.save(allowanceList);
	}

	@Transactional(readOnly = false)
	public void delete(AllowanceList allowanceList) {
		super.delete(allowanceList);
	}

}