/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.EmployeeTypeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.service.DepartmentService;
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
import com.thinkgem.jeesite.modules.fin.entity.TemporaryAdjustDetail;
import com.thinkgem.jeesite.modules.fin.service.TemporaryAdjustDetailService;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2016-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/temporaryAdjustDetail")
public class TemporaryAdjustDetailController extends BaseController {

	@Autowired
	private TemporaryAdjustDetailService temporaryAdjustDetailService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EmployeeTypeService employeeTypeService;
	@Autowired
	private PositionTypeService positionTypeService;
	
	@ModelAttribute
	public TemporaryAdjustDetail get(@RequestParam(required=false) String id) {
		TemporaryAdjustDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = temporaryAdjustDetailService.get(id);
		}
		if (entity == null){
			entity = new TemporaryAdjustDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fin:temporaryAdjustDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(TemporaryAdjustDetail temporaryAdjustDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TemporaryAdjustDetail> page = temporaryAdjustDetailService.findPage(new Page<TemporaryAdjustDetail>(request, response), temporaryAdjustDetail); 
		model.addAttribute("page", page);
		model.addAttribute("departList",departmentService.findList(new Department()));
		model.addAttribute("employeeTypeList",employeeTypeService.findList(new EmployeeType()));
		model.addAttribute("positionTypeList",positionTypeService.findList(new PositionType()));
		return "modules/fin/temporaryAdjustDetailList";
	}

	@RequiresPermissions("fin:temporaryAdjustDetail:view")
	@RequestMapping(value = "form")
	public String form(TemporaryAdjustDetail temporaryAdjustDetail, Model model) {
		model.addAttribute("temporaryAdjustDetail", temporaryAdjustDetail);
		return "modules/fin/temporaryAdjustDetailForm";
	}

	@RequiresPermissions("fin:temporaryAdjustDetail:edit")
	@RequestMapping(value = "save")
	public String save(TemporaryAdjustDetail temporaryAdjustDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, temporaryAdjustDetail)){
			return form(temporaryAdjustDetail, model);
		}
		temporaryAdjustDetailService.save(temporaryAdjustDetail);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/fin/temporaryAdjustDetail/?repage";
	}
	
	@RequiresPermissions("fin:temporaryAdjustDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(TemporaryAdjustDetail temporaryAdjustDetail, RedirectAttributes redirectAttributes) {
		temporaryAdjustDetailService.delete(temporaryAdjustDetail);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/fin/temporaryAdjustDetail/?repage";
	}

}