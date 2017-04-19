package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
public class ChangeUserLogoResponse extends BaseResponse{
	
	
	private String logo;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
