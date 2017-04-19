package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class CommodityResponse extends BaseResponse {

	private static final long serialVersionUID = -6530963149197508664L;
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

}
