package com.linle.mobileapi.v1.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 订单详情请求参数
 */
public class OrderDetailRequest extends BaseRequest {

	private static final long serialVersionUID = 3696978146908706194L;
	
	@NotEmpty(message="订单编号不能为空")
	private String orderNo;
	
//	@NotEmpty(message="订单类型不能为空")
//	private String orderType;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
