package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class TopicCommentResponse extends BaseResponse {

	private long commentId;

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

}
