package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.NeedLoginRequest;

public class ChangeUserInfoRequest extends NeedLoginRequest {
	private static final long serialVersionUID = 1489480457793589367L;
	
	private String  sex;
	
	private String name;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
