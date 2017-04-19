package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UniversalTelephoneVO;

public class UniversalTelephoneResponse extends BaseResponse {

	private static final long serialVersionUID = -1825205312900707724L;
	
	private List<UniversalTelephoneVO> data;

	public List<UniversalTelephoneVO> getData() {
		return data;
	}

	public void setData(List<UniversalTelephoneVO> data) {
		this.data = data;
	}

}
