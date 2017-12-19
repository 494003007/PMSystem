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
import com.thinkgem.jeesite.modules.excel.entity.OuterSalaryReport;
import com.thinkgem.jeesite.modules.excel.entity.SalaryReport;
import com.thinkgem.jeesite.modules.excel.service.SalaryReportService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 工资报表Controller
 * @author cdoublej
 * @version 2016-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/excel/salaryReport")
public class SalaryReportController extends BaseController {

	@Autowired
	private SalaryReportService salaryReportService;
	DecimalFormat df = new DecimalFormat("#.0");  
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
	

	@RequiresPermissions("excel:salaryReport:view")
	@RequestMapping(value = "form")
	public String form(SalaryReport salaryReport, Model model) {
		model.addAttribute("salaryReport", salaryReport);
		return "modules/excel/salaryReportForm";
	}

	@RequiresPermissions("excel:salaryReport:edit")
	@RequestMapping(value = "save")
	public String save(SalaryReport salaryReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, salaryReport)){
			return form(salaryReport, model);
		}
		salaryReportService.save(salaryReport);
		addMessage(redirectAttributes, "保存工资报表成功");
		return "redirect:"+Global.getAdminPath()+"/excel/salaryReport/?repage";
	}
	
	@RequiresPermissions("excel:salaryReport:edit")
	@RequestMapping(value = "delete")
	public String delete(SalaryReport salaryReport, RedirectAttributes redirectAttributes) {
		salaryReportService.delete(salaryReport);
		addMessage(redirectAttributes, "删除工资报表成功");
		return "redirect:"+Global.getAdminPath()+"/excel/salaryReport/?repage";
	}

	@RequiresPermissions("excel:salaryReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(SalaryReport salaryReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SalaryReport> page = salaryReportService.findPage(new Page<SalaryReport>(request, response), salaryReport); 
		
		model.addAttribute("page", page);
		return "modules/excel/salaryReportList";
	}
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("excel:salaryReport:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SalaryReport salaryReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "外包报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<SalaryReport> salaryReportArray = salaryReportService.findList(salaryReport);
    	 
            addMessage(redirectAttributes, "导出成功");
            new ExportExcel("外包报表", OuterSalaryReport.class).setDataList(salaryReportArray).write(response, fileName).dispose();
            
            return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		} 
		return "redirect:"+adminPath+"/excel/salaryReport/list";
    }
	@RequiresPermissions("excel:salaryReport:view")
    @RequestMapping(value = "export2", method=RequestMethod.POST)
    public String exportFile2(SalaryReport salaryReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工资报表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<SalaryReport> salaryReportArray = salaryReportService.salaryExport(salaryReport);
    	 
            addMessage(redirectAttributes, "导出成功");
            new ExportExcel("工资报表", SalaryReport.class).setDataList(salaryReportArray).write(response, fileName).dispose();
          
        	return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		} 
		return "redirect:"+adminPath+"/excel/salaryReport/list";
    }
	
}