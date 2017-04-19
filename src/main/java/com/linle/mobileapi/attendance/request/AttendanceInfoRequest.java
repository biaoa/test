package com.linle.mobileapi.attendance.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class AttendanceInfoRequest extends BaseRequest {

	private static final long serialVersionUID = -1752154726189281206L;

	@NotNull(message = "日期不能为空")
	private Long date;

	@NotNull(message = "类型不能为空")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
