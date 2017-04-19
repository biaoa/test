package com.linle.event;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author pangd
 * @Description 访问邻乐速报 插入访问记录
 * @date 2016年10月20日上午11:20:26
 */
public class KnowledgeAccessRecordEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1654462261821877734L;

	private Long uid;

	private Long communityId;

	public KnowledgeAccessRecordEvent(Long knowledgeId, Long uid, Long communityId) {
		super(knowledgeId);
		this.uid = uid;
		this.communityId = communityId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

}
