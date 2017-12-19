/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 区域设置DAO接口
 * @author cdoublej
 * @version 2016-10-11
 */
@MyBatisDao
public interface SysAreaDao extends TreeDao<SysArea> {
	public List<SysArea> getAllList();
	public ArrayList<SysArea> findAllchildren(SysArea s);
	public List<SysArea> projectSysAreaTree(@Param("type")String type,@Param("employeeid") String employeeId);
	public ArrayList<SysArea> allSysAreaTree(@Param("type")String type,@Param("employeeid") String employeeId);
	public List<SysArea> allProjectSysAreaTree();
}