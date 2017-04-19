package com.linle.mobileapi.shop.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 商家登陆请求
 * @date 2016年7月25日下午3:25:27
 */
public class ShopLoginRequest extends BaseRequest {

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
