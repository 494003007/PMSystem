/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

 

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;

import com.thinkgem.jeesite.modules.fin.service.AllowanceListService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 考勤人员Controller
 * 
 * @author ThinkGem
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/att/attendance")
public class AttendanceController extends BaseController {
	@Autowired
	private PositionTypeService positionTypeService;
	@Autowired
	private AttendanceListService attendanceListService;
 
	@Autowired
	private AllowanceListService allowanceListService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired 
	private SysAreaService sysAreaService;
	@Autowired 
	private AttendanceListDetailService attendanceListDetailService;
	@Autowired 
	private ProjectAttendanceService projectAttendanceService;
	
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
	public String index(HttpServletRequest request,HttpServletResponse response) {	
		User user = UserUtils.getUser();
		String employeeId = user.getEmployeeId().toString();// 系统用户员工编号
		
		Employee employee = employeeService.get(employeeId);// 参数要改成自动获取！！！！
		List<SysArea> bigsysAreaList = null;

		String positionId = employee==null || employee.getQuarters().equals("")?"0":employee.getQuarters();
		String positionName = positionTypeService.get(positionId)==null?"":positionTypeService.get(positionId).getName();// 岗位名称

		
		ArrayList<Project> projectlist = null;
		ArrayList<AttendanceTree> list = new ArrayList<AttendanceTree>();
		String smallsysName = null;// 项目所属区域名称
		AttendanceTree attendanceTree = new AttendanceTree();
		
		String userId = user.getId();
 
		
		Project project = new Project();
		project.setExaminestatus("2");
		if(user.getId().equals("1") || user.getId().equals("2") )//超级管理员所有视图权限
		{
			
		}else
			project.setAuthorityEmployeeId(employeeId);
		projectlist = (ArrayList<Project>) projectService.findList(project);
	 
		 
		
		/*
			if (positionName.equals("项目经理")) {// 只是项目经理的话，只需要查询工号下面的项目
				bigsysAreaList = sysAreaService.projectSysAreaTree("employee_id",employeeId);//根据project表获取项目 所有大区
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					attendanceTree = new AttendanceTree();
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
					projectlist = (ArrayList<Project>) projectService.findList(project);
					smallsysName = sa.getName();
					attendanceTree.setTreeid(sa.getId());
					attendanceTree.setBigSysAreaid(sa.getParent().getId()==null?"0":sa.getParent().getId());
					attendanceTree.setTreeNodeName(smallsysName);
					attendanceTree.setProjectList(projectlist);
					list.add(attendanceTree);
				}
			} else if (positionName.equals("区域经理")) {// 如果是 区域经理。得查询对应区域下的所有项目
				bigsysAreaList = sysAreaService.projectSysAreaTree("spm_employee_id",employeeId);//根据project表获取项目 所有大区
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("spm_employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					attendanceTree = new AttendanceTree();
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
			 
					projectlist = (ArrayList<Project>) projectService.findList(project);
					smallsysName = sa.getName();
					attendanceTree.setTreeid(sa.getId());
					attendanceTree.setBigSysAreaid(sa.getParent().getId()==null?"0":sa.getParent().getId());
					attendanceTree.setTreeNodeName(smallsysName);
					attendanceTree.setProjectList(projectlist);
					list.add(attendanceTree);
				}
			} else if (positionName.equals("大区经理")) {// 如果是大区经理。得查询对应区域下的所有分区、项目
				bigsysAreaList = sysAreaService.projectSysAreaTree("bpm_employee_id",employeeId);//根据project表获取项目 所有大区
				ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("bpm_employee_id",employeeId);//根据project 获取所有区域
				for (int i = 0; i < allChildrenList.size(); i++) {
					SysArea sa = allChildrenList.get(i);
					attendanceTree = new AttendanceTree();
					Project project = new Project();
					project.setExaminestatus("2");
					project.setSysAreaId(sa.getId());
					project.setAuthorityEmployeeId(employeeId);
					projectlist = (ArrayList<Project>) projectService.findList(project);
					smallsysName = sa.getName();
					attendanceTree.setTreeid(sa.getId());
					attendanceTree.setBigSysAreaid(sa.getParent().getId()==null?"0":sa.getParent().getId());
					attendanceTree.setTreeNodeName(smallsysName);
					attendanceTree.setProjectList(projectlist);
					list.add(attendanceTree);
				}
			}
		*/
	 
		
		
		
		HttpSession session = request.getSession(true);
		session.setAttribute("bigsysAreaList", bigsysAreaList);
		session.setAttribute("projectList", projectlist);//項目編號所有  list
		return "modules/att/attendanceIndex";
	}

	@RequestMapping(value = "tree")
	public String tree(Model model,HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String bigsysName = (String) session.getAttribute("bigsysName");
		session.removeAttribute("bigsysName");
		ArrayList<AttendanceTree>  list = (ArrayList<AttendanceTree>) session.getAttribute("projectList");
		model.addAttribute("list",list);
		model.addAttribute("bigsysName",bigsysName);
		return "modules/att/attendanceTree";
	}

	@RequiresPermissions("att:attendance:view")
	@RequestMapping(value = { "list", "" })
	public String list(Employee employee, HttpServletRequest request,HttpServletResponse response, Model model) {
		HttpSession session = request.getSession(true);
/*		ArrayList<AttendanceTree>  list = (ArrayList<AttendanceTree>) session.getAttribute("projectList");
	
		String projectId = ",";
		
		if(list != null)
		for(int i = 0; i < list.size();i++){
			AttendanceTree attendanceTree = list.get(i);
			List<Project>  e = attendanceTree.getProjectList();
			for(int j = 0; j < e.size();j ++){
				projectId += e.get(j).getId()+",";
			}			
		}*/
		String projectId ;
		List<Project>  e = (ArrayList<Project>) session.getAttribute("projectList");

		projectId = e.get(0).getId();

		String sysAreaId = null;
 
		 if(request.getParameter("projectId") != null)
				projectId = request.getParameter("projectId");
		 
		 
 
		try{
			sysAreaId = request.getParameter("sysAreaId");		 
		}catch(Exception w){
			sysAreaId = "";
		}
		Project project = new Project();
		project.setId(projectId.toString());
		employee.setProject(project);
	//	User user = UserUtils.getUser();
	//	String employeeId = user.getEmployeeId().toString();//系统用户员工编号
				
	//	employee.setOrderId(Integer.parseInt(employeeId));//这里用orderid暂时存放项目经理id。参数要改成自动获取！！！！
		Page<Employee> employeepage =  projectService.chooseList(new Page<Employee>(request, response),employee);
		model.addAttribute("page", employeepage);
		model.addAttribute("projectId", projectId);
	//	session.removeAttribute("projectList");
		return "modules/att/attendanceEmployeeList";
	}


	@RequiresPermissions("att:attendance:edit")
	@RequestMapping(value = "delete")
	public String delete(Model model,AttendanceList attendanceList,RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		String projectId;
		try{
			projectId = request.getParameter("projectId2");
		}catch(Exception w){
			projectId = "";
		}
		String attendancelistid = request.getParameter("attendancelistid");
		attendanceList.setId(attendancelistid);
		attendanceListService.delete(attendanceList);
		addMessage(redirectAttributes, "考勤人员删除成功");
	//	model.addAttribute("isallowanceConstantId", type);
		if(projectId=="")
			return "redirect:" + Global.getAdminPath() + "/att/attendance/list";
		else
			return "redirect:" + Global.getAdminPath() + "/att/attendance/list?projectId="+projectId;
	}
	

	@RequestMapping(value = "addList")
	public String addList(String[] idsArr, String projectid,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) throws ParseException {
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String thismonth = s.format(date).substring(0,7);
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < idsArr.length; i++) {
			String employeeId = idsArr[i].toString();
			//添加员工信息到attendanceList
			AttendanceList attendanceList = new AttendanceList();
			attendanceList.setEmployeeId(Integer.parseInt(employeeId));
			attendanceList.setProjectId(projectid);
			int count = attendanceListService.isExist(attendanceList);
			if(count == 0)
				attendanceListService.insert(attendanceList);
			else
				attendanceListService.updateDelFlag(attendanceList);
			AttendanceListDetail attendanceListDetail2 = new AttendanceListDetail();
			attendanceListDetail2.setEmployeeId(employeeId);		 
			attendanceListDetail2.setProjectId(projectid);
			attendanceListDetail2.setAttendanceMonth(s.parse(thismonth+"-01"));
			ArrayList<AttendanceListDetail> list = attendanceListDetailService.isexist(attendanceListDetail2);
			
			if(list.size() == 0){
				//添加员工当月考勤信息
				AttendanceListDetail attendanceListDetail = new AttendanceListDetail();
				attendanceListDetail.setAttendanceMonth(s.parse(thismonth+"-01"));
				attendanceListDetail.setEmployeeId(idsArr[i].toString());
				attendanceListDetail.setAttendanceSum(22);//考勤天数，
				attendanceListDetail.setProjectId(projectid);
				 
				attendanceListDetailService.save(attendanceListDetail);
				
				//添加三条10天考勤信息
				ProjectAttendance  projectAttendance = new ProjectAttendance();				
				projectAttendance.setEmployeeId(Integer.parseInt(employeeId));
				
				Calendar cal = Calendar.getInstance();
		        //设置年份
		        cal.set(Calendar.YEAR,Integer.parseInt(thismonth.substring(0, 4)));
		        //设置月份
		        cal.set(Calendar.MONTH, Integer.parseInt(thismonth.substring(5, 7))-1);
		        //获取某月最大天数
		        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		        //设置日历中月份的最大天数
		        cal.set(Calendar.DAY_OF_MONTH, lastDay);
		        //格式化日期.获取一个月的最后一天
		        String lastDayOfMonth = s.format(cal.getTime());
				projectAttendance.setEndDate(s.parse(lastDayOfMonth)); 
				projectAttendance.setProjectId(Integer.parseInt(projectid));
							
				projectAttendanceService.addThreeAttendance(projectAttendance);
		 }	
			if(date.getDate() < 11){
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH,-1);
				String frontmonth  = s.format(cal.getTime()).substring(0, 7);
				attendanceListDetail2.setAttendanceMonth(s.parse(frontmonth+"-01"));
				ArrayList<AttendanceListDetail> list2 = attendanceListDetailService.isexist(attendanceListDetail2);
				if(list.size() == 0){
					//添加员工当月考勤信息
					AttendanceListDetail attendanceListDetail = new AttendanceListDetail();
					attendanceListDetail.setAttendanceMonth(s.parse(frontmonth+"-01"));
					attendanceListDetail.setEmployeeId(idsArr[i].toString());
					attendanceListDetail.setAttendanceSum(22);//考勤天数，
					attendanceListDetail.setProjectId(projectid);
					 
					attendanceListDetailService.save(attendanceListDetail);
					
					//添加三条10天考勤信息
					ProjectAttendance  projectAttendance = new ProjectAttendance();				
					projectAttendance.setEmployeeId(Integer.parseInt(employeeId));
			        //设置年份
			        cal.set(Calendar.YEAR,Integer.parseInt(frontmonth.substring(0, 4)));
			        //设置月份
			        cal.set(Calendar.MONTH, Integer.parseInt(frontmonth.substring(5, 7))-1);
			        //获取某月最大天数
			        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			        //设置日历中月份的最大天数
			        cal.set(Calendar.DAY_OF_MONTH, lastDay);
			        //格式化日期.获取一个月的最后一天
			        String lastDayOfMonth = s.format(cal.getTime());
					projectAttendance.setEndDate(s.parse(lastDayOfMonth)); 
					projectAttendance.setProjectId(Integer.parseInt(projectid));
								
					projectAttendanceService.addThreeAttendance(projectAttendance);
				}
				
			}
			
		}
		addMessage(redirectAttributes, "添加考勤员工成功");
	//	return "redirect:"+Global.getAdminPath()+"/fin/allowanceList/form/??allowanceConstantId="+type;
		return "redirect:" + Global.getAdminPath() + "/att/attendance/list?projectId="+projectid;
	}
}
