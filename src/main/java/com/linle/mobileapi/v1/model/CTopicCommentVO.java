package com.linle.mobileapi.v1.model;

import java.util.Date;

public class CTopicCommentVO {
	private long commentId;
 	public String fromUserId;
    public String fromUserName;
    public String fromUserNameImg;
    public String toUserName;		
    public String content;
    public Date createTime;
    private Boolean isMain;
    private int identityId;//用户身份
    
    
    public int getIdentityId() {
		return identityId;
	}

	public void setIdentityId(int identityId) {
		this.identityId = identityId;
	}

	public Boolean getIsMain() {
 		return isMain;
 	}

 	public void setIsMain(Boolean isMain) {
 		this.isMain = isMain;
 	}
 	
	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromUserNameImg() {
		return fromUserNameImg;
	}
	public void setFromUserNameImg(String fromUserNameImg) {
		this.fromUserNameImg = fromUserNameImg;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
