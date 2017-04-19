package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;


public class LoginRequest extends BaseRequest{
	
	private static final long serialVersionUID = 2979119134726384143L;
	@NotNull(message="用户名不能为空")
	private String userName;
	@NotNull(message="密码不能为空")
	private String password;
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
	
}
