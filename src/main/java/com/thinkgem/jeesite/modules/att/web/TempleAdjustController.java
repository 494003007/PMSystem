/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.att.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.thinkgem.jeesite.modules.att.entity.AttendanceList;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.Salary;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.SalaryService;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;
import com.thinkgem.jeesite.modules.excel.service.SalaryReportService;
import com.thinkgem.jeesite.modules.fin.entity.TemporaryAdjustDetail;
import com.thinkgem.jeesite.modules.fin.service.TemporaryAdjustDetailService;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.service.ProjectService;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 临时调整Controller
 * @author cdoublej
 * @version 2016-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/att/templeAdjust")
public class TempleAdjustController extends BaseController {
	@Autowired
	private AttendanceListService attendanceListService;
	@Autowired
	private PositionTypeService positionTypeService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired 
	private SysAreaService sysAreaService;
	@Autowired
	private SalaryReportService salaryReportService;
	@Autowired
	private SalaryService salaryService;
	@Autowired
	private TemporaryAdjustDetailService adjustDetailService;
	@ModelAttribute
	public SalaryReport get(@RequestParam(required=false) String id) {
		SalaryReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salaryReportService.get(id);
		}
		if (entity == null){
			entity = new SalaryReport();
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
		/*if (positionName.equals("项目经理")) {// 只是项目经理的话，只需要查询工号下面的项目
			bigsysAreaList = sysAreaService.projectSysAreaTree("employee_id",employeeId);//根据project表获取项目 所有大区
			ArrayList<SysArea> allChildrenList = (ArrayList<SysArea>) sysAreaService.allSysAreaTree("employee_id",employeeId);//根据project 获取所有区域
			for (int i = 0; i < allChildrenList.size(); i++) {
				SysArea sa = allChildrenList.get(i);
				attendanceTree = new AttendanceTree();
				Project project = new Project();
				project.setExaminestatus("2");
				project.setSysAreaId(sa.getId());
				project.setEmployeeId(Integer.parseInt(employeeId));
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
				project.setSpmemployeeId(Integer.parseInt(employeeId));
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
				project.setBpmemployeeId(Integer.parseInt(employeeId));
				projectlist = (ArrayList<Project>) projectService.findList(project);
				smallsysName = sa.getName();
				attendanceTree.setTreeid(sa.getId());
				attendanceTree.setBigSysAreaid(sa.getParent().getId()==null?"0":sa.getParent().getId());
				attendanceTree.setTreeNodeName(smallsysName);
				attendanceTree.setProjectList(projectlist);
				list.add(attendanceTree);
			}
		}*/
		Project project = new Project();
		project.setExaminestatus("2");
		if(user.getId().equals("1") || user.getId().equals("2") )//超级管理员所有视图权限
		{
			
		}else
			project.setAuthorityEmployeeId(employeeId);
		projectlist = (ArrayList<Project>) projectService.findList(project);
	 
		 
		HttpSession session = request.getSession(true);
		session.setAttribute("bigsysAreaList", bigsysAreaList);
		session.setAttribute("projectList", projectlist);//項目編號所有  list
		return "modules/att/templeAdjustIndex";
	}

	@RequestMapping(value = "tree")
	public String tree(Model model,HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String bigsysName = (String) session.getAttribute("bigsysName");
		session.removeAttribute("bigsysName");
		ArrayList<AttendanceTree>  list = (ArrayList<AttendanceTree>) session.getAttribute("projectList");
		model.addAttribute("list",list);
		model.addAttribute("bigsysName",bigsysName);
		return "modules/att/templeAdjustTree";
	}

	@RequiresPermissions("att:templeAdjust:view")
	@RequestMapping(value = {"list", ""})
	public String list(SalaryReport salaryReport ,HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession(true);
	/*	ArrayList<AttendanceTree>  list = (ArrayList<AttendanceTree>) session.getAttribute("projectList");//获取项目list
	
		String projectId = ",";//所有project 的串，1,2,3，
		String allemployeeId = ",";
		if(list != null)
		for(int i = 0; i < list.size();i++){
			AttendanceTree attendanceTree = list.get(i);
			List<Project>  e = attendanceTree.getProjectList();
			for(int j = 0; j < e.size();j ++){
				projectId += e.get(j).getId()+",";
			}			
		}*/
		String projectId = "";
		List<Project>  e = (ArrayList<Project>) session.getAttribute("projectList");

			projectId = e.get(0).getId();


		if (request.getParameter("projectId") != null)
			projectId = request.getParameter("projectId");	

		AttendanceList alist = new AttendanceList();
		alist.setAllProjectID(projectId);
		List<AttendanceList> alAttendanceList = attendanceListService.findAllEmployee(alist);// 获取项目下的员工
	/*	for (int i = 0; i < alAttendanceList.size(); i++)
			allemployeeId += alAttendanceList.get(i).getEmployeeId().toString() + ",";// 获取全部员工 id的字符串
*/		 
		salaryReport.setProjectids(projectId);
/*		salaryReport.setEmployeeids(allemployeeId);*/
		
		Page<SalaryReport> page = salaryReportService.findTemplePage(new Page<SalaryReport>(request, response), salaryReport); 
		model.addAttribute("page", page);
//		session.removeAttribute("projectList");
		model.addAttribute("projectId", projectId);
		return "modules/att/templeAdjustList";
	}

	
	@RequiresPermissions("att:templeAdjust:edit")
	@RequestMapping(value = "form")
	public String form(SalaryReport salaryReport, Model model,HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("salaryReport", salaryReport);
		String projectId = request.getParameter("projectIds");	
		model.addAttribute("projectId", projectId);
		return "modules/att/templeAdjustForm";
	}
	 

 
	
	@RequiresPermissions("att:templeAdjust:edit")
	@RequestMapping(value = "save")
	public String save(SalaryReport salaryReport, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		TemporaryAdjustDetail temporaryAdjustDetail = null;
		if (salaryReport.getTempleAdjustId()!=null && !salaryReport.getTempleAdjustId().equals("")){
	 
			temporaryAdjustDetail = adjustDetailService.get(salaryReport.getTempleAdjustId());
		}
		else{
			temporaryAdjustDetail = new TemporaryAdjustDetail();
		}
		temporaryAdjustDetail.setAttendanceListDetailId(salaryReport.getId());
		temporaryAdjustDetail.setMoney(Double.parseDouble(salaryReport.getTempleAllowance().replaceAll(",", "")));
		temporaryAdjustDetail.setCreateDate(salaryReport.getAttendanceMonth());
		temporaryAdjustDetail.setEmployeeId(Integer.parseInt(salaryReport.getEmployeeId()));
		String projectId = request.getParameter("projectId");	
		
		adjustDetailService.save(temporaryAdjustDetail);
		addMessage(redirectAttributes, "员工临时调整成功");
		return "redirect:"+Global.getAdminPath()+"/att/templeAdjust/list?projectId="+projectId;
	}
	
	@RequiresPermissions("att:templeAdjust:edit")
	@RequestMapping(value = "delete")
	public String delete(Salary salary, RedirectAttributes redirectAttributes) {
		salaryService.delete(salary);
		addMessage(redirectAttributes, "删除员工薪资基准成功");
		return "redirect:"+Global.getAdminPath()+"/att/templeAdjust/?repage";
	}

}