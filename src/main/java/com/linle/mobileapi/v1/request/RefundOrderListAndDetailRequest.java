package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class RefundOrderListAndDetailRequest extends BaseRequest {

	private static final long serialVersionUID = -5314114077861360019L;
	
	private Integer pageSize;
	
	private Integer pageNumber;

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
