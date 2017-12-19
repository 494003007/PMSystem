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
import com.thinkgem.jeesite.modules.att.entity.AttendanceListDetail;
import com.thinkgem.jeesite.modules.att.entity.AttendanceRecord;
import com.thinkgem.jeesite.modules.att.entity.AttendanceTree;
import com.thinkgem.jeesite.modules.att.entity.AttendenceType;
import com.thinkgem.jeesite.modules.att.entity.ProjectAttendance;
import com.thinkgem.jeesite.modules.att.service.AttendanceListDetailService;
import com.thinkgem.jeesite.modules.att.service.AttendanceListService;
import com.thinkgem.jeesite.modules.att.service.AttendanceRecordService;
import com.thinkgem.jeesite.modules.att.service.AttendenceTypeService;
import com.thinkgem.jeesite.modules.att.service.ProjectAttendanceService;
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
 * 员工考勤Controller
 * 
 * @author cdoublej
 * @version 2016-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/att/attendanceListDetail")
public class AttendanceListDetailController extends BaseController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectAttendanceService projectAttendanceService;
	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private AttendenceTypeService attendenceTypeService;
	@Autowired
	private AttendanceListDetailService attendanceListDetailService;
	@Autowired
	private AttendanceListService attendanceListService;
	@Autowired
	private SysAreaService sysAreaService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionTypeService positionTypeService;

	@ModelAttribute
	public AttendanceListDetail get(@RequestParam(required = false) String id) {
		AttendanceListDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = attendanceListDetailService.get(id);
		}
		if (entity == null) {
			entity = new AttendanceListDetail();
		}
		return entity;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
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
		return "modules/att/attendanceDetailIndex";
	}

	@RequestMapping(value = "tree")
	public String tree(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String bigsysName = (String) session.getAttribute("bigsysName");
		ArrayList<AttendanceTree> list = (ArrayList<AttendanceTree>) session.getAttribute("projectList");
		session.removeAttribute("bigsysName");
		model.addAttribute("list", list);
		model.addAttribute("bigsysName", bigsysName);
		return "modules/att/attendanceDetailTree";
	}

	@RequiresPermissions("att:attendanceListDetail:view")
	@RequestMapping(value = { "list", "" })
	public String list(AttendanceListDetail attendanceListDetail, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat s11 = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		String thismonth = s.format(date).substring(0, 7);// 获取当前年月 2017-09
		String thisday = s.format(date).split("-")[2];// 获取当前日 09
		Employee employee = new Employee();
		// employee.setOaId("1"); //当前用户的 员工编号
		// ArrayList<Employee> employeelist = (ArrayList<Employee>)
		// projectService.chooseList(employee);//查找当前用户项目 下的所有员工

		HttpSession session = request.getSession(true);
		/*ArrayList<AttendanceTree> attendanceTreelist = (ArrayList<AttendanceTree>) session.getAttribute("projectList");// 获取当前用户下的所有项目

		String projectId = ",";
		String allemployeeId = ",";
		// 根据项目获取项目下的所有员工。员工属于项目，项目属于项目经理
		if (attendanceTreelist != null)
			for (int i = 0; i < attendanceTreelist.size(); i++) {
				AttendanceTree attendanceTree = attendanceTreelist.get(i);
				List<Project> e = attendanceTree.getProjectList();
				for (int j = 0; j < e.size(); j++) {
					projectId += e.get(j).getId() + ",";
				}
			}
*/
		String allemployeeId = ",";
		String projectId = "";
		List<Project>  e = (ArrayList<Project>) session.getAttribute("projectList");

		projectId = e.get(0).getId();

		if (request.getParameter("projectId") != null)
			projectId = request.getParameter("projectId");

		AttendanceList alist = new AttendanceList();
		alist.setAllProjectID(projectId);
		List<AttendanceList> alAttendanceList = attendanceListService.findAllEmployee(alist);// 获取项目下的所有员工attendancelist
		for (int i = 0; i < alAttendanceList.size(); i++)
			allemployeeId += alAttendanceList.get(i).getEmployeeId().toString() + ",";// 获取全部员工 id的字符串
	    //判断是否员工当月都有月视图的考勤记录.主要用于给新加的员工添加考勤记录		
		int count = attendanceListService.getEmployeeAttendanceCount(s.parse(thismonth + "-01"));
		
		// 如果不存在当月考勤，添加记录
		if (e != null) //有项目
			if(count != alAttendanceList.size()){ //缺记录
				if(Integer.parseInt(thisday) < 11){ //添加上个月记录
					cal.setTime(date);
					cal.add(Calendar.MONTH,-1);
					String frontmonth  = s.format(cal.getTime()).substring(0, 7);
					int frontcount = attendanceListService.getEmployeeAttendanceCount(s.parse(frontmonth + "-01"));
					if(frontcount != alAttendanceList.size()){//上个月缺记录
						for (int i = 0; i < alAttendanceList.size(); i++) { //遍历所有员工，没有考勤记录的添加当月考勤
							String projectid = alAttendanceList.get(i).getProjectId().toString();
							String employeeId = alAttendanceList.get(i).getEmployeeId().toString();		
							AttendanceListDetail attendanceListDetail2 = new AttendanceListDetail();
							attendanceListDetail2.setEmployeeId(employeeId);
							attendanceListDetail2.setProjectId(projectid);
							attendanceListDetail2.setAttendanceMonth(s.parse(frontmonth + "-01"));
							ArrayList<AttendanceListDetail> list = attendanceListDetailService.isexist(attendanceListDetail2); //是否有当月月视图的记录
							if (list.size() == 0) {
								// 添加员工当月考勤信息
							 				
								AttendanceListDetail attendanceListDetail1 = new AttendanceListDetail();
								attendanceListDetail1.setAttendanceMonth(s.parse(frontmonth + "-01"));
								attendanceListDetail1.setEmployeeId(employeeId);
								attendanceListDetail1.setAttendanceSum(22);// 考勤天数，
								attendanceListDetail1.setProjectId(projectid);
								 
								attendanceListDetailService.save(attendanceListDetail1);
			
								// 添加三条10天考勤信息
								ProjectAttendance projectAttendance = new ProjectAttendance();
								projectAttendance.setEmployeeId(Integer
										.parseInt(employeeId));
			
							
								// 设置年份
								cal.set(Calendar.YEAR,
										Integer.parseInt(frontmonth.substring(0, 4)));
								// 设置月份
								cal.set(Calendar.MONTH,
										Integer.parseInt(frontmonth.substring(5, 7)) - 1);
								// 获取某月最大天数
								int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
								// 设置日历中月份的最大天数
								cal.set(Calendar.DAY_OF_MONTH, lastDay);
								// 格式化日期.获取一个月的最后一天
								String lastDayOfMonth = s.format(cal.getTime());
								projectAttendance.setEndDate(s.parse(lastDayOfMonth));
								projectAttendance.setProjectId(Integer.parseInt(projectid));
			
								projectAttendanceService.addThreeAttendance(projectAttendance);
							}
						  }
					
					}
				}
				for (int i = 0; i < alAttendanceList.size(); i++) { //遍历所有员工，没有考勤记录的添加当月考勤
					String projectid = alAttendanceList.get(i).getProjectId().toString();
					String employeeId = alAttendanceList.get(i).getEmployeeId().toString();
	
					AttendanceListDetail attendanceListDetail2 = new AttendanceListDetail();
					attendanceListDetail2.setEmployeeId(employeeId);
					attendanceListDetail2.setProjectId(projectid);
					attendanceListDetail2.setAttendanceMonth(s.parse(thismonth + "-01"));
					ArrayList<AttendanceListDetail> list = attendanceListDetailService.isexist(attendanceListDetail2); //是否有当月月视图的记录
					if (list.size() == 0) {
						// 添加员工当月考勤信息
					 				
						AttendanceListDetail attendanceListDetail1 = new AttendanceListDetail();
						attendanceListDetail1.setAttendanceMonth(s.parse(thismonth + "-01"));
						attendanceListDetail1.setEmployeeId(employeeId);
						attendanceListDetail1.setAttendanceSum(22);// 考勤天数，
						attendanceListDetail1.setProjectId(projectid);
						 
						attendanceListDetailService.save(attendanceListDetail1);
	
						// 添加三条10天考勤信息
						ProjectAttendance projectAttendance = new ProjectAttendance();
						projectAttendance.setEmployeeId(Integer
								.parseInt(employeeId));
	
					
						// 设置年份
						cal.set(Calendar.YEAR,
								Integer.parseInt(thismonth.substring(0, 4)));
						// 设置月份
						cal.set(Calendar.MONTH,
								Integer.parseInt(thismonth.substring(5, 7)) - 1);
						// 获取某月最大天数
						int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						// 设置日历中月份的最大天数
						cal.set(Calendar.DAY_OF_MONTH, lastDay);
						// 格式化日期.获取一个月的最后一天
						String lastDayOfMonth = s.format(cal.getTime());
						projectAttendance.setEndDate(s.parse(lastDayOfMonth));
						projectAttendance.setProjectId(Integer.parseInt(projectid));
	
						projectAttendanceService.addThreeAttendance(projectAttendance);
					}
				  }
			
			
			}
		attendanceListDetail.setAllProjectID(projectId);
		attendanceListDetail.setEmployeeId(allemployeeId);
		Calendar calender =  Calendar.getInstance();
		Date attendancedate = null;//已经显示的考勤月份 2016-11-01
		//如果十号前，显示上月考勤。过滤作用
		if(date.getDate() < 11){
			
			calender.setTime(date);
			calender.add(Calendar.MONTH,-1);
			attendancedate = s11.parse(s11.format(calender.getTime()));
			attendanceListDetail.setAttendanceMonth(attendancedate);
		}else{
			attendancedate = s11.parse(s11.format(date));
			attendanceListDetail.setAttendanceMonth(attendancedate);
		}
	
		
		// 要展示的数据
		Page<AttendanceListDetail> page = attendanceListDetailService.findPage(
				new Page<AttendanceListDetail>(request, response),
				attendanceListDetail);
		ArrayList<AttendanceListDetail> attendanceList = (ArrayList<AttendanceListDetail>) page.getList();
		ArrayList<AttendanceListDetail> list = new ArrayList<AttendanceListDetail>();
		ArrayList<AttendanceListDetail> list2 = new ArrayList<AttendanceListDetail>();
		list = attendanceList;
//		for (int i = 0; i < attendanceList.size(); i++) {
//			AttendanceListDetail aDetail = attendanceList.get(i);
//			Date attendanceDay = aDetail.getAttendanceMonth(); //
//			String attendancedate = s.format(attendanceDay).substring(0, 7);// 获取考勤年月
//																			// thismonth当前年月
//			if (Integer.parseInt(thisday) > 10) {
//				if (attendancedate.equals(thismonth)) {
//					aDetail.setStatus(1);
//				}
//			} else {
//				String a = thismonth.substring(0, 5);
//				Integer b = Integer.parseInt(thismonth.substring(5, 7)) - 1;// 上个月
//																			// 日
//				String c = b.toString();
//				if (b < 10)
//					c = "0" + c;
//				String thismonth2 = a + c;// 上个月 月
//				if (attendancedate.equals(thismonth2)) {
//					aDetail.setStatus(1);
//				}
//			}
//			if (aDetail.getStatus() == 1)
//				list.add(0, aDetail);
//			else
//				 continue;
//			//list.add(aDetail);
//		}
		int sum = 0;//分页条数
		//判断弹窗要弹出哪个页面
		for (int i = 0; i < list.size(); i++) {
			String employeeID = list.get(i).getEmployeeId();
			String projectAllowanceDetaliID = null;// 当月第几个十天
													// projectAllowanceDetaliID
			String projectId2 = list.get(i).getProjectId();
//			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//			Date d = new Date();
			String thisdate = s.format(date);// 获取当前年月日
//			Integer thisyear = Integer.parseInt(thisdate.split("-")[0]);
//			Integer thismonth1 = Integer.parseInt(thisdate.split("-")[1]);
//			Integer thismonth2 = thismonth1 - 1;
			Integer thisday1 = Integer.parseInt(thisdate.split("-")[2]);
//			String athismonth = thismonth1.toString();
//			String athismonth2 = thismonth2.toString();
//			String athisday = thisday1.toString();
//			if (thismonth1 < 10)
//				athismonth = "0" + athismonth;
//			if (thismonth2 < 10)
//				athismonth2 = "0" + athismonth2;
			ProjectAttendance p = new ProjectAttendance();
			p.setEmployeeId(Integer.parseInt(employeeID));
			p.setProjectId(Integer.parseInt(projectId2));
			p.setStartDate(attendancedate);
//			
//			if (thisday1 <= 10) {
//				p.setStartDate(sd.parse(thisyear.toString() + "-" + athismonth2+ "-01"));
//			}  else {
//				p.setStartDate(sd.parse(thisyear.toString() + "-" + athismonth+ "-01"));
//			}
			
			ArrayList<ProjectAttendance> projectAttendanceList = (ArrayList<ProjectAttendance>) projectAttendanceService.findList(p);
   			for (int i1 = 0; i1 < projectAttendanceList.size(); i1++) {
				String dt = s.format(projectAttendanceList.get(i1).getStartDate()).substring(8, 10);
				if (1 <= thisday1 && thisday1 <= 10) {
					if (dt.equals("21")) {
						projectAllowanceDetaliID = projectAttendanceList.get(i1).getId();
						break;
					}
				} else if (11 <= thisday1 && thisday1 <= 20) {
					if (dt.equals("01")) {
						projectAllowanceDetaliID = projectAttendanceList.get(i1).getId();
						break;
					}
				} else if (21 <= thisday1) {
					if (dt.equals("11")) {
						projectAllowanceDetaliID = projectAttendanceList.get(i1).getId();
						break;
					}
				}
			}

			list.get(i).setProjectAllowanceDetailId(projectAllowanceDetaliID);
			list.get(i).setProjectAllowanceDetailstatu(projectAttendanceService.get(projectAllowanceDetaliID).getStatus());

			if (list.get(i).getProjectAllowanceDetailstatu().equals("1")){
				list.get(i).setStatus(1);
				list2.add(0, list.get(i));
			}
			else{
				// continue;
				list.get(i).setStatus(1);
				list2.add(list.get(i));
			}
			sum ++ ;

		}
		page.setCount(sum);
		page.setList(list2);
	//	session.removeAttribute("projectList");
		model.addAttribute("projectId", projectId);
		model.addAttribute("page", page);
		return "modules/att/attendanceListDetailList";
	}

	@RequiresPermissions("att:attendanceListDetail:view")
	@RequestMapping(value = "form")
	public String form(AttendanceListDetail attendanceListDetail, Model model) {
		model.addAttribute("attendanceListDetail", attendanceListDetail);
		return "modules/att/attendanceListDetailForm";
	}

	@RequiresPermissions("att:attendanceListDetail:view")
	@RequestMapping(value = "select")
	public String select(HttpServletRequest request,HttpServletResponse response, Model model) throws ParseException {
 		String employeeID = request.getParameter("employeeid");
		 String projectAllowanceDetaliID = request.getParameter("projectAllowanceDetailId");//当月第几个十天

		String projectId = request.getParameter("projectId");// 所选项目id

		ProjectAttendance projectAttendance;
		if (projectAllowanceDetaliID == null)
			projectAttendance = new ProjectAttendance();
		else
			projectAttendance = projectAttendanceService.get(projectAllowanceDetaliID);
		// String employeeId = projectAttendance.getEmployeeId().toString();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Integer startdate1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[2]);
		String startdate = startdate1.toString();
		Integer enddate1 = Integer.parseInt(date.format(projectAttendance.getEndDate()).split("-")[2]);
		String enddate = enddate1.toString();
		String year = date.format(projectAttendance.getStartDate()).split("-")[0];
		Integer month1 = Integer.parseInt(date.format(projectAttendance.getStartDate()).split("-")[1]);
		String month = month1.toString();
		int effectiveDate = projectAttendance.getEffectiveDate();
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("year", year);
		model.addAttribute("month", month);

		model.addAttribute("effectiveDate", effectiveDate);
		String statr_date = date.format(projectAttendance.getStartDate());
		String end_date = date.format(projectAttendance.getEndDate());
		// int projectId = projectAttendance.getProjectId();
		ArrayList<AttendanceRecord> list = (ArrayList<AttendanceRecord>) attendanceRecordService.findAttendanceList(projectAllowanceDetaliID);
		model.addAttribute("AttendanceRecordList", list);// 考勤记录
		ArrayList<AttendenceType> attendenceTypelist = (ArrayList<AttendenceType>) attendenceTypeService.findList(null);
		model.addAttribute("attendenceTypelist", attendenceTypelist);// 日历考勤类型
		ArrayList<ProjectAttendance> coeifficientList = (ArrayList<ProjectAttendance>) projectAttendanceService.findCoeifficientList(Integer.parseInt(projectId), statr_date, end_date);
		model.addAttribute("coeifficientList", coeifficientList);// 考勤系数列表
		model.addAttribute("projectAttendance", projectAttendance);// 日历页面个人信息

		return "modules/att/attendanceSelect";
	}

	@RequiresPermissions("att:attendanceListDetail:edit")
	@RequestMapping(value = "save")
	public String save(AttendanceListDetail attendanceListDetail, Model model,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendanceListDetail)) {
			return form(attendanceListDetail, model);
		}
		attendanceListDetailService.save(attendanceListDetail);
		addMessage(redirectAttributes, "保存员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/att/attendanceListDetail/?repage";
	}

	@RequiresPermissions("att:attendanceListDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(AttendanceListDetail attendanceListDetail,RedirectAttributes redirectAttributes) {
		attendanceListDetailService.delete(attendanceListDetail);
		addMessage(redirectAttributes, "删除员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/att/attendanceListDetail/?repage";
	}

	@RequestMapping(value = "addList")
	// projectAttendanceID 十天一条记录。记录编号。employeeID员工编号 idsArr考勤
	// coeifficientList绩效系数 projectMoneyArray项目补贴
	public String addList(String[] addAttendanceArray, String projectId2,String getattendancedate1, String IDhidden,String projectAttendanceID, String employeeID, String[] idsArr,
			String[] coeifficientList, String[] projectMoneyArray,HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes)throws ParseException {
		StringBuilder msg = new StringBuilder();
		ProjectAttendance projectAttendance = projectAttendanceService.get(projectAttendanceID);
		
		// 考勤
		for (int i = 0; i < idsArr.length; i += 2) {
			int projectid = projectAttendance.getProjectId();
			attendanceRecordService.addList(employeeID, idsArr[i],idsArr[i + 1], projectAttendanceID, projectid);
		}

		// 绩效系数
		for (int j = 0; j < coeifficientList.length; j += 2) {

			projectAttendanceService.updatePerformanceCoefficient(coeifficientList[j],coeifficientList[j + 1]);
		}

	/*	if (projectMoneyArray.length != 0) {
		 
			projectAttendance.setProjectMoney(projectMoneyArray[1]);
			projectAttendanceService.save(projectAttendance);
		}*/
		// 加班
		if (addAttendanceArray.length != 0) {
/*	 
			projectAttendance.setAddAttendance((addAttendanceArray[1]));
			projectAttendance.setBeforeAddAttendance((addAttendanceArray[1]));*/
			projectAttendanceService.updateAddAttendance(addAttendanceArray[0],addAttendanceArray[1]);
		}
		//设置状态，已考勤、未考勤
 		if (idsArr.length > 0) {
			ArrayList list = (ArrayList) attendanceRecordService.findAttendanceList(projectAttendanceID);
			String status=null;	
			if(list.size() == projectAttendance.getDateSum()){
					status = "2";
				
				}else{
					status = "1";
				}
	   		projectAttendanceService.updateStatus(projectAttendanceID,status);
		}
 		//更新当月考勤天数
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		AttendanceListDetail attendanceListDetail = new AttendanceListDetail();
		attendanceListDetail.setEmployeeId(employeeID);
		attendanceListDetail.setId(IDhidden);
		attendanceListDetail.setAttendanceMonth(date.parse(getattendancedate1));
		attendanceListDetail.setProjectId(projectId2);

		attendanceListDetailService.attendanceupdate(attendanceListDetail);
		String allprojectId = request.getParameter("projectId");

		addMessage(redirectAttributes, "员工考勤成功");
		return "redirect:" + Global.getAdminPath()+ "/att/attendanceListDetail/list?projectId=" + allprojectId;
	}
}