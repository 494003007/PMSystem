/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;

/**
 * 外包登记DAO接口
 * 
 * @author LKY
 * @version 2016-11-22
 */
@MyBatisDao
public interface OutcontractDao extends TreeDao<Outcontract> {
	public List<Outcontract> findList1(Outcontract outcontract);

	public List<Outcontract> find111List(Outcontract outcontract);

	public void updatestatus1(Outcontract outcontract);

	public List<Outcontract> findnewList(Outcontract outcontract);

	public List<Outcontract> findCheckList(Outcontract outcontract);
}