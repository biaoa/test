package com.linle.mobileapi.v1.model;

/**
 * 
* @ClassName: RongTokenVO 
* @Description: 融云token vo
* @author pangd
* @date 2016年3月31日 下午8:10:22 
*
 */
public class RongTokenVO {
	private String code;
	
	private String userId;
	
	private String token;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
