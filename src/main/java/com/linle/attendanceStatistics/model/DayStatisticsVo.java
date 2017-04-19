package com.linle.attendanceStatistics.model;

import java.io.Serializable;
import java.util.Date;

import com.linle.common.util.ExcelVOAttribute;

public class DayStatisticsVo implements Serializable {

	private static final long serialVersionUID = 4347800001383970161L;
	
	@ExcelVOAttribute(column = "A", name = "日期")
	private String showDate;
	
	private Date data;
	
	@ExcelVOAttribute(column = "B", name = "姓名")
	private String name;
	
	@ExcelVOAttribute(column = "C", name = "部门")
	private String departmentName;
	
	private Long templateId;
	
	@ExcelVOAttribute(column = "D", name = "所属规则")
	private String templateName;
	
	@ExcelVOAttribute(column = "E", name = "上班时间")
	private String onDuty;
	
	@ExcelVOAttribute(column = "G", name = "下班时间")
	private String offDuty;
	
	private Long mainId;
	
	
	private Date onDutyDate;
	
	private Date offDutyDate;
	
	private Integer status;
	
	private Integer dayType; //今天是否需要上班
	
	private Long uid;
	
	@ExcelVOAttribute(column = "F", name = "签到时间")
	private String showonDutyDate;
	
	@ExcelVOAttribute(column = "H", name = "签退时间")
	private String showoffDutyDate;
	
	@ExcelVOAttribute(column = "I", name = "状态")
	private String showStatus;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

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

	public String getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(String onDuty) {
		this.onDuty = onDuty;
	}

	public String getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(String offDuty) {
		this.offDuty = offDuty;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public Date getOffDutyDate() {
		return offDutyDate;
	}

	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public Integer getDayType() {
		return dayType;
	}

	public void setDayType(Integer dayType) {
		this.dayType = dayType;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getShowonDutyDate() {
		return showonDutyDate;
	}

	public void setShowonDutyDate(String showonDutyDate) {
		this.showonDutyDate = showonDutyDate;
	}

	public String getShowoffDutyDate() {
		return showoffDutyDate;
	}

	public void setShowoffDutyDate(String showoffDutyDate) {
		this.showoffDutyDate = showoffDutyDate;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}


}
