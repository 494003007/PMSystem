/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import com.thinkgem.jeesite.modules.fin.entity.SocialSecurity;
import com.thinkgem.jeesite.modules.fin.utils.AccumulationFundUtil;
import com.thinkgem.jeesite.modules.fin.utils.AccumulationFundUtil;
import com.thinkgem.jeesite.modules.fin.utils.AccumulationFundUtil;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.EmployeeTypeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import com.thinkgem.jeesite.modules.fin.service.AccumulationFundService;

import java.util.Date;
import java.util.List;

/**
 * accumulation_fundController
 * @author czy
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/accumulationFund")
public class AccumulationFundController extends BaseController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private AccumulationFundService accumulationFundService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EmployeeTypeService employeeTypeService;
	@Autowired
	private PositionTypeService positionTypeService;


	@ModelAttribute
	public AccumulationFund get(@RequestParam(required = false) String id) {
		AccumulationFund entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = accumulationFundService.get(id);
		}
		if (entity == null) {
			entity = new AccumulationFund();
		}
		return entity;
	}

	@RequiresPermissions("fin:accumulationFund:view")
	@RequestMapping(value = {"list", ""})
	public String list(AccumulationFund accumulationFund, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if (accumulationFund.getEmployeeId()!=null)
//			accumulationFund.setEmployee(employeeService.get(""+accumulationFund.getEmployeeId()));
//		if (accumulationFund.getEmployeeId() == null && accumulationFund.getBeginPayTime()==null && accumulationFund.getEndPayTime() == null){
//			Date date = new Date();
//			accumulationFund.setBeginPayTime(new Date(date.getYear(),date.getMonth(),1));
//			if (date.getMonth()!=12)
//				accumulationFund.setEndPayTime(new Date(date.getYear(),date.getMonth()+1,1));
//			else if (date.getMonth()==12)
//				accumulationFund.setEndPayTime(new Date(date.getYear()+1,1,1));
//		}
		Page<AccumulationFund> pg =  new Page<AccumulationFund>(request, response);
		pg.setOrderBy("pay_date desc");
		Page<AccumulationFund> page = accumulationFundService.findPage(pg, accumulationFund);
		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("departList",departmentService.findList(new Department()));
		model.addAttribute("employeeTypeList",employeeTypeService.findList(new EmployeeType()));
		model.addAttribute("positionTypeList",positionTypeService.findList(new PositionType()));
		model.addAttribute("page", page);
		return "modules/fin/accumulationFundList";
	}

	@RequiresPermissions("fin:accumulationFund:view")
	@RequestMapping(value = "form")
	public String form(AccumulationFund accumulationFund, Model model) {
		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("accumulationFund", accumulationFund);
		return "modules/fin/accumulationFundForm";
	}

	@RequiresPermissions("fin:accumulationFund:edit")
	@RequestMapping(value = "save")
	public String save(AccumulationFund accumulationFund, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accumulationFund)) {
			return form(accumulationFund, model);
		}
		Date date = new Date();
		Date begin = new Date(date.getYear(),date.getMonth(),1);
		Date end = null;
		if (date.getMonth()==12){
			end = new Date(date.getYear(),1,1);
		}
		else if (date.getMonth()!=12){
			end = new Date(date.getYear(),date.getMonth()+1,1);
		}
		AccumulationFund accumulationFund1 = new AccumulationFund();
		accumulationFund1.setEmployeeId(accumulationFund.getEmployeeId());
		accumulationFund1.setBeginPayTime(begin);
		accumulationFund1.setEndPayTime(end);
		List<AccumulationFund> list = accumulationFundService.findList(accumulationFund1);
			if (list.size()==0){
			accumulationFundService.save(accumulationFund);
			addMessage(redirectAttributes, "保存每月公积金明细成功");
		}else {
			addMessage(redirectAttributes, "该用户此月记录已存在");
		}
		return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
	}

	@RequiresPermissions("fin:accumulationFund:edit")
	@RequestMapping(value = "delete")
	public String delete(AccumulationFund accumulationFund, RedirectAttributes redirectAttributes) {
		accumulationFundService.delete(accumulationFund);
		addMessage(redirectAttributes, "删除每月公积金明细成功");
		return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
	}

	@ResponseBody
	@RequiresPermissions("fin:accumulationFund:edit")
	@RequestMapping(value = "checkRule")
	public boolean checkRule(AccumulationFund accumulationFund) throws Exception {
		if (employeeService.get(String.valueOf(accumulationFund.getEmployeeId())) == null) {
			throw new Exception("该employeeId不存在");
		} else return true;
	}

	@RequiresPermissions("fin:accumulationFund:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AccumulationFund> list = ei.getDataList(AccumulationFund.class);
			for (AccumulationFund accumulationFund : list) {
				System.out.println(accumulationFund.getEmployeeId());
				try {
					if (checkRule(accumulationFund)) {
						accumulationFundService.save(accumulationFund);
						successNum++;
					} else {
						failureMsg.append("<br/>用户id " + accumulationFund.getEmployeeId() + " 不存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("用户id " + accumulationFund.getEmployeeId() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("用户id " + accumulationFund.getEmployeeId() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
	}

	@RequiresPermissions("fin:accumulationFund:edit")
	@RequestMapping(value = "importTemplate", method = RequestMethod.GET)
	public String exportTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "公积金数据导入模板.xlsx";
			List<AccumulationFund> list = Lists.newArrayList();
			list.add(accumulationFundService.get("2"));
			new ExportExcel("用户数据", AccumulationFund.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
	}

	@RequiresPermissions("fin:accumulationFund:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(AccumulationFund accumulationFund, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "公积金导出数据.xlsx";
			List<AccumulationFund> list;
			list = accumulationFundService.findList(accumulationFund);
			new ExportExcel("用户数据", AccumulationFund.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/fin/accumulationFund/?repage";
	}
}