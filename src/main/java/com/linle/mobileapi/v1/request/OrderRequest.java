package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class OrderRequest extends BaseRequest {

	private static final long serialVersionUID = -8229082379566145667L;
	
	private String type; //类型 车位：space
	
	private Integer status; //状态 0 待付款 1 已支付 2已关闭
	
	private Integer pageSize;
	
	private Integer pageNumber;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
