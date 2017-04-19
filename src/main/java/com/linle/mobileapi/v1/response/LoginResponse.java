package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.TopicUserManagerVo;

public class LoginResponse extends BaseResponse {
	
	private static final long serialVersionUID = 1L;
	private String sid;
	private Long userId;
	private String username; //用户名
	private String userLogo;
	private String phone;
	private String address;
	private String addressDetails;
	private String sex;
	private String rongToken;
	private String cityName;
	private String communityRongId;//用户对于的 小区ID,聊天要用到
	private String communityLogo;
	private String communityName;
	private String communityPhone; //物业电话
	private String communityPresidentPhone; //社区社长电话
	private TopicUserManagerVo topicUserManager;
	
	
	public TopicUserManagerVo getTopicUserManager() {
		return topicUserManager;
	}

	public void setTopicUserManager(TopicUserManagerVo topicUserManager) {
		this.topicUserManager = topicUserManager;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRongToken() {
		return rongToken;
	}

	public void setRongToken(String rongToken) {
		this.rongToken = rongToken;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCommunityRongId() {
		return communityRongId;
	}

	public void setCommunityRongId(String communityRongId) {
		this.communityRongId = communityRongId;
	}

	public String getCommunityLogo() {
		return communityLogo;
	}

	public void setCommunityLogo(String communityLogo) {
		this.communityLogo = communityLogo;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCommunityPhone() {
		return communityPhone;
	}

	public void setCommunityPhone(String communityPhone) {
		this.communityPhone = communityPhone;
	}

	public String getCommunityPresidentPhone() {
		return communityPresidentPhone;
	}

	public void setCommunityPresidentPhone(String communityPresidentPhone) {
		this.communityPresidentPhone = communityPresidentPhone;
	}
}
