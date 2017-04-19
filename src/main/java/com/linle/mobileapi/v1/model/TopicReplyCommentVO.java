package com.linle.mobileapi.v1.model;

import java.util.Date;

public class TopicReplyCommentVO {
	private long commentId;
	private long topicId;//话题id
	public String fromUserId;//回复人
    public String fromUserName;//回复人名称
    public String fromUserNameImg;//回复人头像
    public String replyContent; // 回复的内容
    public Date createTime; // 回复时间
  
	private String image ;// 发布信息第一张图片(如果有就返回地址,没有就返回null)
    public String topicContent; // 发布信息的文字(没有图片必须有文字)

    
    
    
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
    public String getReplyContent() {
  		return replyContent;
  	}
  	public void setReplyContent(String replyContent) {
  		this.replyContent = replyContent;
  	}
  	public String getImage() {
  		return image;
  	}
  	public void setImage(String image) {
  		this.image = image;
  	}
  	public String getTopicContent() {
  		return topicContent;
  	}
  	public void setTopicContent(String topicContent) {
  		this.topicContent = topicContent;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
