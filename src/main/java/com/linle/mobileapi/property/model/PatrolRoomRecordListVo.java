package com.linle.mobileapi.property.model;

public class PatrolRoomRecordListVo extends PatrolRoomRecordVo{
	private long uId;
	    
    private String userImg; //头像
	
	private String nickName; //昵称

	public long getuId() {
		return uId;
	}

	public void setuId(long uId) {
		this.uId = uId;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}