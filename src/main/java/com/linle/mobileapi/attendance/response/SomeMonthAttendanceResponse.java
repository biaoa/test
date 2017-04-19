package com.linle.mobileapi.attendance.response;

import java.util.List;

import com.linle.mobileapi.attendance.model.SomeMonthAttenVO;
import com.linle.mobileapi.base.BaseResponse;

public class SomeMonthAttendanceResponse extends BaseResponse {

	private static final long serialVersionUID = 1767192248880797857L;
	
	private List<SomeMonthAttenVO> data;

	public List<SomeMonthAttenVO> getData() {
		return data;
	}

	public void setData(List<SomeMonthAttenVO> data) {
		this.data = data;
	}

}
