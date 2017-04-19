package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class SuggestionsCommentOperateRequest  extends BaseRequest {
	
	@NotNull(message="suggestionsId不能为空")
	private Long suggestionsId;
	
	@NotNull(message="内容不能为空")
	private String content;

	public Long getSuggestionsId() {
		return suggestionsId;
	}

	public void setSuggestionsId(Long suggestionsId) {
		this.suggestionsId = suggestionsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}
