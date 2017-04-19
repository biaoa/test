package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class PayRequest extends BaseRequest {

	private static final long serialVersionUID = 5433690460849339551L;
	
	private String orderNo;
	
	private String channel;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
