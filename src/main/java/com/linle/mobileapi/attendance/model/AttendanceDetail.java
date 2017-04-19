package com.linle.mobileapi.attendance.model;

import java.util.Date;
import java.util.List;

public class AttendanceDetail {
	private Date dutyDate; //打卡时间
	
	private String description;//打卡描述
	
	private List<String> imgs;//打卡图片
	
	private int status; //打卡状态 0 正常 1 异常

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
