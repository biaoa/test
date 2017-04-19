package com.linle.mobileapi.v1.model;

/**
 * 
* @ClassName: UserInfoVO 
* @Description: 融云 根据用户ID 获得头像和昵称
* @author pangd
* @date 2016年3月29日 下午5:14:54 
*
 */
public class UserVO {
	
	private long userId;
	private String picture ;
	private String nickname ;
	private String sex ;
	
	public String getPicture() {
		return picture;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
