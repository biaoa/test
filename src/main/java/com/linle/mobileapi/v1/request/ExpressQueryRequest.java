package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class ExpressQueryRequest extends BaseRequest {

	private static final long serialVersionUID = -687882450620208710L;
	
	@NotNull(message="快递单号不能为空")
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
