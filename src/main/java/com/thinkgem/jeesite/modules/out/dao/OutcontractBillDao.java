/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractBill;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Map;

/**
 * outcontract_billDAO接口
 * @author czy
 * @version 2016-12-10
 */
@MyBatisDao
public interface OutcontractBillDao extends CrudDao<OutcontractBill> {
    public String getSumBill(@Param("subId") String subId);

    public String getCountBill(@Param("subId") String subId);

    public List<OutcontractBill> findListBysubIds(@Param("subIds") String subIds);

    public List<OutcontractBill> findListByprojectId(@Param("projectId") String projectId);

	public Map<String, String> getInfo(Outcontract outcontract);

	public OutcontractBill get2(OutcontractBill outcontractBill);

	public void updateBillAmount(@Param("outcontractBillId")String outcontractBillId, @Param("payAmount")Double payAmount);

	public Map<String, String> getprintBill(@Param("projectId")String projectId,
			@Param("outcontractId")String outcontractId);
	public Map<String, String> getprintPay(@Param("projectId")String projectId,
			@Param("outcontractId")String outcontractId);
	public List<OutcontractBill> findBillList(@Param("outcontractId") String projectId);

}