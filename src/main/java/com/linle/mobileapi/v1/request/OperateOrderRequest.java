package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class OperateOrderRequest extends BaseRequest {

	private static final long serialVersionUID = 989095902941972008L;
	
	@NotNull(message="订单号不能为空")
	private String orderNo;
	
	@NotNull(message="订单号不能为空")
	private Integer status;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
