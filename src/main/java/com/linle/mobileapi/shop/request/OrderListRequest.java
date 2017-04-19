package com.linle.mobileapi.shop.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 订单列表请求
 * @date 2016年7月26日下午6:26:51
 */
public class OrderListRequest extends BaseRequest {

	private static final long serialVersionUID = -7629934376740549930L;
	
	@NotNull(message="参数错误")
	private Integer orderStatus; //订单状态
	
	private Integer pageSize;
	
	private Integer pageNumber;

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	

}
