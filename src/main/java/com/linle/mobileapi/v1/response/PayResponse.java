package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class PayResponse extends BaseResponse {

	private static final long serialVersionUID = -7844482153829930282L;
	
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
