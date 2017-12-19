/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.pro.entity.Project;

/**
 * 考勤人员树Entity
 * @author cdoublej
 * @version 2016-11-03
 */
public class AttendanceTree implements Serializable{
	private String bigSysAreaid;//大区id
	private String treeid;//区域id
	private String treeNodeName;//区域名称
	private List<Project> projectList;
	
 
	public String getBigSysAreaid() {
		return bigSysAreaid;
	}
	public void setBigSysAreaid(String bigSysAreaid) {
		this.bigSysAreaid = bigSysAreaid;
	}
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getTreeNodeName() {
		return treeNodeName;
	}
	public void setTreeNodeName(String treeNodeName) {
		this.treeNodeName = treeNodeName;
	}
	public List<Project> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
 
	
}