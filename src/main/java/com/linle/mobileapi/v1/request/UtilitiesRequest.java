package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class UtilitiesRequest extends BaseRequest {
	private static final long serialVersionUID = -5693009921180267150L;
	
	private String type;//类型 1 水费 2 电费 3 燃气费

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
