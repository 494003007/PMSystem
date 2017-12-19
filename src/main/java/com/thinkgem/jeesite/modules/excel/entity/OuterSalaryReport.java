/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.excel.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.common.utils.excel.fieldtype.GetName;
import com.thinkgem.jeesite.modules.att.entity.Salary;

import com.thinkgem.jeesite.modules.fin.entity.AllowanceList;
import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.per.entity.EmployeeType;

/**
 * 工资报表Entity
 * @author cdoublej
 * @version 2016-10-26
 */
public class OuterSalaryReport extends DataEntity<OuterSalaryReport> {
	DecimalFormat d = new DecimalFormat("###,##0.00");	
	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM");
	private String employeeId;		// 员工编号
	private Date attendanceMonth;		// 考勤月份
	private String month;
	private Integer attendanceSum;		// 当月需考勤天数
	private String employeeids;
	private String projectids;
	private Integer addAttendanceSum ;
	private String projectId;
	private String performanceCoefficient;//绩效系数
	private int projectAttendanceSum;
	private String allProjectID;
	private int status;						//考勤状态
	private String projectAllowanceDetailId;
	private String projectAllowanceDetailstatu;
	private Integer attendanceListID;
	private String templeAdjustId;
	private Date thisMonth; //系统当前月份
	 
	private String computerAllowance; //电脑补贴
	private String hotAllowance;//高温补贴
	private String allowancemoney;//证件补贴
	private String temporarymoney;//临时调整
	private String totalSalary;//总收入\
	
    private String totalAttendanceDay;
    private String projectDay;
	private String employeeName;
	private String bankAccount;
	private String bankType;
	private String employeeTypeName;
	private String sysAreaName;
	private String departmentName;
	private String projectName;
	private String lifeSubsidy;
	private String positionSubsidy;
	private String salarySubsidy;
	private String projectSubsidy;
	private String departmentId;
	private String sysAreaId;
	private String templeAllowance;
 
	private String orderPay;//其他扣款
	private String countOnLine;//分身个数
	
	
	@ExcelField(title="分身", align=2, sort=63)
	public String getCountOnLine() {
		return countOnLine;
	}
	public void setCountOnLine(String countOnLine) {
		this.countOnLine = countOnLine;
	}
	@ExcelField(title="月份", align=2, sort=65)
	public String getMonth() {
		 
		return s.format(attendanceMonth);
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@ExcelField(title="其他扣款", align=3, sort=1100)
	public String getOrderPay() {
		return d.format(Double.parseDouble(orderPay==null?"0":orderPay));
	}
	public void setOrderPay(String orderPay) {
		this.orderPay = orderPay;
	}
	public String getTempleAdjustId() {
		return templeAdjustId;
	}
	public void setTempleAdjustId(String templeAdjustId) {
		this.templeAdjustId = templeAdjustId;
	}
	public String getEmployeeids() {
		return employeeids;
	}
	public void setEmployeeids(String employeeids) {
		this.employeeids = employeeids;
	}
	public String getProjectids() {
		return projectids;
	}
	public void setProjectids(String projectids) {
		this.projectids = projectids;
	}
	@ExcelField(title="临时调整", align=3, sort=990)
	public String getTempleAllowance() {
		 
		return d.format(Double.parseDouble(templeAllowance==null?"0":templeAllowance));
	}
	public void setTempleAllowance(String templeAllowance) {
		this.templeAllowance = templeAllowance;
	}
	 
	@ExcelField(title="正常出勤天数", align=2, sort=80)
	public int getProjectAttendanceSum() {
		return projectAttendanceSum;
	}
	public void setProjectAttendanceSum(int projectAttendanceSum) {
		this.projectAttendanceSum = projectAttendanceSum;
	}
	
	public String getTotalAttendanceDay() {
		return totalAttendanceDay;
	}
	public void setTotalAttendanceDay(String totalAttendanceDay) {
		this.totalAttendanceDay = totalAttendanceDay;
	}
	
	public String getProjectDay() {
		return projectDay;
	}
	public void setProjectDay(String projectDay) {
		this.projectDay = projectDay;
	}
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	@ExcelField(title="姓名", align=2, sort=30)
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@ExcelField(title="      账     号       ", align=2, sort=60)
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	@ExcelField(title="开户行", align=2, sort=50,dictType="bank")
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	@ExcelField(title="员工类别", align=2, sort=40)
	public String getEmployeeTypeName() {
		return employeeTypeName;
	}
	public void setEmployeeTypeName(String employeeTypeName) {
		this.employeeTypeName = employeeTypeName;
	}
	@ExcelField(title="区域/部门", align=2, sort=10)
	public String getSysAreaName() {
		return sysAreaName;
	}
	public void setSysAreaName(String sysAreaName) {
		this.sysAreaName = sysAreaName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@ExcelField(title="   项目名称    ", align=2, sort=20)
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@ExcelField(title="生活津贴（元）", align=3, sort=120)
	public String getLifeSubsidy() {
		
		return d.format(Double.parseDouble(lifeSubsidy==null?"0":lifeSubsidy));
	}
	public void setLifeSubsidy(String lifeSubsidy) {
		this.lifeSubsidy = lifeSubsidy;
	}
	@ExcelField(title="岗位津贴（元）", align=3, sort=126)
	public String getPositionSubsidy() {
		return d.format(Double.parseDouble(positionSubsidy==null?"0":positionSubsidy));
	}
	public void setPositionSubsidy(String positionSubsidy) {
		this.positionSubsidy = positionSubsidy;
	}
	@ExcelField(title="工资基准（元/月）", align=3, sort=110)
	public String getSalarySubsidy() {
		return  d.format(Double.parseDouble(salarySubsidy==null?"0":salarySubsidy	));
	}
	public void setSalarySubsidy(String salarySubsidy) {
		this.salarySubsidy = salarySubsidy;
	}
	@ExcelField(title="项目补贴标准（元/天）", align=3, sort=100)
	public String getProjectSubsidy() {
	 
		return d.format(Double.parseDouble(projectSubsidy==null?"0":projectSubsidy));
	}
	public void setProjectSubsidy(String projectSubsidy) {
		this.projectSubsidy = projectSubsidy;
	}
	@ExcelField(title="理论工资", align=3, sort=1000)
	public String getTotalSalary() {
		return d.format(Double.parseDouble(totalSalary==null?"0":totalSalary));
	}
	public void setTotalSalary(String totalSalary) {
		this.totalSalary = totalSalary;
	}
	public String getTemporarymoney() {
		return temporarymoney;
	}
	public void setTemporarymoney(String temporarymoney) {
		this.temporarymoney = temporarymoney;
	}
	@ExcelField(title="证件津贴（元）", align=3, sort=160)
	public String getAllowancemoney() {
		return d.format(Double.parseDouble(allowancemoney==null?"0":allowancemoney));
	}
	public void setAllowancemoney(String allowancemoney) {
		this.allowancemoney = allowancemoney;
	}
	@ExcelField(title="电脑补贴（元）", align=3, sort=165)
	public String getComputerAllowance() {
		return  d.format(Double.parseDouble(computerAllowance==null?"0":computerAllowance));
	}
	public void setComputerAllowance(String computerAllowance) {
		this.computerAllowance = computerAllowance;
	}
	@ExcelField(title="高温补贴（元）", align=3, sort=169)
	public String getHotAllowance() {
		return d.format(Double.parseDouble(hotAllowance==null?"0":hotAllowance));
	}
	public void setHotAllowance(String hotAllowance) {
		this.hotAllowance = hotAllowance;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public Date getAttendanceMonth() {
		return attendanceMonth;
	}
	public void setAttendanceMonth(Date attendanceMonth) {
		this.attendanceMonth = attendanceMonth;
	}
	@ExcelField(title="考勤总天数", align=2, sort=70)
	public Integer getAttendanceSum() {
		return attendanceSum;
	}
	public void setAttendanceSum(Integer attendanceSum) {
		this.attendanceSum = attendanceSum;
	}
 
	public Integer getAddAttendanceSum() {
		return addAttendanceSum;
	}
	public void setAddAttendanceSum(Integer addAttendanceSum) {
		this.addAttendanceSum = addAttendanceSum;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@ExcelField(title="绩效系数", align=2, sort=90)
	public String getPerformanceCoefficient() {
		return d.format(Double.parseDouble(performanceCoefficient==null?"0":performanceCoefficient));
	}
	public void setPerformanceCoefficient(String performanceCoefficient) {
		this.performanceCoefficient = performanceCoefficient;
	}
	 
	public String getAllProjectID() {
		return allProjectID;
	}
	public void setAllProjectID(String allProjectID) {
		this.allProjectID = allProjectID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getProjectAllowanceDetailId() {
		return projectAllowanceDetailId;
	}
	public void setProjectAllowanceDetailId(String projectAllowanceDetailId) {
		this.projectAllowanceDetailId = projectAllowanceDetailId;
	}
	public String getProjectAllowanceDetailstatu() {
		return projectAllowanceDetailstatu;
	}
	public void setProjectAllowanceDetailstatu(String projectAllowanceDetailstatu) {
		this.projectAllowanceDetailstatu = projectAllowanceDetailstatu;
	}
	public Integer getAttendanceListID() {
		return attendanceListID;
	}
	public void setAttendanceListID(Integer attendanceListID) {
		this.attendanceListID = attendanceListID;
	}
	 
	public Date getThisMonth() {
		return thisMonth;
	}
	public void setThisMonth(Date thisMonth) {
		this.thisMonth = thisMonth;
	}
	 
}