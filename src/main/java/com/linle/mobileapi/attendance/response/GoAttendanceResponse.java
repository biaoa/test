package com.linle.mobileapi.attendance.response;

import com.linle.mobileapi.attendance.model.AttendanceDetail;
import com.linle.mobileapi.attendance.model.GoAttendance;
import com.linle.mobileapi.base.BaseResponse;

public class GoAttendanceResponse extends BaseResponse {

	private static final long serialVersionUID = 6652328748682859150L;
	
	private GoAttendance basicInfo; //考勤基本信息
	
	private AttendanceDetail onDutyInfo; //上班打卡信息
	
	private AttendanceDetail offDutyInfo;//下班打卡信息
	
	private String propertyLogo; //物业logo

	public GoAttendance getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(GoAttendance basicInfo) {
		this.basicInfo = basicInfo;
	}

	public AttendanceDetail getOnDutyInfo() {
		return onDutyInfo;
	}

	public void setOnDutyInfo(AttendanceDetail onDutyInfo) {
		this.onDutyInfo = onDutyInfo;
	}

	public AttendanceDetail getOffDutyInfo() {
		return offDutyInfo;
	}

	public void setOffDutyInfo(AttendanceDetail offDutyInfo) {
		this.offDutyInfo = offDutyInfo;
	}

	public String getPropertyLogo() {
		return propertyLogo;
	}

	public void setPropertyLogo(String propertyLogo) {
		this.propertyLogo = propertyLogo;
	}

}
