package com.linle.io.rong.models;

/**
 * 
 * @author pangd
 * @Description 融云返回的token信息
 */
public class GetTokenResult {
	
	private int code;
	
	private Long userId;
	
	private String token;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
