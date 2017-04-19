package com.linle.mobileapi.v1.model;

import java.io.Serializable;

public class TopicUserManagerVo implements Serializable{
	private int isPublish;

	private int isReply;

	private long topicTypeId;
	private long commTopicTypeId;
	
	public long getCommTopicTypeId() {
		return commTopicTypeId;
	}

	public void setCommTopicTypeId(long commTopicTypeId) {
		this.commTopicTypeId = commTopicTypeId;
	}

	public int getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(int isPublish) {
		this.isPublish = isPublish;
	}

	public int getIsReply() {
		return isReply;
	}

	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}

	public long getTopicTypeId() {
		return topicTypeId;
	}

	public void setTopicTypeId(long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}
	  
	  
}