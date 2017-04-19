package com.linle.mobileapi.v1.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.NeedLoginRequest;


public class ChangePasswordRequest extends NeedLoginRequest{
	private static final long serialVersionUID = 3091991695658113527L;
	@NotEmpty(message = "原密码不能为空")
	private String oldPassword;
	@NotEmpty(message = "新密码不能为空")
	private String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
