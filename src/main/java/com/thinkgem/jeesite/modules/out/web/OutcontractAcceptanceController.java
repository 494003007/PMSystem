/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.out.entity.Outcontract;
import com.thinkgem.jeesite.modules.out.entity.OutcontractAcceptance;
import com.thinkgem.jeesite.modules.out.service.OutcontractAcceptanceService;
import com.thinkgem.jeesite.modules.out.service.OutcontractService;
import org.apache.tools.zip.ZipEntry;   
import org.apache.tools.zip.ZipOutputStream;  
/**
 * 外包验收Controller
 * @author LKY
 * @version 2016-11-10
 */
@Controller
@RequestMapping(value = "${adminPath}/out/outcontractAcceptance")
public class OutcontractAcceptanceController extends BaseController {

	@Autowired
	private OutcontractAcceptanceService outcontractAcceptanceService;
	@Autowired
	private OutcontractService outcontractService;
	
	@ModelAttribute
	public OutcontractAcceptance get(@RequestParam(required=false) String id) {
		OutcontractAcceptance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = outcontractAcceptanceService.get(id);
		}
		if (entity == null){
			entity = new OutcontractAcceptance();
		}
		return entity;
	}
	
	@RequiresPermissions("out:outcontractAcceptance:view")
	@RequestMapping(value = {"list", ""})
	public String list(OutcontractAcceptance outcontractAcceptance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OutcontractAcceptance> page = outcontractAcceptanceService.findPage(new Page<OutcontractAcceptance>(request, response), outcontractAcceptance); 
		for(int i=0;i<page.getList().size();i++){
		page.getList().get(i).setOutcontractName(outcontractService.get(""+page.getList().get(i).getOutcontractId()).getName());
			
			
		}
		Outcontract outcontract =new Outcontract();
	model.addAttribute("outcontractList", 	outcontractService.findList(outcontract));
		
		
		
		model.addAttribute("page", page);
		return "modules/out/outcontractAcceptanceList";
	}

	@RequiresPermissions("out:outcontractAcceptance:view")
	@RequestMapping(value = "form")
	public String form(OutcontractAcceptance outcontractAcceptance, Model model) {
	
		
		Outcontract outcontract =new Outcontract();
	model.addAttribute("outcontractList", 	outcontractService.findList(outcontract));
		model.addAttribute("outcontractAcceptance", outcontractAcceptance);
		return "modules/out/outcontractAcceptanceForm";
	}
	

	@RequestMapping(value = "form1")
	public String form1(OutcontractAcceptance outcontractAcceptance, Model model) {

  outcontractAcceptance.setOutcontractName(outcontractService.get(""+outcontractAcceptance.getOutcontractId()).getName());
		model.addAttribute("outcontractAcceptance", outcontractAcceptance);
		return "modules/out/outcontractAcceptanceForm1";
	}
	
	
	
	@RequestMapping(value = "form2")
	public String form2(OutcontractAcceptance outcontractAcceptance,HttpServletRequest req, Model model) {
     System.out.println("进入了form2");
	//合同的编号	
     String id1=req.getParameter("Id1");
     String name1=outcontractService.get(id1).getName();
     model.addAttribute("outcontractid1", id1);
     model.addAttribute("outcontractname1", name1);
		
		System.out.println(outcontractAcceptance);
		
		Outcontract outcontract =new Outcontract();
	model.addAttribute("outcontractList", 	outcontractService.findList(outcontract));
		model.addAttribute("outcontractAcceptance", outcontractAcceptance);
		return "modules/out/outcontractAcceptanceForm2";
	}
//	条件查找
	@RequestMapping(value = "findterm")
	public String findterm(OutcontractAcceptance outcontractAcceptance,HttpServletRequest req,HttpServletResponse response, Model model) {
     System.out.println("进入了findterm");
	//合同的编号	
     String id1=req.getParameter("Id1");
  System.out.println("外包合同编号为"+id1);
  
  outcontractAcceptance.setOutcontractId(Integer.parseInt(id1));
  

		
	

		Page<OutcontractAcceptance> page = outcontractAcceptanceService.findPage(new Page<OutcontractAcceptance>(req, response), outcontractAcceptance); 
		for(int i=0;i<page.getList().size();i++){
		page.getList().get(i).setOutcontractName(outcontractService.get(""+page.getList().get(i).getOutcontractId()).getName());
		}

		Outcontract newoutcontract =new Outcontract();
		model.addAttribute("outcontractList", 	outcontractService.findList(newoutcontract));



		model.addAttribute("page", page);
		return "modules/out/outcontractAcceptanceOneList";
		
	}
	
	
	
	
//保存/修改
	@RequiresPermissions("out:outcontractAcceptance:edit")
	@RequestMapping(value = "save")
	public String save(@RequestParam(value="file",required=false) CommonsMultipartFile file,HttpServletRequest request, HttpServletResponse response,OutcontractAcceptance outcontractAcceptance, Model model, RedirectAttributes redirectAttributes) {
	String id=request.getParameter("outcontract");
	System.out.println("合同编号为"+id);
	String message="";

		
		String suffix="";
		String fileName = file.getOriginalFilename();  
        System.out.println("原始文件名:" + fileName); 
        // 新文件名  
        String newFileName =outcontractService.get(""+outcontractAcceptance.getOutcontractId()).getName()+"-"+ fileName;  
  System.out.println("新文件名："+newFileName);
        // 上传位置  
  String path = "C:/yanshoudan/";
  String FinalFileName=path+newFileName;
  File newFile=new File(FinalFileName);
  try{
  suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
  }catch(Exception w){
	  message= "保存证件信息不成功，请上传pdf文件";
	 
  }
if(".pdf".equals(suffix)){ 
	//通过CommonsMultipartFile的方法直接写文件（注意这个时候）
	  try {
			file.transferTo(newFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  outcontractAcceptance.setAcceptanceUrl(FinalFileName);
	

			outcontractAcceptanceService.save(outcontractAcceptance);
		message= "保存验收单信息成功";
}else{
message= "保存证件信息不成功，请上传pdf文件";
}
OutcontractAcceptance outcontractAcceptance1=new OutcontractAcceptance();
outcontractAcceptance1.setOutcontractId(Integer.parseInt(id));

Page<OutcontractAcceptance> page = outcontractAcceptanceService.findPage(new Page<OutcontractAcceptance>(request, response), outcontractAcceptance1); 
for(int i=0;i<page.getList().size();i++){
page.getList().get(i).setOutcontractName(outcontractService.get(""+page.getList().get(i).getOutcontractId()).getName());
}

Outcontract newoutcontract =new Outcontract();
model.addAttribute("outcontractList", 	outcontractService.findList(newoutcontract));


model.addAttribute("outcontract",outcontractService.get(id));
model.addAttribute("page", page);
model.addAttribute("message", message);
return "modules/out/outcontractAcceptanceOneList";
	}
	
	@RequiresPermissions("out:outcontractAcceptance:edit")
	@RequestMapping(value = "delete")
	public String delete(OutcontractAcceptance outcontractAcceptance, HttpServletRequest request, HttpServletResponse response,Model model) {
		outcontractAcceptanceService.delete(outcontractAcceptance);
	String	message= "删除验收单信息成功";
		String id=request.getParameter("outcontract");
		System.out.println("合同编号为"+id);
	
		//1
		OutcontractAcceptance outcontractAcceptance1=new OutcontractAcceptance();
		outcontractAcceptance1.setOutcontractId(Integer.parseInt(id));

		Page<OutcontractAcceptance> page = outcontractAcceptanceService.findPage(new Page<OutcontractAcceptance>(request, response), outcontractAcceptance1); 
		for(int i=0;i<page.getList().size();i++){
		page.getList().get(i).setOutcontractName(outcontractService.get(""+page.getList().get(i).getOutcontractId()).getName());
		}

		Outcontract newoutcontract =new Outcontract();
		model.addAttribute("outcontractList", 	outcontractService.findList(newoutcontract));

       model.addAttribute("message", message);
       model.addAttribute("outcontract",outcontractService.get(id));
		model.addAttribute("page", page);
		return "modules/out/outcontractAcceptanceOneList";
	}
	
	
	
//查看验收单
	@RequestMapping(value = "searchlook")
	public String searchlook(HttpServletRequest req, OutcontractAcceptance outcontractAcceptance, Model model) {
		
		model.addAttribute("url", req.getParameter("url"));
		return "modules/out/look";
	}
	
	
	
	//下载
	@RequestMapping(value = "upload")
	public void upload(HttpServletRequest request,
			HttpServletResponse response,String[] url,OutcontractAcceptance outcontractAcceptance, Model model) {
	  System.out.println("url"+url);
	  for(int i=0;i<url.length;i++){
		  System.out.println(url[i]);
	  }
	  try{
		//多文件下载
      byte[] buffer = new byte[1024];   
      
      //生成的ZIP文件名为Demo.zip   
 
      String strZipName = "e:/Demo.zip";   
      File a=new File(strZipName);
      if(a.exists()){
    	  a.delete();
      }
 
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));   
 
      //需要同时下载的两个文件result.txt ，source.txt   
 File file=null;
      for(int i=0;i<url.length;i++) {   
    	  file=new File(url[i]);
          FileInputStream fis = new FileInputStream(file);   
          out.putNextEntry(new ZipEntry(file.getName()));   
          int len;   
          //读入需要下载的文件的内容，打包到zip文件   
         while((len = fis.read(buffer))>0) {   
          out.write(buffer,0,len);    
         }   
          out.closeEntry();   
          fis.close();   
      }   
       out.close();   
       System.out.println("生成Demo.zip成功");   
       
 			   File file1 = new File(strZipName);
 			             if (file1.exists()) {   
 			                String dfileName = file1.getName();   
 			                InputStream fis = new BufferedInputStream(new FileInputStream( file1));   
 			                 response.reset();   
 			                 response.setContentType("application/x-download");
 			                 response.addHeader("Content-Disposition","attachment;filename="+ new String(dfileName.getBytes(),"iso-8859-1"));
 			                 response.addHeader("Content-Length", "" + file1.length());   
 			                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());   
 			               response.setContentType("application/octet-stream");   
 			                 byte[] buffer2 = new byte[1024 * 1024 * 4];   
 			                 int i = -1;   
 			                 while ((i = fis.read(buffer2)) != -1) {   
 			                     toClient.write(buffer2, 0, i);  
 			                     
 			                 }   
 			                 fis.close();   
 			                 toClient.flush();   
 			                 toClient.close();   }
 		
	  }catch (Exception e) {
		  System.out.println("生成Demo.zip不成功");   
	}
	
	
	
	}

}