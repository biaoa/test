package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class CommunityListRequest extends BaseRequest {

	private static final long serialVersionUID = -3715326049953729364L;
	
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
