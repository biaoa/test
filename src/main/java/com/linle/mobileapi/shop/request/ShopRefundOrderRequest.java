package com.linle.mobileapi.shop.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.linle.mobileapi.base.BaseRequest;

public class ShopRefundOrderRequest extends BaseRequest {

	private static final long serialVersionUID = 1032906927250736066L;

	@Min(value = 1, message = "参数错误")
	@Max(value = 2, message = "参数错误")
	@NotNull(message = "参数错误")
	private Integer status;

	@NotBlank(message = "订单号错误")
	private String orderNo;

	private String failReason;// 拒绝原因

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
