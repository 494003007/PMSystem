/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.per.entity.CertificatesType;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.service.CertificatesTypeService;
import com.thinkgem.jeesite.modules.per.service.EmployeeService;
import org.apache.commons.fileupload.util.Streams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.per.entity.Credentials;
import com.thinkgem.jeesite.modules.per.service.CredentialsService;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2016-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/per/credentials")
public class CredentialsController extends BaseController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CertificatesTypeService certificatesTypeService;

	@Autowired
	private CredentialsService credentialsService;
	
	@ModelAttribute
	public Credentials get(@RequestParam(required=false) String id) {
		Credentials entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = credentialsService.get(id);
		}
		if (entity == null){
			entity = new Credentials();
		}
		return entity;
	}
	
	@RequiresPermissions("per:allowanceConstant:view")
	@RequestMapping(value = {"list", ""})
	public String list(Credentials credentials, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Credentials> page = credentialsService.findPage(new Page<Credentials>(request, response), credentials);
		model.addAttribute("employeeList", employeeService.findList(new Employee()));
		model.addAttribute("typeList", certificatesTypeService.findList(new CertificatesType()));
		model.addAttribute("page", page);
		return "modules/per/credentialsList";
	}

	@RequiresPermissions("per:allowanceConstant:view")
	@RequestMapping(value = "form")
	public String form(Credentials credentials, Model model ,HttpServletRequest request) {
//		if(credentials.getIsNewRecord()){
//			if(request.getParameter("employeeId") == null)
//				model.addAttribute("employeeList", employeeService.findList(new Employee()));
//			else{
//				ArrayList<Employee> el = new ArrayList<Employee>();
//				el.add(employeeService.get(request.getParameter("employeeId")));
//				model.addAttribute("employeeList", el);
//			}
//
//		}
		model.addAttribute("typeList", certificatesTypeService.findList(new CertificatesType()));
		model.addAttribute("credentials", credentials);
		return "modules/per/credentialsForm";
	}

	@RequiresPermissions("per:allowanceConstant:edit")
	@RequestMapping(value = "save")
	public String save(Credentials credentials, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request) {
		if (!beanValidator(model, credentials)){
			return form(credentials, model, request);
		}
		credentialsService.save(credentials);
		addMessage(redirectAttributes, "保存证件信息成功");
		return "redirect:"+Global.getAdminPath()+"/per/credentials/?repage";
	}
	
	@RequiresPermissions("per:allowanceConstant:edit")
	@RequestMapping(value = "delete")
	public String delete(Credentials credentials, RedirectAttributes redirectAttributes) {
		credentialsService.delete(credentials);
		addMessage(redirectAttributes, "删除证件信息成功");
		return "redirect:"+Global.getAdminPath()+"/per/credentials/?repage";
	}

	@RequiresPermissions("per:allowanceConstant:edit")
	@RequestMapping(value = "uploadC")
	public String uploadC(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") CommonsMultipartFile file,@RequestParam("employee_id") String id,@RequestParam("cr_type") String type) throws IOException {
		System.out.println("\n\n\n\n\n\n\n\n---------\n\n\n\n\n\n\n");
		System.out.println("fileName："+file.getOriginalFilename());
		File flo = new File(request.getSession().getServletContext().getRealPath("/") + "pdf" );
		if(!flo.exists()){
			flo.mkdir();
		}
		BufferedInputStream in = new BufferedInputStream(file.getInputStream());

		File fl = new File(request.getSession().getServletContext().getRealPath("/") + "pdf" + File.separator + id + type + ".pdf");
		System.out.println(fl.getName());
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fl));
		Streams.copy(in, out, true);
		return "";
	}
	@RequestMapping(value = "downloadAll")
	public String downloadAll(HttpServletRequest request) throws IOException {
		try {
			String fi = new Date().getTime() + ".zip";
			zip(request.getSession().getServletContext().getRealPath("/") + fi,
					new File(request.getSession().getServletContext().getRealPath("/") + "pdf"));
			return "redirect:/" + fi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "downloadChoice",method = RequestMethod.POST )
	public String downloadChoice(HttpServletRequest request,@RequestParam("credentialsList")String credList) throws IOException {
		try {
			String fi = new Date().getTime() + ".zip";
			String[] par = credList.split(",");
			File[] a = new File[par.length];
			ArrayList<File> fl = new ArrayList<File>();

			for(String flname : par){
				fl.add(new File(request.getSession().getServletContext().getRealPath("/") + "pdf"+File.separator+flname));
			}
			a = fl.toArray(a);
			zipFiles(request.getSession().getServletContext().getRealPath("/") + fi,a);
			return "redirect:/" + fi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		zip(out, inputFile, inputFile.getName(), bo);
		bo.close();
		out.close(); // 输出流关闭

	}

	private void zipFiles(String zipFileName, File[] inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		for(File f : inputFile){
			zip(out, f, f.getName(), bo);
		}

		bo.close();
		out.close(); // 输出流关闭

	}

	private void zip(ZipOutputStream out, File f, String base,
					 BufferedOutputStream bo) throws Exception { // 方法重载
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base

			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bi.close();
			in.close(); // 输入流关闭
		}
	}

}