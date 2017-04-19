package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.NeedLoginRequest;


public class ChangeUserLogoRequest extends NeedLoginRequest{
	
	@NotNull(message="头像不能为空")
	private CommonsMultipartFile logo;

	public CommonsMultipartFile getLogo() {
		return logo;
	}

	public void setLogo(CommonsMultipartFile logo) {
		this.logo = logo;
	}
	
}
