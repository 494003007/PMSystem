/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.per.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.GetName;
import com.thinkgem.jeesite.modules.pro.entity.Project;
import com.thinkgem.jeesite.modules.sys.entity.Department;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;

/**
 * 员工Entity
 * @author cdoublej
 * @version 2016-10-15
 */
public class Employee extends DataEntity<Employee> {

	private static final long serialVersionUID = 1L;
	private String sysAreaId;		// 所属区域
	private String name;		// 姓名
	private Integer departmentId;		// 所属部门
	private Integer employeeTypeId;		// 员工类型
	private String identityCard;		// 身份证
	private int bankType;		// 银行类型
	private String bankAccount;		// 银行账号
	private Date bornDate;		// 出生日期
	private String archivesId;		// 档案编号
	private String oaId;		// OA编号
	private String quarters;		// 岗位
	private String sex;		// 性别
	private Date entryDate;		// 入职时间
	private Date formalDate;		// 转正时间
	private Date quitDate;		// 离职时间
	private String mobile;		// 手机号码
	private String educationType;		// 学历
	private Date graduationDate;		// 毕业时间
	private String graduationSchool;		// 毕业学校
	private String major;		// 专业
	private String address;		// 籍贯
	private String email;		// 邮箱
	private String enteyReson;		// 入职原因
	private String entryExamineRemarks;
	private String quitExamineRemarks;
	private SysArea sysArea; //所属区域
	private Department department;//部门
	private EmployeeType employeeType;//员工类型

	private PositionType quartersType;//岗位类型
	private ArrayList reducelist;//排除的employee id 装id
	private ArrayList selectlist;//只在这个list里边选择
	private String authorityEmployeeId;
	private Project project;//临时项目编号
	private Integer orderId;//其他表关联id
	private String files;
	private String quitReson;
	private String quitFiles;
	private String entryExamineStatus;
	private String quitExamineStatus;
	private String status;
	
	
	
	public String getAuthorityEmployeeId() {
		return authorityEmployeeId;
	}

	public void setAuthorityEmployeeId(String authorityEmployeeId) {
		this.authorityEmployeeId = authorityEmployeeId;
	}

	public String getEntryExamineRemarks() {
		return entryExamineRemarks;
	}

	public void setEntryExamineRemarks(String entryExamineRemarks) {
		this.entryExamineRemarks = entryExamineRemarks;
	}

	public String getQuitExamineRemarks() {
		return quitExamineRemarks;
	}

	public void setQuitExamineRemarks(String quitExamineRemarks) {
		this.quitExamineRemarks = quitExamineRemarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getQuitReson() {
		return quitReson;
	}

	public void setQuitReson(String quitReson) {
		this.quitReson = quitReson;
	}

	public String getQuitFiles() {
		return quitFiles;
	}

	public void setQuitFiles(String quitFiles) {
		this.quitFiles = quitFiles;
	}

	public String getEntryExamineStatus() {
		return entryExamineStatus;
	}

	public void setEntryExamineStatus(String entryExamineStatus) {
		this.entryExamineStatus = entryExamineStatus;
	}

	public String getQuitExamineStatus() {
		return quitExamineStatus;
	}

	public void setQuitExamineStatus(String quitExamineStatus) {
		this.quitExamineStatus = quitExamineStatus;
	}

	@ExcelField(title="所属项目", align=2, sort=20,fieldType=GetName.class)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public ArrayList getSelectlist() {
		return selectlist;
	}

	public void setSelectlist(ArrayList selectlist) {
		this.selectlist = selectlist;
	}

	public ArrayList getReducelist() {
		return reducelist;
	}

	public void setReducelist(ArrayList reducelist) {
		this.reducelist = reducelist;
	}

	public Employee() {
		super();
	}

	public Employee(String id){
		super(id);
	}


	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}

	@Length(min=1, max=20, message="姓名不能为空")
	@ExcelField(title="姓名", align=2, sort=30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@NotNull(message="员工类型不能为空")
	public Integer getEmployeeTypeId() {
		return employeeTypeId;
	}

	public void setEmployeeTypeId(Integer employeeTypeId) {
		this.employeeTypeId = employeeTypeId;
	}

	@Pattern(regexp = "^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$", message = "身份证格式错误")
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}


	public int getBankType() {
		return bankType;
	}

	public void setBankType(int bankType) {
		this.bankType = bankType;
	}

	@Length(min=16, max=19, message="银行账号长度必须介于 16 和 19 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	@Length(min=0, max=20, message="档案编号长度必须介于 0 和 20 之间")
	public String getArchivesId() {
		return archivesId;
	}

	public void setArchivesId(String archivesId) {
		this.archivesId = archivesId;
	}

	@Length(min=0, max=20, message="OA编号长度必须介于 0 和 20 之间")
	public String getOaId() {
		return oaId;
	}

	public void setOaId(String oaId) {
		this.oaId = oaId;
	}

	@NotNull(message="岗位不能为空")
	public String getQuarters() {
		return quarters;
	}

	public void setQuarters(String quarters) {
		this.quarters = quarters;
	}

	@Length(min=0, max=10, message="性别长度必须介于 0 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFormalDate() {
		return formalDate;
	}

	public void setFormalDate(Date formalDate) {
		this.formalDate = formalDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	@Pattern(regexp = "^1[345789][0-9]{9}$", message="手机号码格式错误")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min=0, max=20, message="学历长度必须介于 0 和 20 之间")
	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	@Length(min=0, max=50, message="毕业学校长度必须介于 0 和 50 之间")
	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	@Length(min=0, max=20, message="专业长度必须介于 0 和 20 之间")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Length(min=0, max=50, message="籍贯长度必须介于 0 和 50 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$", message="邮箱格式错误")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=255, message="入职原因长度必须介于 0 和 255 之间")
	public String getEnteyReson() {
		return enteyReson;
	}

	public void setEnteyReson(String enteyReson) {
		this.enteyReson = enteyReson;
	}
	@ExcelField(title="所属区域", align=2, sort=10,fieldType=GetName.class)
	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}
	@ExcelField(title="所属部门", align=2, sort=40,fieldType=GetName.class)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	@ExcelField(title="员工类型", align=2, sort=50,fieldType=GetName.class)
	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public PositionType getQuartersType() {
		return quartersType;
	}

	public void setQuartersType(PositionType quartersType) {
		this.quartersType = quartersType;
	}

}