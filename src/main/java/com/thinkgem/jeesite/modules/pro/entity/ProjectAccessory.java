/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目附件Entity
 * @author cdoublej
 * @version 2016-11-24
 */
public class ProjectAccessory extends DataEntity<ProjectAccessory> {
	
	private static final long serialVersionUID = 1L;
	private String url;		// 附件路径
	private Integer projectId;		// 所属项目编号
	
	public ProjectAccessory() {
		super();
	}

	public ProjectAccessory(String id){
		super(id);
	}

	@Length(min=0, max=50, message="附件路径长度必须介于 0 和 50 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
}