/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
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
import com.thinkgem.jeesite.modules.fin.entity.PositionSubsidyDetail;
import com.thinkgem.jeesite.modules.fin.service.PositionSubsidyDetailService;

/**
 * position_subsidyController
 * @author czy
 * @version 2016-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/positionSubsidyDetail")
public class PositionSubsidyDetailController extends BaseController {

	@Autowired
	private PositionSubsidyDetailService positionSubsidyDetailService;
	@Autowired
	private PositionTypeService positionTypeService;

	@ModelAttribute
	public PositionSubsidyDetail get(@RequestParam(required=false) String id) {
		PositionSubsidyDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = positionSubsidyDetailService.get(id);
		}
		if (entity == null){
			entity = new PositionSubsidyDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("fin:positionSubsidyDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(PositionSubsidyDetail positionSubsidyDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (positionSubsidyDetail.getPositionTypeId()!=null)
			positionSubsidyDetail.setPositionType(positionTypeService.get(""+positionSubsidyDetail.getPositionTypeId()));
		Page<PositionSubsidyDetail> page = positionSubsidyDetailService.findPage(new Page<PositionSubsidyDetail>(request, response), positionSubsidyDetail);
		model.addAttribute("positionTypeList", positionTypeService.findList(new PositionType()));
		model.addAttribute("page", page);
		return "modules/fin/positionSubsidyDetailList";
	}

	@RequiresPermissions("fin:positionSubsidyDetail:view")
	@RequestMapping(value = "form")
	public String form(PositionSubsidyDetail positionSubsidyDetail, Model model) {
		model.addAttribute("positionSubsidyDetail", positionSubsidyDetail);
		model.addAttribute("positionTypeList", positionTypeService.findList(new PositionType()));
		return "modules/fin/positionSubsidyDetailForm";
	}

	@RequiresPermissions("fin:positionSubsidyDetail:edit")
	@RequestMapping(value = "save")
	public String save(PositionSubsidyDetail positionSubsidyDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, positionSubsidyDetail)){
			return form(positionSubsidyDetail, model);
		}
		positionSubsidyDetailService.save(positionSubsidyDetail);
		addMessage(redirectAttributes, "保存position_subsidy成功");
		return "redirect:"+Global.getAdminPath()+"/fin/positionSubsidyDetail/?repage";
	}
	
	@RequiresPermissions("fin:positionSubsidyDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(PositionSubsidyDetail positionSubsidyDetail, RedirectAttributes redirectAttributes) {
		positionSubsidyDetailService.delete(positionSubsidyDetail);
		addMessage(redirectAttributes, "删除position_subsidy成功");
		return "redirect:"+Global.getAdminPath()+"/fin/positionSubsidyDetail/?repage";
	}

}