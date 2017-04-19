package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class EvaluateHeadRequest extends BaseRequest {

	private static final long serialVersionUID = 7268119961833380263L;
	
	private String  orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
