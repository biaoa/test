package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

public class ForgotPasswordRequest {
	@NotNull(message="手机号不能为空")
	private String mobilePhone;

	@NotNull(message="密码不能为空")
	private String password;

	@NotNull(message="验证码不能为空")
	private String validateCode;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	
}
