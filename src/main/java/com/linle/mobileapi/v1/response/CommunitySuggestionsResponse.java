package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.AdviceVO;

/**
 * 
 * @Description 返回反馈建议详情
 */
public class CommunitySuggestionsResponse extends BaseResponse {

	private AdviceVO communitySuggestions;//反馈建议对象
	
	private List<CommunitySuggestionsComment>   commentList;

	public AdviceVO getCommunitySuggestions() {
		return communitySuggestions;
	}

	public void setCommunitySuggestions(AdviceVO communitySuggestions) {
		this.communitySuggestions = communitySuggestions;
	}

	public List<CommunitySuggestionsComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommunitySuggestionsComment> commentList) {
		this.commentList = commentList;
	}
	
	

	
}
