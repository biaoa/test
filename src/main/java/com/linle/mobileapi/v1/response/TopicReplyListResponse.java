package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.TopicReplyCommentVO;

public class TopicReplyListResponse extends BaseResponse {

	
	private List<TopicReplyCommentVO> data;

	public List<TopicReplyCommentVO> getData() {
		return data;
	}

	public void setData(List<TopicReplyCommentVO> data) {
		this.data = data;
	}

}
