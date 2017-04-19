package com.linle.mobileapi.property.request;

import com.linle.mobileapi.base.BaseRequest;

public class PatrolRoomRecordListRequest extends BaseRequest {
	
	private String searchValue;
	
	private Integer pageSize;
	
	private Integer pageNumber;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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
