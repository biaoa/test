package com.linle.mobileapi.attendance.model;

import java.util.Date;

public class SomeMonthAttenVO {
	
	private Date date; //具体某一天的时间
	
	private Integer status; // 0正常 1  异常 2 无记录
	
	private String onDutyDate; //签到时间 9:00
	
	private String offDutyDate; //签退时间 18:00
	
	private Date onAttendanceDate;//上班时间 
	
	private Date offAttendanceDate;//下班时间
	
	private Integer onStatus; //上班考勤状态
	
	private Integer offStatus;//下班考勤状态

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(String onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public String getOffDutyDate() {
		return offDutyDate;
	}

	public void setOffDutyDate(String offDutyDate) {
		this.offDutyDate = offDutyDate;
	}

	public Date getOnAttendanceDate() {
		return onAttendanceDate;
	}

	public void setOnAttendanceDate(Date onAttendanceDate) {
		this.onAttendanceDate = onAttendanceDate;
	}

	public Date getOffAttendanceDate() {
		return offAttendanceDate;
	}

	public void setOffAttendanceDate(Date offAttendanceDate) {
		this.offAttendanceDate = offAttendanceDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOnStatus() {
		return onStatus;
	}

	public void setOnStatus(Integer onStatus) {
		this.onStatus = onStatus;
	}

	public Integer getOffStatus() {
		return offStatus;
	}

	public void setOffStatus(Integer offStatus) {
		this.offStatus = offStatus;
	}
}
