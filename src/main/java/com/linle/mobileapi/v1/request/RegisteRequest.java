package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: RegisteRequest 
* @Description: 注册请求
* @author pangd
* @date 2016年3月11日 下午2:12:47 
*
 */
public class RegisteRequest {
	
	@NotNull(message="手机号码不能为空")
	private String phone;
	
	@NotNull(message="密码不能为空")
	private String password;
	
	@NotNull(message="验证码不能为空")
	private String smsCode;//验证码
	
	@NotNull(message="请选择小区")
	private Long communityID; //小区ID
	
	@NotNull(message="详细地址不能为空")
	private Long addressDetails;//地址补充

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Long getCommunityID() {
		return communityID;
	}

	public void setCommunityID(Long communityID) {
		this.communityID = communityID;
	}

	public Long getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(Long addressDetails) {
		this.addressDetails = addressDetails;
	}

}
