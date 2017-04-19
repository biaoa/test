package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class ReportOperateRequest extends BaseRequest {
	private Long topicId;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
}
