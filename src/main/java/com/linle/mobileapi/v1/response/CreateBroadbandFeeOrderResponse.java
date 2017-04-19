package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class CreateBroadbandFeeOrderResponse extends BaseResponse {

	private static final long serialVersionUID = 4587411971292395396L;
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
