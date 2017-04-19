package com.linle.entity.sys;

import java.util.Date;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public class LoginToken extends BaseDomain{
	private String token;
	private Users user;
	private String userName;
	private String password;
	private Date expireTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
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
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	
}
