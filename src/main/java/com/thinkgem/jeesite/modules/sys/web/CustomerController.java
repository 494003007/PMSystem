/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.thinkgem.jeesite.modules.pro.entity.ContractPay;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.CustomerUser;
import com.thinkgem.jeesite.modules.sys.service.CustomerService;
import com.thinkgem.jeesite.modules.sys.service.CustomerUserService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 客户Controller
 * @author cdoublejj
 * @version 2016-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/customer")
public class CustomerController extends BaseController {
	@Autowired
	private CustomerUserService  customerUserService;
	@Autowired
	private CustomerService customerService;
	
	@ModelAttribute
	public Customer get(@RequestParam(required=false) String id) {
		Customer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerService.get(id);
		}
		if (entity == null){
			entity = new Customer();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:customer:view")
	@RequestMapping(value = {"list", ""})
	public String list(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Customer> page = customerService.findPage(new Page<Customer>(request, response), customer); 
		model.addAttribute("page", page);
		return "modules/sys/customerList";
	}
	
	@RequiresPermissions("sys:customer:view")
	@RequestMapping("selectCustomer")
	public String selectCustomer(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Customer> page = customerService.findPage(new Page<Customer>(request, response), customer); 
		String selecttype = request.getParameter("selecttype");
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		return "modules/sys/customerSelect";
	}
	
	@RequiresPermissions("sys:customer:view")
	@RequestMapping(value = "form")
	public String form(Customer customer, Model model) {
		CustomerUser customerUser = new CustomerUser();
		List<CustomerUser> customerUserList = new ArrayList<CustomerUser>();
		String customerId = "";
		try{
			customerId = customer.getId();
		}catch(Exception e){
			customerId = "";
		}
		
		if(customerId != null && !customerId.equals("")){
			customerUser.setCustomerId(Integer.parseInt(customerId));
			customerUserList = customerUserService.findList(customerUser);	
			model.addAttribute("customerUserList", customerUserList);
	 
		}
		model.addAttribute("customer", customer);
		return "modules/sys/customerForm";
	}

	@RequiresPermissions("sys:customer:edit")
	@RequestMapping(value = "save")
	public String save(String [] customeruserlist, String customerid,Customer customer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customer)){
			return form(customer, model);
		}
		if(customerid != null && !customerid.equals(""))
			customerUserService.deleteAll(customerid);
		
		for(int i = 0; customeruserlist.length > i;i+=4){
			CustomerUser customerUser = new CustomerUser();
			if(customerid != null && !customerid.equals(""))
				customerUser.setCustomerId(Integer.parseInt(customerid));
			customerUser.setName(customeruserlist[i]);
			customerUser.setSex(customeruserlist[i + 1]);
			customerUser.setMoblie(customeruserlist[i + 2]);
			customerUser.setRemarks(customeruserlist[i + 3]);
			customerUserService.save(customerUser);
		}
		customerService.save(customer);
		addMessage(redirectAttributes, "保存客户成功");
		return "redirect:"+Global.getAdminPath()+"/sys/customer/?repage";
	}
	
	@RequiresPermissions("sys:customer:edit")
	@RequestMapping(value = "delete")
	public String delete(Customer customer, RedirectAttributes redirectAttributes) {
		try{
			customerService.delete(customer);
		}catch(Exception e){
			addMessage(redirectAttributes, "该客户已被关联，不能删除该客户");
			return "redirect:"+Global.getAdminPath()+"/sys/customer/?repage";
		}
		addMessage(redirectAttributes, "删除客户成功");
		return "redirect:"+Global.getAdminPath()+"/sys/customer/?repage";
	}
	
	@RequestMapping(value ="customerSelect",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> customerSelect(HttpServletRequest request, HttpServletResponse response, Model model) {		
		String customerid = request.getParameter("customerid");	 
		Customer customer = customerService.get(customerid);
		Map<String, String> map = new HashedMap();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		map.put("customer_companyCode", customer.getCompanyCode()+simpleDateFormat.format(date));
		map.put("customer_companyName", customer.getCompanyName());
		 
		return map;
	}
}