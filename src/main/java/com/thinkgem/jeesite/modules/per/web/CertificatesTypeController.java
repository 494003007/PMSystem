/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.per.entity.CertificatesType;
import com.thinkgem.jeesite.modules.per.service.CertificatesTypeService;

/**
 * 证书类型Controller
 * @author cdoublej
 * @version 2016-10-09
 */
@Controller
@RequestMapping(value = "${adminPath}/per/certificatesType")
public class CertificatesTypeController extends BaseController {

	@Autowired
	private CertificatesTypeService certificatesTypeService;
	
	@ModelAttribute
	public CertificatesType get(@RequestParam(required=false) String id) {
		CertificatesType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = certificatesTypeService.get(id);
		}
		if (entity == null){
			entity = new CertificatesType();
		}
		return entity;
	}
	
	@RequiresPermissions("per:certificatesType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CertificatesType certificatesType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CertificatesType> page = certificatesTypeService.findPage(new Page<CertificatesType>(request, response), certificatesType); 
		model.addAttribute("page", page);
		return "modules/per/certificatesTypeList";
	}

	@RequiresPermissions("per:certificatesType:view")
	@RequestMapping(value = "form")
	public String form(CertificatesType certificatesType, Model model) {
		model.addAttribute("certificatesType", certificatesType);
		return "modules/per/certificatesTypeForm";
	}

	@RequiresPermissions("per:certificatesType:edit")
	@RequestMapping(value = "save")
	public String save(CertificatesType certificatesType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, certificatesType)){
			return form(certificatesType, model);
		}
		certificatesTypeService.save(certificatesType);
		addMessage(redirectAttributes, "保存证书类型成功");
		return "redirect:"+Global.getAdminPath()+"/per/certificatesType/?repage";
	}
	
	@RequiresPermissions("per:certificatesType:edit")
	@RequestMapping(value = "delete")
	public String delete(CertificatesType certificatesType, RedirectAttributes redirectAttributes) {
		try{
			certificatesTypeService.delete(certificatesType);
		}catch(Exception e){
			addMessage(redirectAttributes, "证件类型已经被关联，不能删除该证件类型");
			return "redirect:"+Global.getAdminPath()+"/per/certificatesType/?repage";
		}
		addMessage(redirectAttributes, "删除证书类型成功");
		return "redirect:"+Global.getAdminPath()+"/per/certificatesType/?repage";
	}

}