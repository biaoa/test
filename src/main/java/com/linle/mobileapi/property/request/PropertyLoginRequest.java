package com.linle.mobileapi.property.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author chenk
 * @Description 物业版登陆请求
 * @date 2016年8月12日
 */
public class PropertyLoginRequest extends BaseRequest {

	private static final long serialVersionUID = 1929599484782996231L;

	@NotNull(message = "用户名不能为空")
	private String userName;
	
	@NotNull(message = "密码不能为空")
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
