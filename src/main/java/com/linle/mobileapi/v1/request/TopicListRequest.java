package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class TopicListRequest extends BaseRequest {
	
	private Long typeId;//类型Id  （活动，帮帮忙，家园二手，发牢骚，好友圈）好友圈就是只有好友才能看得见
	
	private int communityPrivg;//是否查看所有小区 1：查看所有小区  0:查看当前小区
	
	private String toUserId;
	
	private Integer pageSize;
	
	private Integer pageNumber;
	
	public int getCommunityPrivg() {
		return communityPrivg;
	}

	public void setCommunityPrivg(int communityPrivg) {
		this.communityPrivg = communityPrivg;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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
