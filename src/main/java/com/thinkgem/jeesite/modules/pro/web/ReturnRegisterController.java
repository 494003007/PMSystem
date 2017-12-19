/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.pro.entity.ReturnRegister;
import com.thinkgem.jeesite.modules.pro.service.ReturnRegisterService;
import com.thinkgem.jeesite.modules.sys.entity.Customer;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CustomerService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 回款登记Controller
 * @author cdoublej
 * @version 2016-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/returnRegister")
public class ReturnRegisterController extends BaseController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ReturnRegisterService returnRegisterService;
	
	@ModelAttribute
	public ReturnRegister get(@RequestParam(required=false) String id) {
		ReturnRegister entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = returnRegisterService.get(id);
		}
		if (entity == null){
			entity = new ReturnRegister();
		}
		return entity;
	}
	
	@RequiresPermissions("pro:returnRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReturnRegister returnRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReturnRegister> page = returnRegisterService.findPage(new Page<ReturnRegister>(request, response), returnRegister); 
		model.addAttribute("page", page);
		return "modules/pro/returnRegisterList";
	}

	@RequiresPermissions("pro:returnRegister:view")
	@RequestMapping(value = "form")
	public String form(ReturnRegister returnRegister, Model model) {
		model.addAttribute("returnRegister", returnRegister);
		return "modules/pro/returnRegisterForm";
	}

	
	@RequiresPermissions("pro:returnRegister:view")
	@RequestMapping(value = "info")
	public String info(ReturnRegister returnRegister, Model model) {
		
		List<Map<String,String>> returnRegisterList = returnRegisterService.findAllInvoice(returnRegister);
		model.addAttribute("returnRegister", returnRegister);
		model.addAttribute("returnRegisterList", returnRegisterList);	
		return "modules/pro/returnRegisterInfo";
	}
	@RequiresPermissions("pro:returnRegister:edit")
	@RequestMapping(value = "save")
	public String save(ReturnRegister returnRegister, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, returnRegister)){
			return form(returnRegister, model);
		}
		returnRegisterService.save(returnRegister);
		addMessage(redirectAttributes, "保存回款登记成功");
		return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
	}
	
	@RequiresPermissions("pro:returnRegister:edit")
	@RequestMapping(value = "delete")
	public String delete(ReturnRegister returnRegister, RedirectAttributes redirectAttributes) {
		try{
			returnRegisterService.delete(returnRegister);
		}catch(Exception e){
			addMessage(redirectAttributes, "回款已被关联，不能删除改回款记录");
			return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
		}
		addMessage(redirectAttributes, "删除回款登记成功");
		return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pro:returnRegister:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ReturnRegister> list = ei.getDataList(ReturnRegister.class);
			for (ReturnRegister returnRegister : list){
				try{
					int returnRegisterCount = returnRegisterService.isExit(returnRegister);
					if (returnRegisterCount == 0){//不存在记录。不重复导入
						List<Customer> customerlist =  customerService.isTrueCompany(returnRegister.getReturnCompany());
						if(customerlist.size() == 0){//回款单位不一致
							returnRegister.setStatus("3"); //1已使用 。2可使用  3 不可使用
							returnRegister.setIsTrueCompany("1"); // 1否 2是
						}else{
							returnRegister.setStatus("2");
							returnRegister.setIsTrueCompany("2");
						}
						returnRegister.setSurplusAmount(returnRegister.getAmount());
						returnRegisterService.save(returnRegister);
						successNum++;
					}else{
						failureMsg.append("<br/>回款记录（回款单位： "+returnRegister.getReturnCompany()+"；回款时间："+s.format(returnRegister.getReturnDate())+"；回款金额："+returnRegister.getAmount()+" )已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/><br/>回款记录（回款单位： "+returnRegister.getReturnCompany()+"；回款时间："+s.format(returnRegister.getReturnDate())+"；回款金额："+returnRegister.getAmount()+" )导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>回款记录（回款单位： "+returnRegister.getReturnCompany()+"；回款时间："+returnRegister.getReturnDate()+"；回款金额："+returnRegister.getAmount()+" )导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条回款记录，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条回款记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入回款记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
    }
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pro:returnRegister:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ReturnRegister returnRegister, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "回款记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ReturnRegister> page = returnRegisterService.findPage(new Page<ReturnRegister>(request, response, -1), returnRegister);
    		new ExportExcel("回款记录", ReturnRegister.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出回款记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
    }
	
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pro:returnRegister:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "回款台账导入模板.xlsx";
    		List<ReturnRegister> list = Lists.newArrayList();
    		list.add(returnRegisterService.findList(null).get(0));
    		new ExportExcel("回款台账", ReturnRegister.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/returnRegister/?repage";
    }
	
	
	@RequiresPermissions("pro:returnRegister:view")
	@RequestMapping("returnRegisterSelect")
	public String returnRegisterSelect(ReturnRegister returnRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		returnRegister.setStatus("2");//只显示可用的回款单
		Page<ReturnRegister> page = returnRegisterService.findPage(new Page<ReturnRegister>(request, response), returnRegister); 
		String selecttype = request.getParameter("selecttype");
		model.addAttribute("selecttype", selecttype);
		model.addAttribute("page", page);
		
		return "modules/pro/returnRegisterSelect";
	}
	
}