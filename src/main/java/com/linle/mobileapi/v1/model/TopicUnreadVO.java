package com.linle.mobileapi.v1.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author pangd
 * @Description
 * @date 2016年10月18日下午4:08:28
 */
public class TopicUnreadVO implements Serializable {

	private static final long serialVersionUID = 7474906956762658243L;
	
	private Long typeId; //类型
	
	private int unreadCount; //未读数量
	
	@JsonIgnore
	private Date lastReadTime;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public Date getLastReadTime() {
		return lastReadTime;
	}

	public void setLastReadTime(Date lastReadTime) {
		this.lastReadTime = lastReadTime;
	}

}
