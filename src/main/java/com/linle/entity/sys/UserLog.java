package com.linle.entity.sys;

import com.linle.entity.enumType.UserAction;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public class UserLog extends BaseDomain{
	private Users user;
	private UserAction action;
	private String loginIp;
	
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UserAction getAction() {
		return action;
	}
	public void setAction(UserAction action) {
		this.action = action;
	}
	
}
