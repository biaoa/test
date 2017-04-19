package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class CommunityNoticeRequest extends BaseRequest {

	private static final long serialVersionUID = 613295669950527622L;
	
	private Integer pageSize;
	
	private Integer pageNumber;

	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
