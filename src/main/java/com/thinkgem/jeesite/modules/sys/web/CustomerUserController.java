/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;
import com.thinkgem.jeesite.modules.sys.service.CustomerUserService;

/**
 * 客户Controller
 * @author cdoublej
 * @version 2016-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/customerUser")
public class CustomerUserController extends BaseController {
	
	@Autowired
	private CustomerUserService customerUserService;
	
	@ModelAttribute
	public CustomerUser get(@RequestParam(required=false) String id) {
		CustomerUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerUserService.get(id);
		}
		if (entity == null){
			entity = new CustomerUser();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:customerUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerUser customerUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerUser> page = customerUserService.findPage(new Page<CustomerUser>(request, response), customerUser); 
		model.addAttribute("page", page);
		return "modules/sys/customerUserSelect";
	}

	@RequiresPermissions("sys:customerUser:view")
	@RequestMapping(value = "form")
	public String form(CustomerUser customerUser, Model model) {
		model.addAttribute("customerUser", customerUser);
		return "modules/sys/customerUserForm";
	}

	@RequiresPermissions("sys:customerUser:edit")
	@RequestMapping(value = "save")
	public String save(CustomerUser customerUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerUser)){
			return form(customerUser, model);
		}
		customerUserService.save(customerUser);
		addMessage(redirectAttributes, "保存客户成功");
		return "redirect:"+Global.getAdminPath()+"/sys/customerUser/?repage";
	}
	
	@RequiresPermissions("sys:customerUser:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerUser customerUser, RedirectAttributes redirectAttributes) {
		customerUserService.delete(customerUser);
		addMessage(redirectAttributes, "删除客户成功");
		return "redirect:"+Global.getAdminPath()+"/sys/customerUser/?repage";
	}

	@RequestMapping(value ="customerUserSelect",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> customerUserSelect(HttpServletRequest request, HttpServletResponse response, Model model) {		
		String customerid = request.getParameter("customeruserid");	 
		CustomerUser customerUser = customerUserService.get(customerid);
		Map<String, String> map = new HashedMap();
		map.put("customerUser_Moblie", customerUser.getMoblie());		 
		return map;
	}
}