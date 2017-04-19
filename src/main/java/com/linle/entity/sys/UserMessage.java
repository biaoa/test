package com.linle.entity.sys;

import com.linle.entity.enumType.MessageStatus;
import com.linle.entity.enumType.MessageType;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月8日
 **/
public class UserMessage extends BaseDomain{
	/** 用户 **/
	private Users user;
	/** 企业 **/
//	private EpInfo enterprise;
	/** 业务类型 **/
	private MessageType messageType;
	/** 消息的关联ID**/
	private Long ownerId;
	/** 消息的标题 **/
	private String title;
	/** 消息的内容 **/
	private String content;
	/** 消息的状态 **/
	private MessageStatus messageStatus;
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MessageStatus getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}
//	public EpInfo getEnterprise() {
//		return enterprise;
//	}
//	public void setEnterprise(EpInfo enterprise) {
//		this.enterprise = enterprise;
//	}
	
	
}
