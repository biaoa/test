package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class CommentOperateRequest extends BaseRequest {

	private Long topicId;
	
	@NotNull(message="内容不能为空")
	private String content;
	
	@NotNull(message="评论回复对象不能为空")
	private String toUserId;
	
	@NotNull(message="isMain标识不能为空")
	private boolean isMain;
	
	public boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(boolean isMain) {
		this.isMain = isMain;
	}

	public Long getTopicId() {
		return topicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

}
