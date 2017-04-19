package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.NeedLoginRequest;

public class ChangePhoneRequest extends NeedLoginRequest {

	private static final long serialVersionUID = -7026994016833430882L;
	
	@NotNull(message="手机号码不能为空")
	private String phone;
	
	@NotNull(message="验证码不能为空")
	private String code;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
