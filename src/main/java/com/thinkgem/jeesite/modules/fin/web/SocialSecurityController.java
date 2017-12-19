/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.fin.entity.AccumulationFund;
import com.thinkgem.jeesite.modules.fin.utils.SocialSecurityUtil;
import com.thinkgem.jeesite.modules.fin.utils.SocialSecurityUtil;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;
import com.thinkgem.jeesite.modules.per.entity.PositionType;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import com.thinkgem.jeesite.modules.per.service.EmployeeTypeService;
import com.thinkgem.jeesite.modules.per.service.PositionTypeService;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DepartmentService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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
import com.thinkgem.jeesite.modules.fin.entity.SocialSecurity;
import com.thinkgem.jeesite.modules.fin.service.SocialSecurityService;

import java.util.ArrayList;
import java.util.List;

/**
 * social_securityController
 *
 * @author czy
 * @version 2016-10-13
 */
@Controller
@RequestMapping(value = "${adminPath}/fin/socialSecurity")
public class SocialSecurityController extends BaseController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SocialSecurityService socialSecurityService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeTypeService employeeTypeService;
    @Autowired
    private PositionTypeService positionTypeService;

    @ModelAttribute
    public SocialSecurity get(@RequestParam(required = false) String id) {
        SocialSecurity entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = socialSecurityService.get(id);
        }
        if (entity == null) {
            entity = new SocialSecurity();
        }
        return entity;
    }

    @RequiresPermissions("fin:socialSecurity:view")
    @RequestMapping(value = {"list", ""})
    public String list(SocialSecurity socialSecurity, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (socialSecurity.getEmployeeId()!=null)
            socialSecurity.setEmployee(employeeService.get(""+socialSecurity.getEmployeeId()));
        Page<SocialSecurity> page = socialSecurityService.findPage(new Page<SocialSecurity>(request, response), socialSecurity);
        model.addAttribute("employeeList",employeeService.findList(new Employee()));
        model.addAttribute("departList",departmentService.findList(new Department()));
        model.addAttribute("employeeTypeList",employeeTypeService.findList(new EmployeeType()));
        model.addAttribute("positionTypeList",positionTypeService.findList(new PositionType()));
        model.addAttribute("page", page);
        return "modules/fin/socialSecurityList";
    }

    @RequiresPermissions("fin:socialSecurity:view")
    @RequestMapping(value = "form")
    public String form(SocialSecurity socialSecurity, Model model) {
        model.addAttribute("employeeList",employeeService.findList(new Employee()));
        model.addAttribute("socialSecurity", socialSecurity);
        return "modules/fin/socialSecurityForm";
    }

    @RequiresPermissions("fin:socialSecurity:edit")
    @RequestMapping(value = "save")
    public String save(SocialSecurity socialSecurity, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, socialSecurity)) {
            return form(socialSecurity, model);
        }
        socialSecurityService.save(socialSecurity);
        addMessage(redirectAttributes, "保存每月社保明细成功");
        return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
    }

    @RequiresPermissions("fin:socialSecurity:edit")
    @RequestMapping(value = "delete")
    public String delete(SocialSecurity socialSecurity, RedirectAttributes redirectAttributes) {
        socialSecurityService.delete(socialSecurity);
        addMessage(redirectAttributes, "删除每月社保明细成功");
        return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
    }

    @ResponseBody
    @RequiresPermissions("fin:socialSecurity:edit")
    @RequestMapping(value = "checkRule")
    public boolean checkRule(SocialSecurity socialSecurity) throws Exception {
        if (employeeService.get(String.valueOf(socialSecurity.getEmployeeId())) == null) {
            throw new Exception("该employeeId不存在");
        } else return true;
    }


    @RequiresPermissions("fin:socialSecurity:edit")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<SocialSecurity> list = ei.getDataList(SocialSecurity.class);
            for (SocialSecurity socialSecurity : list) {
                System.out.println(socialSecurity.getEmployeeId());
                try {
                    if (checkRule(socialSecurity)) {
                        socialSecurityService.save(socialSecurity);
                        successNum++;
                    } else {
                        failureMsg.append("<br/>用户id " + socialSecurity.getEmployeeId() + " 不存在; ");
                        failureNum++;
                    }
                } catch (ConstraintViolationException ex) {
                    failureMsg.append("用户id " + socialSecurity.getEmployeeId() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    failureMsg.append("用户id " + socialSecurity.getEmployeeId() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
    }

    @RequiresPermissions("fin:socialSecurity:edit")
    @RequestMapping(value = "importTemplate", method = RequestMethod.GET)
    public String exportTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "社保数据导入模板.xlsx";
            List<SocialSecurity> list = Lists.newArrayList();
            list.add(socialSecurityService.get("1"));
            new ExportExcel("用户数据", SocialSecurity.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
    }
    @RequiresPermissions("fin:socialSecurity:view")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportFile(SocialSecurity socialSecurity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "社保导出数据.xlsx";
            List<SocialSecurity> list;
            list = socialSecurityService.findList(socialSecurity);
            new ExportExcel("用户数据", AccumulationFund.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/fin/socialSecurity/?repage";
    }
}