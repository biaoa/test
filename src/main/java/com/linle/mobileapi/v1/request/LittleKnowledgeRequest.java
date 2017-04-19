package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class LittleKnowledgeRequest extends BaseRequest {

	private static final long serialVersionUID = 613295669950527622L;
	
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
