package com.linle.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public class UserLoginToken  extends UsernamePasswordToken {
	private LoginMode loginMode;

	public LoginMode getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(LoginMode loginMode) {
		this.loginMode = loginMode;
	}

	public enum LoginMode {
		password, token;
	}
}
