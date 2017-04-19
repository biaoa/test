package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.TopicUnreadVO;

/**
 * 
 * @author pangd
 * @Description 圈子是否有新内容响应
 * @date 2016年10月18日上午11:11:29
 */
public class HasNewTopicResponse extends BaseResponse {

	private static final long serialVersionUID = 6750328197721717072L;
	
	private int hasNewTopic;
	
	private List<TopicUnreadVO> unreadList;

	public int getHasNewTopic() {
		return hasNewTopic;
	}

	public void setHasNewTopic(int hasNewTopic) {
		this.hasNewTopic = hasNewTopic;
	}

	public List<TopicUnreadVO> getUnreadList() {
		return unreadList;
	}

	public void setUnreadList(List<TopicUnreadVO> unreadList) {
		this.unreadList = unreadList;
	}
}
