/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

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
import com.thinkgem.jeesite.modules.pro.entity.ContractCredentials;
import com.thinkgem.jeesite.modules.pro.service.ContractCredentialsService;

/**
 * 合同证件Controller
 * @author czy
 * @version 2016-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/contractCredentials")
public class ContractCredentialsController extends BaseController {

	@Autowired
	private ContractCredentialsService contractCredentialsService;
	
	@ModelAttribute
	public ContractCredentials get(@RequestParam(required=false) String id) {
		ContractCredentials entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractCredentialsService.get(id);
		}
		if (entity == null){
			entity = new ContractCredentials();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:contractCredentials:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractCredentials contractCredentials, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractCredentials> page = contractCredentialsService.findPage(new Page<ContractCredentials>(request, response), contractCredentials); 
		model.addAttribute("page", page);
		return "modules/pro/contractCredentialsList";
	}

	@RequiresPermissions("pro:contractCredentials:view")
	@RequestMapping(value = "form")
	public String form(ContractCredentials contractCredentials, Model model) {
		model.addAttribute("contractCredentials", contractCredentials);
		return "modules/pro/contractCredentialsForm";
	}

	@RequiresPermissions("pro:contractCredentials:edit")
	@RequestMapping(value = "save")
	public String save(ContractCredentials contractCredentials, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractCredentials)){
			return form(contractCredentials, model);
		}
		contractCredentialsService.save(contractCredentials);
		addMessage(redirectAttributes, "保存合同证件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractCredentials/?repage";
	}
	
	@RequiresPermissions("pro:contractCredentials:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractCredentials contractCredentials, RedirectAttributes redirectAttributes) {
		contractCredentialsService.delete(contractCredentials);
		addMessage(redirectAttributes, "删除合同证件成功");
		return "redirect:"+Global.getAdminPath()+"/pro/contractCredentials/?repage";
	}

}