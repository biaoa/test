package com.linle.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.linle.io.rong.models.TxtMessage;

/**
 * @描述:
 * @作者:
 * @创建时间：2016年08月02日
 **/
public class RongMessageEvent extends ApplicationEvent {
	
	
	private static final long serialVersionUID = 1L;
	private Long sendId;
	private List<String> toUserIds;
	private TxtMessage message;
	
	//发送多个用户
	public RongMessageEvent(Long sendId, List<String> toUserIds, TxtMessage message) {
		super(sendId);
		this.sendId = sendId;
		this.toUserIds = toUserIds;
		this.message = message;
	}
	
	//发送一个用户
	public RongMessageEvent(Long sendId, String userId, TxtMessage message) {
		super(sendId);
		List<String> toUserIds=new ArrayList<>();
		toUserIds.add(userId);
		this.sendId = sendId;
		this.toUserIds = toUserIds;
		this.message = message;
	}
	
	public Long getSendId() {
		return sendId;
	}
	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}
	public List<String> getToUserIds() {
		return toUserIds;
	}
	public void setToUserIds(List<String> toUserIds) {
		this.toUserIds = toUserIds;
	}
	public TxtMessage getMessage() {
		return message;
	}
	public void setMessage(TxtMessage message) {
		this.message = message;
	}
	
}
