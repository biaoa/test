package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class OrderSuccessRequest extends BaseRequest {

	private static final long serialVersionUID = 103018785187904192L;
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
