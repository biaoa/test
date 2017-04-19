package com.linle.mobileapi.attendance.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class SomeMonthAttendanceRequest extends BaseRequest {

	private static final long serialVersionUID = -3362246864502725412L;
	
	@NotNull(message="年不能为空")
	private Integer year;
	
	@NotNull(message="月不能为空")
	private Integer month;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

}
