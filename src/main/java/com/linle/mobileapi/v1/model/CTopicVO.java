package com.linle.mobileapi.v1.model;

import java.util.Date;
import java.util.List;

public class CTopicVO {
	
	private String topicId;//话题ID
	
	private String userId;//用于聊天使用的id ,创建者
	
	private String userImg; //头像
	
	private String communityName; //楼主地址（小区名称）
	
	private String nickName; //昵称
	
	private List<String> topicImages;//图片集合
	private List<String> thumImages;
	
	private String topicContent;//内容
	
	private String sex;//性别
	
	private Date createTime;//创建时间
	
	private long commentNum;//评论数
	
	private long supportNum;//赞的次数   
	
	private boolean isSupport;//是不是点赞过
	
	private boolean isReport;//是不是举报过
	
	private int identityId;
	
	
	
	public List<String> getThumImages() {
		return thumImages;
	}
	public void setThumImages(List<String> thumImages) {
		this.thumImages = thumImages;
	}
	public int getIdentityId() {
		return identityId;
	}
	public void setIdentityId(int identityId) {
		this.identityId = identityId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public List<String> getTopicImages() {
		return topicImages;
	}
	public void setTopicImages(List<String> topicImages) {
		this.topicImages = topicImages;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(long commentNum) {
		this.commentNum = commentNum;
	}
	public long getSupportNum() {
		return supportNum;
	}
	public void setSupportNum(long supportNum) {
		this.supportNum = supportNum;
	}
	public boolean getIsSupport() {
		return isSupport;
	}
	public void setIsSupport(boolean isSupport) {
		this.isSupport = isSupport;
	}

	public boolean getIsReport() {
		return isReport;
	}
	public void setIsReport(boolean isReport) {
		this.isReport = isReport;
	}
}
