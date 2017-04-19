package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CTopicCommentVO;
import com.linle.mobileapi.v1.model.CTopicVO;

public class TopicCommentListResponse extends BaseResponse {

	private CTopicVO topic;

	private List<CTopicCommentVO> data;

	public List<CTopicCommentVO> getData() {
		return data;
	}

	public void setData(List<CTopicCommentVO> data) {
		this.data = data;
	}
	public CTopicVO getTopic() {
		return topic;
	}

	public void setTopic(CTopicVO topic) {
		this.topic = topic;
	}

}
