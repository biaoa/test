package com.linle.mobileapi.base;

import java.io.Serializable;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015-8-11
 **/
public abstract class BaseRequest implements Serializable{

	private String appKey;
	
	private String sign;
	
	private String sid;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}	

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
