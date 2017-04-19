package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class VerifySMSCodeRequest extends BaseRequest {
	
	private static final long serialVersionUID = 8474272410770145324L;
	
	@NotNull(message="手机号码不能为空")
	private String phone;
	
	@NotNull(message="验证码不能为空")
	private String smsCode;//验证码

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
