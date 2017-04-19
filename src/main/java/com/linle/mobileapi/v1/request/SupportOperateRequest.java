package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class SupportOperateRequest extends BaseRequest {
	
	private Long topicId;
	
	private long commentId;
	
	private Integer isSupport;
	
	
	
	public long getCommentId() {
		return commentId;
	}
	
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Integer getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(Integer isSupport) {
		this.isSupport = isSupport;
	}

}
