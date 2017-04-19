package com.linle.user.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Login {

	@NotEmpty(message="用户名不能为空")
	private String userName;
	@NotEmpty(message="密码不能为空")
	private String password;
	private String validateCode;
	private boolean remember;
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
