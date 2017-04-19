package com.linle.mobileapi.shop.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 接单请求参数
 * @date 2016年7月27日下午1:40:03
 */
public class AcceptOrderRequest extends BaseRequest {

	private static final long serialVersionUID = -2188715272933030893L;
	
	@NotBlank(message="参数错误")
	private String type;
	
	@NotNull(message="参数错误")
	private String orderNo;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
