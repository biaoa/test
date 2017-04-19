package com.linle.mobileapi.attendance.model;

import java.util.Date;
import java.util.List;

public class AttendanceInfoMode {
	
	private Date dutyDate; //考勤时间

	private String address; //考勤位置中的一个

	private String description;//打卡描述

	private List<String> imgs; //描述中的图片

	private Integer status; //打卡状态  0 正常 1 距离异常 2 迟到 3 早退

	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
