/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractPayRegister;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * outcontract_billDAO接口
 * @author czy
 * @version 2016-12-10
 */
@MyBatisDao
public interface OutcontractPayRegisterDao extends CrudDao<OutcontractPayRegister> {
    public String getSumBill(@Param("subId") String subId);

    public String getCountBill(@Param("subId") String subId);

    public List<OutcontractPayRegister> findListBysubIds(@Param("subIds") String subIds);

	public Map<String, String> getInfo(Outcontract outcontract);

	public List<OutcontractPayRegister> findExamineList(OutcontractPayRegister outcontractPayRegister);

	public void updateRegister(OutcontractPayRegister outcontractPayRegister);

	public OutcontractPayRegister get2(String outcontractCheckId);

	public List<OutcontractPayRegister> findBillPayList(OutcontractPayRegister outcontractPayRegister);

	public List<OutcontractPayRegister> findPayList(OutcontractPayRegister outcontractPayRegister);

	public OutcontractPayRegister getPrint(String outcontractCheckId);

	public List<OutcontractPayRegister> getPayList(String outcontractId);


}