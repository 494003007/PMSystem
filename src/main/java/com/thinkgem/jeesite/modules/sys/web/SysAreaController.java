/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import com.thinkgem.jeesite.modules.sys.service.SysAreaService;


/**
 * 区域结构生成Controller
 * @author cdoublej
 * @version 2016-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysArea")
public class SysAreaController extends BaseController {

	@Autowired
	private SysAreaService sysAreaService;
	
	@ModelAttribute
	public SysArea get(@RequestParam(required=false) String id) {
		SysArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysAreaService.get(id);
		}
		if (entity == null){
			entity = new SysArea();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysArea:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysArea sysArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SysArea> list = sysAreaService.findList(sysArea); 
		model.addAttribute("list", list);
		return "modules/sys/sysAreaList";
	}

	@RequiresPermissions("sys:sysArea:view")
	@RequestMapping(value = "form")
	public String form(SysArea sysArea, Model model) {
		if (sysArea.getParent()!=null && StringUtils.isNotBlank(sysArea.getParent().getId())){
			sysArea.setParent(sysAreaService.get(sysArea.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(sysArea.getId())){
				SysArea sysAreaChild = new SysArea();
				sysAreaChild.setParent(new SysArea(sysArea.getParent().getId()));
				List<SysArea> list = sysAreaService.findList(sysArea); 
				if (list.size() > 0){
					sysArea.setSort(list.get(list.size()-1).getSort());
					if (sysArea.getSort() != null){
						sysArea.setSort(sysArea.getSort() + 30);
					}
				}
			}
		}
		if (sysArea.getSort() == null){
			sysArea.setSort(30);
		}
		model.addAttribute("sysArea", sysArea);
		return "modules/sys/sysAreaForm";
	}

	@RequiresPermissions("sys:sysArea:edit")
	@RequestMapping(value = "save")
	public String save(SysArea sysArea, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysArea)){
			return form(sysArea, model);
		}
		sysAreaService.save(sysArea);
		addMessage(redirectAttributes, "保存区域成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysArea/?repage";
	}
	
	@RequiresPermissions("sys:sysArea:edit")
	@RequestMapping(value = "delete")
	public String delete(SysArea sysArea, RedirectAttributes redirectAttributes) {
		try{
			sysAreaService.delete(sysArea);
		}catch(Exception e){
			addMessage(redirectAttributes, "该区域已被关联，不能删除该区域");
			return "redirect:"+Global.getAdminPath()+"/sys/sysArea/?repage";
		}
		addMessage(redirectAttributes, "删除区域成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysArea/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysArea> list = sysAreaService.findList(new SysArea());
		for (int i=0; i<list.size(); i++){
			SysArea e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}