/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;
import com.thinkgem.jeesite.modules.fin.service.AllowanceListService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;

/**
 * 员工Controller
 * @author cdoublej
 * @version 2016-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/per/employeeTemple")
public class EmployeeTempleController extends BaseController {
	@Autowired
	private AllowanceListService allowanceListService;
	@Autowired
	private EmployeeService employeeService;
	Employee employee = null;
	AllowanceList allowanceList = null;
	@ModelAttribute
	public void get(@RequestParam(required=false) String id,@RequestParam(required=false) String allowanceConstantId) {
		if (StringUtils.isNotBlank(id)){
			employee = employeeService.get(id);
		}
		if (employee == null){
			employee = new Employee();
		}
		if (StringUtils.isNotBlank(allowanceConstantId)) {
			allowanceList = allowanceListService.get(allowanceConstantId);
		}
		if (allowanceList == null) {
			allowanceList = new AllowanceList();
		}		
	}
	
	@RequiresPermissions("per:employee:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {		
		Integer type =Integer.parseInt(request.getParameter("allowanceConstantId"));//常量类型	
		AllowanceList query = new AllowanceList();
		query.setAllowanceConstantId(type);
		ArrayList<AllowanceList> allowanceLists = (ArrayList<AllowanceList>) allowanceListService.findList(query);//查询对应类型的补贴list
		
		ArrayList<String> arrayList1 = new ArrayList<String>();//存放数据库中已经存在的特定类型的补贴名单的employeeId
		ArrayList<String> arrayList2 = new ArrayList<String>();//存放临时特定类型的补贴名单employeeid
		for(int i = 0;i < allowanceLists.size();i++){		
			arrayList1.add(((AllowanceList)allowanceLists.get(i)).getEmployeeId().toString());
		}
		HttpSession session = request.getSession(true);
		ArrayList employees = (ArrayList) session.getAttribute("employeeList");//全部临时数据
		if(employees != null){
			for(int i = 0;i < employees.size();i++){
				ArrayList employeeList = (ArrayList) employees.get(i);
					if(employeeList.get(0) == type)
						arrayList2.add(employeeList.get(1).toString());
			}
		}
		arrayList2.addAll(arrayList1);
		employee.setReducelist(arrayList2);
		Page<Employee> page = employeeService.findEmployeeTemple(new Page<Employee>(request, response),employee); 
		model.addAttribute("page", page);
		model.addAttribute("isallowanceConstantId", type);
		return "modules/per/employeeListTemple";
	}
	
	
	
	@RequiresPermissions("per:employee:view")
	@RequestMapping("employeeTemple")
	public String employeeTemple(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {		
		Page<Employee> page = employeeService.findEmployeeTemple(new Page<Employee>(request, response),employee); 
		String selecttype = request.getParameter("selecttype");
		if(request.getParameter("positiontype")!=null && !request.getParameter("positiontype").equals("")){
			employee.setQuarters(request.getParameter("positiontype"));
			model.addAttribute("positiontype", request.getParameter("positiontype")); 
		}	
		model.addAttribute("selecttype",selecttype);
		model.addAttribute("page", page);
		return "modules/per/employeeSelect";
	}
	@RequiresPermissions("per:employee:view")
	@RequestMapping(value ="selectEmployee",method= RequestMethod.POST)
	public @ResponseBody Map<String,String> selectEmployee(HttpServletRequest request, HttpServletResponse response, Model model) {		
		String employeeid = request.getParameter("employeeid");
 
		Employee employee = employeeService.get(employeeid);
		Map<String, String> map = new HashedMap();
		map.put("employeename", employee.getName());
		map.put("employeemobile", employee.getMobile());
		map.put("employeesex", employee.getSex());
		map.put("employeeemail", employee.getEmail());
		return map;
	}

	@RequiresPermissions("per:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/per/employeeForm";
	}

	@RequiresPermissions("per:employee:edit")
	@RequestMapping(value = "save")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employee)){
			return form(employee, model);
		}
		employeeService.save(employee);
		addMessage(redirectAttributes, "保存员工成功");
		return "redirect:"+Global.getAdminPath()+"/per/employee/?repage";
	}
	
	@RequiresPermissions("per:employee:edit")
	@RequestMapping(value = "delete")
	public String delete(Employee employee, RedirectAttributes redirectAttributes) {
		employeeService.delete(employee);
		addMessage(redirectAttributes, "删除员工成功");
		return "redirect:"+Global.getAdminPath()+"/per/employee/?repage";
	}
	
	@RequestMapping(value = "deleteList")
	public String deleteList(Employee employee, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) throws ParseException {	
		Integer type = Integer.parseInt(request.getParameter("allowanceConstantId"));
		HttpSession session = request.getSession(true);		
		ArrayList list = (ArrayList)session.getAttribute("employeeList");	
		for(int i = 0;i < list.size();i++){
			ArrayList list2 = (ArrayList) list.get(i);
			if(list2.get(0) == type && list2.get(1).equals(employee.getId()))
				list.remove(i);
		}
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		AllowanceList allowanceList = new AllowanceList();
		if(request.getParameter("startdate") != null && request.getParameter("startdate") !="")
			allowanceList.setStartDate(s.parse(request.getParameter("startdate")));
		if(request.getParameter("enddate") != null && request.getParameter("enddate") !="")
		allowanceList.setEndDate(s.parse(request.getParameter("enddate")));
		
		session.setAttribute("employeeList", list);
		session.setAttribute("allowanceListsession",allowanceList);
		addMessage(redirectAttributes, "删除补贴员工成功");
		return "redirect:"+Global.getAdminPath()+"/fin/allowanceList/deleteform/?allowanceConstantId="+type;
	}

	@RequestMapping(value = "addList")
	public String addList(String[] idsArr, HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes,AllowanceList allowanceList) {		
		model.addAttribute("allowanceList", allowanceList);
		int type = allowanceList.getAllowanceConstantId();
		model.addAttribute("isallowanceConstantId", type);
		HttpSession session = request.getSession(true);
		ArrayList list = (ArrayList)session.getAttribute("employeeList");	 
		if(list==null)
			list = new ArrayList();		
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < idsArr.length; i++) {
			ArrayList employeeArray = new ArrayList();
			employeeArray.add(type);
			employeeArray.add(idsArr[i]);			
			list.add(employeeArray);
		}
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		for (int i = 0; i < list.size(); i++) {	
			ArrayList array = (ArrayList) list.get(i);
			if(array.get(0) == allowanceList.getAllowanceConstantId())
				employeeList.add(employeeService.get(array.get(1).toString()));
		}
		session.setAttribute("employeeList", list);
		model.addAttribute("employeeList", employeeList);

		addMessage(redirectAttributes, "添加补贴员工成功");
	//	return "redirect:"+Global.getAdminPath()+"/fin/allowanceList/form/??allowanceConstantId="+type;
		return "modules/fin/allowanceListForm";
	}
	//批量添加补贴员工
	@RequestMapping(value = "addsave")
	public String addsave(String allowance_type,String startDate, String endDate, RedirectAttributes redirectAttributes, HttpServletRequest request) throws ParseException {		
		HttpSession session = request.getSession(true);
		ArrayList list = (ArrayList) session.getAttribute("employeeList");	
		for(int i = 0; i < list.size(); i++){
			ArrayList employeeList = (ArrayList) list.get(i);
			if(employeeList.get(0).toString().equals(allowance_type)){
				AllowanceList allowanceList = new AllowanceList();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
				allowanceList.setStartDate(sdf.parse(startDate));
				allowanceList.setEndDate(sdf.parse(endDate));
				allowanceList.setAllowanceConstantId(Integer.parseInt(allowance_type));
				allowanceList.setEmployeeId(Integer.parseInt(employeeList.get(1).toString()));
				allowanceListService.save(allowanceList);		
			}
		}
		ArrayList list1 = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			ArrayList employeeList = (ArrayList) list.get(i);
			if(!employeeList.get(0).toString().equals(allowance_type))
				list1.add(list.get(i));
		}
	
		session.setAttribute("employeeList",list1);
		addMessage(redirectAttributes, "添加补贴员工成功");
		return "redirect:"+Global.getAdminPath()+"/fin/allowanceList/form/?allowanceConstantId="+allowance_type;
	}	
}