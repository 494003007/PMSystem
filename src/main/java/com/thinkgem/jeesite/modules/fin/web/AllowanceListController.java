/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import java.util.ArrayList;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;
import com.thinkgem.jeesite.modules.per.service.AllowanceConstantService;
import com.thinkgem.jeesite.modules.fin.service.AllowanceListService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;

/**
 * 内容管理Controller
 * 
 * @author ThinkGem
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/allowanceList")
public class AllowanceListController extends BaseController {

	@Autowired
	private AllowanceConstantService allowanceConstantService;
	@Autowired
	private AllowanceListService allowanceListService;
	@Autowired
	private EmployeeService employeeService;
	@ModelAttribute
	public AllowanceList get(@RequestParam(required = false) String id) {
		AllowanceList entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = allowanceListService.get(id);
		}
		if (entity == null) {
			entity = new AllowanceList();
		}
		return entity;
	}

	@RequestMapping(value = "index")
	public String index() {
		return "modules/fin/allowanceIndex";
	}

	@RequestMapping(value = "tree")
	public String tree(Model model) {
		model.addAttribute("categoryList",allowanceConstantService.findList(null));
		return "modules/fin/allowanceTree";
	}

	@RequiresPermissions("fin:allowanceList:view")
	@RequestMapping(value = { "list", "" })
	public String list(AllowanceList allowanceList, HttpServletRequest request,HttpServletResponse response, Model model) {
		Page<AllowanceList> page = allowanceListService.findPage(new Page<AllowanceList>(request, response), allowanceList);
		model.addAttribute("page", page);
		model.addAttribute("isallowanceConstantId", request.getParameter("allowanceConstantId"));
		return "modules/fin/allowanceListList";
	}

	@RequiresPermissions("fin:allowanceList:view")
	@RequestMapping(value = "form")
	public String form(AllowanceList allowanceList, Model model,HttpServletRequest request, HttpServletResponse response) {		
		
		
		if(request.getParameter("allowanceConstantId") != null && request.getParameter("allowanceConstantId") != ""){
			allowanceList.setAllowanceConstantId(Integer.parseInt(request.getParameter("allowanceConstantId")));			
		}
		String type = request.getParameter("allowanceConstantId");
		if(allowanceList == null){
			allowanceList = (AllowanceList) request.getSession().getAttribute("allowanceListsession");
			request.getSession().removeAttribute("allowanceListsession");
		}
		model.addAttribute("allowanceList", allowanceList);
		@SuppressWarnings("unchecked")		
		ArrayList list = (ArrayList) request.getSession().getAttribute("employeeList");
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		if(list != null && list.size() > 0)
		for(int i = 0;i < list.size();i++){
			ArrayList list2 = (ArrayList) list.get(i);
			if(list2.get(0) == allowanceList.getAllowanceConstantId())
				employeeList.add(employeeService.get(list2.get(1).toString()));
		}
		
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("isallowanceConstantId", type);
		return "modules/fin/allowanceListForm";
	}
	
	
	@RequiresPermissions("fin:allowanceList:view")
	@RequestMapping(value = "deleteform")
	public String deleteform(AllowanceList allowanceList, Model model,HttpServletRequest request, HttpServletResponse response) {		
		allowanceList = (AllowanceList) request.getSession().getAttribute("allowanceListsession");
		request.getSession().removeAttribute("allowanceListsession");
		if(request.getParameter("allowanceConstantId") != null && request.getParameter("allowanceConstantId") != ""){
			allowanceList.setAllowanceConstantId(Integer.parseInt(request.getParameter("allowanceConstantId")));			
		}
		String type = request.getParameter("allowanceConstantId");
		
		model.addAttribute("allowanceList", allowanceList);
		@SuppressWarnings("unchecked")		
		ArrayList list = (ArrayList) request.getSession().getAttribute("employeeList");
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		if(list != null && list.size() > 0)
		for(int i = 0;i < list.size();i++){
			ArrayList list2 = (ArrayList) list.get(i);
			if(list2.get(0) == allowanceList.getAllowanceConstantId())
				employeeList.add(employeeService.get(list2.get(1).toString()));
		}
		
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("isallowanceConstantId", type);
		return "modules/fin/allowanceListForm";
	}

	@RequiresPermissions("fin:allowanceList:view")
	@RequestMapping(value = "form2")
	public String form2(AllowanceList allowanceList, Model model) {
		model.addAttribute("allowanceList", allowanceList);
		return "modules/fin/allowanceListForm2";
	}

	@RequiresPermissions("fin:allowanceList:edit")
	@RequestMapping(value = "save")
	public String save(AllowanceList allowanceList, Model model,RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		if (!beanValidator(model, allowanceList)) {
			return form(allowanceList, model, request, response);
		}
		allowanceListService.save(allowanceList);
		addMessage(redirectAttributes, "保存补贴员工成功");
		return "redirect:" + Global.getAdminPath() + "/fin/allowanceList/?repage";
	}

	@RequiresPermissions("fin:allowanceList:edit")
	@RequestMapping(value = "delete")
	public String delete(Model model,AllowanceList allowanceList,RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		String type = request.getParameter("allowanceConstantId");
		allowanceListService.delete(allowanceList);
		addMessage(redirectAttributes, "删除补贴员工成功");
		model.addAttribute("isallowanceConstantId", type);
		return "redirect:" + Global.getAdminPath() + "/fin/allowanceList/?allowanceConstantId="+type;
	}
}
