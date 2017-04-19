package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class DelOrderRequest extends BaseRequest {

	private static final long serialVersionUID = 8238521150705557581L;
	
	@NotNull(message="订单号不能为空")
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
