package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class TopicDetailListRequest extends BaseRequest {
	
	private Long topicId;//话题Id  
	
	private int needDetails;//是否需要把话题详情的数据返回过来 0不需要1需要
	
	private Integer pageSize;
	
	private Integer pageNumber;

	
	
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public int getNeedDetails() {
		return needDetails;
	}

	public void setNeedDetails(int needDetails) {
		this.needDetails = needDetails;
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
