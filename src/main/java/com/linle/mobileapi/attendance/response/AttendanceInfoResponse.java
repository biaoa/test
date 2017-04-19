package com.linle.mobileapi.attendance.response;

import com.linle.mobileapi.attendance.model.AttendanceInfoMode;
import com.linle.mobileapi.base.BaseResponse;

public class AttendanceInfoResponse extends BaseResponse {

	private static final long serialVersionUID = 8479113699147609946L;
	
	private AttendanceInfoMode info;

	public AttendanceInfoMode getInfo() {
		return info;
	}

	public void setInfo(AttendanceInfoMode info) {
		this.info = info;
	}

}
