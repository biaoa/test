package com.linle.attendanceStatistics.model;

import java.io.Serializable;

import com.linle.common.util.ExcelVOAttribute;

public class MonthStatisticsVo implements Serializable {

	private static final long serialVersionUID = -2177849702262110461L;
	
	@ExcelVOAttribute(column = "A", name = "姓名")
	private String name;
	
	@ExcelVOAttribute(column = "B", name = "部门")
	private String departmentName;
	
	@ExcelVOAttribute(column = "C", name = "所属规则")
	private String templateName;
	
	@ExcelVOAttribute(column = "D", name = "工作日")
	private String workDays;
	
	@ExcelVOAttribute(column = "E", name = "打卡异常次数")
	private int abnormalCount;//异常次数
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	public int getAbnormalCount() {
		return abnormalCount;
	}

	public void setAbnormalCount(int abnormalCount) {
		this.abnormalCount = abnormalCount;
	}
}
