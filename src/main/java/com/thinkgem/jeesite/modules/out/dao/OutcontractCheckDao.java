/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractCheck;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPay;

/**
 * 外包验收DAO接口
 * @author LKY
 * @version 2016-12-02
 */
@MyBatisDao
public interface OutcontractCheckDao extends CrudDao<OutcontractCheck> {
public int getnumber(String string);

public List<OutcontractCheck> findCheckList(OutcontractCheck outcontractCheck);

public List<OutcontractCheck> findSelectPage(OutcontractCheck outcontractCheck);

public OutcontractCheck getAjaxDate(String outcontractCheckId);

 
}