package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

//退款失败 申请客服介入申请
public class ApplyCustomerServicesRequest extends BaseRequest {

	private static final long serialVersionUID = 7595705442272686937L;
	
	@NotNull(message="订单编号不能为空")
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
