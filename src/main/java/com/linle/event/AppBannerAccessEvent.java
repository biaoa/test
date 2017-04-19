package com.linle.event;

import org.springframework.context.ApplicationEvent;

public class AppBannerAccessEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1654462261821877734L;

	private Long uid;

	private Long communityId;

	public AppBannerAccessEvent(Long bannerId, Long uid, Long communityId) {
		super(bannerId);
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
