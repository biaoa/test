package com.linle.entity.sys;

import java.util.Date;

public class CTopicComment {
    private Long commentId;

    private Long topicId;//话题id

    private Long fromUserId;//评论者	

    private Long toUserId;//评论目标

    private String content;//内容
    
    private Boolean isRead;//是否已读
    
    private Boolean isMain;
    
	private Date createTime;
	private String fromUserName;//
    private String toUserName;//
    private String fromUserNameImg;//
    private int isDel;
    
    public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
    public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserNameImg() {
		return fromUserNameImg;
	}

	public void setFromUserNameImg(String fromUserNameImg) {
		this.fromUserNameImg = fromUserNameImg;
	}

	public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
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