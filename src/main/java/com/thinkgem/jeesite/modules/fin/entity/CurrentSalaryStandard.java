/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fin.entity;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.modules.per.entity.Employee;
import com.thinkgem.jeesite.modules.sys.entity.SysArea;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * current_salary_standardEntity
 * @author czy
 * @version 2016-10-22
 */
public class CurrentSalaryStandard extends DataEntity<CurrentSalaryStandard> {
	
	private static final long serialVersionUID = 1L;
	private Integer employeeId;		// employee_id
	private String sysAreaName;		// sys_area_name
	private Double currentSalaryStandard;		// current_salary_standard
	private String salaryStandardAdjreason;		// salary_standard_adjreason
	private Date currentSalaryStandardCreatetime;		// current_salary_standard_createtime
	private Double currentTemporaryAdjust;		// current_temporary_adjust
	private String temporaryAdjustReason;		// temporary_adjust_reason
	private Date currentTemporaryAdjustCreatetime;		// current_temporary_adjust_createtime
	private Double currentPositionSubsidy;		// current_position_subsidy
	private String positionSubsidyAdjreason;		// position_subsidy_adjreason
	private Date currentPositionSubsidyCreatetime;		// current_position_subsidy_createtime
	private Double currentLifeSubsidy;		// current_life_subsidy
	private String lifeSubsidyAdjreason;		// life_subsidy_adjreason
	private Date currentLifeSubsidyCreatetime;		// current_life_subsidy_createtime
	private SysArea sysArea;
	private Employee employee;
	private String sysAreaId;
	public CurrentSalaryStandard() {
		super();
	}

	public CurrentSalaryStandard(String id){
		super(id);
	}

	@NotNull(message="employee_id不能为空")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=1, max=64, message="sys_area_name长度必须介于 1 和 64 之间")
	public String getSysAreaName() {
		return sysAreaName;
	}

	public void setSysAreaName(String sysAreaName) {
		this.sysAreaName = sysAreaName;
	}
	
	@NotNull(message="current_salary_standard不能为空")
	public Double getCurrentSalaryStandard() {
		return currentSalaryStandard;
	}

	public void setCurrentSalaryStandard(Double currentSalaryStandard) {
		this.currentSalaryStandard = currentSalaryStandard;
	}
	
	@Length(min=0, max=200, message="salary_standard_adjreason长度必须介于 0 和 200 之间")
	public String getSalaryStandardAdjreason() {
		return salaryStandardAdjreason;
	}

	public void setSalaryStandardAdjreason(String salaryStandardAdjreason) {
		this.salaryStandardAdjreason = salaryStandardAdjreason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCurrentSalaryStandardCreatetime() {
		return currentSalaryStandardCreatetime;
	}

	public void setCurrentSalaryStandardCreatetime(Date currentSalaryStandardCreatetime) {
		this.currentSalaryStandardCreatetime = currentSalaryStandardCreatetime;
	}
	
	public Double getCurrentTemporaryAdjust() {
		return currentTemporaryAdjust;
	}

	public void setCurrentTemporaryAdjust(Double currentTemporaryAdjust) {
		this.currentTemporaryAdjust = currentTemporaryAdjust;
	}
	
	@Length(min=0, max=200, message="temporary_adjust_reason长度必须介于 0 和 200 之间")
	public String getTemporaryAdjustReason() {
		return temporaryAdjustReason;
	}

	public void setTemporaryAdjustReason(String temporaryAdjustReason) {
		this.temporaryAdjustReason = temporaryAdjustReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCurrentTemporaryAdjustCreatetime() {
		return currentTemporaryAdjustCreatetime;
	}

	public void setCurrentTemporaryAdjustCreatetime(Date currentTemporaryAdjustCreatetime) {
		this.currentTemporaryAdjustCreatetime = currentTemporaryAdjustCreatetime;
	}
	
	public Double getCurrentPositionSubsidy() {
		return currentPositionSubsidy;
	}

	public void setCurrentPositionSubsidy(Double currentPositionSubsidy) {
		this.currentPositionSubsidy = currentPositionSubsidy;
	}
	
	@Length(min=0, max=200, message="position_subsidy_adjreason长度必须介于 0 和 200 之间")
	public String getPositionSubsidyAdjreason() {
		return positionSubsidyAdjreason;
	}

	public void setPositionSubsidyAdjreason(String positionSubsidyAdjreason) {
		this.positionSubsidyAdjreason = positionSubsidyAdjreason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCurrentPositionSubsidyCreatetime() {
		return currentPositionSubsidyCreatetime;
	}

	public void setCurrentPositionSubsidyCreatetime(Date currentPositionSubsidyCreatetime) {
		this.currentPositionSubsidyCreatetime = currentPositionSubsidyCreatetime;
	}
	
	public Double getCurrentLifeSubsidy() {
		return currentLifeSubsidy;
	}

	public void setCurrentLifeSubsidy(Double currentLifeSubsidy) {
		this.currentLifeSubsidy = currentLifeSubsidy;
	}
	
	@Length(min=0, max=200, message="life_subsidy_adjreason长度必须介于 0 和 200 之间")
	public String getLifeSubsidyAdjreason() {
		return lifeSubsidyAdjreason;
	}

	public void setLifeSubsidyAdjreason(String lifeSubsidyAdjreason) {
		this.lifeSubsidyAdjreason = lifeSubsidyAdjreason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCurrentLifeSubsidyCreatetime() {
		return currentLifeSubsidyCreatetime;
	}

	public void setCurrentLifeSubsidyCreatetime(Date currentLifeSubsidyCreatetime) {
		this.currentLifeSubsidyCreatetime = currentLifeSubsidyCreatetime;
	}

    public void setLifeSubsidyDetail(LifeSubsidyDetail lifeSubsidyDetail) {
		setCurrentLifeSubsidy(lifeSubsidyDetail.getMoney());
		setCurrentLifeSubsidyCreatetime(lifeSubsidyDetail.getCreateTime());
		setLifeSubsidyAdjreason(lifeSubsidyDetail.getCreateReason());
    }

	public void setPositionSubsidyDetail(PositionSubsidyDetail positionSubsidyDetail) {
		setCurrentPositionSubsidy(positionSubsidyDetail.getMoney());
		setCurrentPositionSubsidyCreatetime(positionSubsidyDetail.getCreateTime());
		setPositionSubsidyAdjreason(positionSubsidyDetail.getCreateReason());
	}

	public void setSalaryStandardDetail(SalaryStandardDetail salaryStandardDetail){
		setCurrentSalaryStandard(salaryStandardDetail.getMoney());
		setSalaryStandardAdjreason(salaryStandardDetail.getCreateReason());
		setCurrentSalaryStandardCreatetime(salaryStandardDetail.getCreateTime());
	}

	public void setTemporaryAdjustDetail(TemporaryAdjustDetail temporaryAdjustDetail){
		setTemporaryAdjustReason(temporaryAdjustDetail.getCreateReason());
		setCurrentTemporaryAdjust(temporaryAdjustDetail.getMoney());
		setCurrentTemporaryAdjustCreatetime(temporaryAdjustDetail.getCreateTime());
	}

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
}