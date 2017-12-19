/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.excel.entity.AttendanceReport;
import com.thinkgem.jeesite.modules.excel.service.AttendanceReportService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 工资报表Controller
 * @author cdoublej
 * @version 2016-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/attendanceReport")
public class AttendanceReportController extends BaseController {

	@Autowired
	private AttendanceReportService attendanceReportService;
	DecimalFormat df = new DecimalFormat("#.0");  
	@ModelAttribute
	public AttendanceReport get(@RequestParam(required=false) String id) {
		AttendanceReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceReportService.get(id);
		}
		if (entity == null){
			entity = new AttendanceReport();
		}
		return entity;
	}

	@RequiresPermissions("excel:attendanceReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttendanceReport attendanceReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttendanceReport> page = attendanceReportService.findPage(new Page<AttendanceReport>(request, response), attendanceReport); 
		model.addAttribute("page", page);
		return "modules/excel/attendanceReportList";
	}
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("excel:attendanceReport:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(AttendanceReport attendanceReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考勤报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<AttendanceReport> page = attendanceReportService.findList(attendanceReport);
 
            new ExportExcel("考勤报表", AttendanceReport.class).setDataList(page).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出考勤报表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/excel/attendanceReport/?repage";
    }

	
}