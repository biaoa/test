package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.request.OrderDetailVo2;

public class OrderDetailResponse extends BaseResponse {

	private static final long serialVersionUID = 6909148931805495840L;
	
	private OrderDetailVo2 data;

	public OrderDetailVo2 getData() {
		return data;
	}

	public void setData(OrderDetailVo2 data) {
		this.data = data;
	}
}	
