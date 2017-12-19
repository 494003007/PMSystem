/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fin.entity.SocialSecurity;
import com.thinkgem.jeesite.modules.fin.dao.SocialSecurityDao;

/**
 * social_securityService
 * @author czy
 * @version 2016-10-13
 */
@Service
@Transactional(readOnly = true)
public class SocialSecurityService extends CrudService<SocialSecurityDao, SocialSecurity> {

	public SocialSecurity get(String id) {
		return super.get(id);
	}
	
	public List<SocialSecurity> findList(SocialSecurity socialSecurity) {
		return super.findList(socialSecurity);
	}
	
	public Page<SocialSecurity> findPage(Page<SocialSecurity> page, SocialSecurity socialSecurity) {
		return super.findPage(page, socialSecurity);
	}
	
	@Transactional(readOnly = false)
	public void save(SocialSecurity socialSecurity) {
		super.save(socialSecurity);
	}
	
	@Transactional(readOnly = false)
	public void delete(SocialSecurity socialSecurity) {
		super.delete(socialSecurity);
	}
	
}