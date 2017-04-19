package com.linle.mobileapi.property.response;

import com.linle.mobileapi.base.BaseResponse;

/**
 * 
 * @author chnek
 * @Description 物业登陆返回信息
 * @date 2016年8月12日
 */
public class PropertyLoginResponse extends BaseResponse {

	private static final long serialVersionUID = 5881146290123495996L;
	
	private Long userId;
	
	private String sid;
	
	private String nickName;//名称
	
	private String userLogo;//证件照
	
	private String phone;//联系方式
	
	private String identity;//用户身份
	
	private String identityName;//职位身份
	
	private String departmentName;//部门

	private long communityId;
	
	
	
	public long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	

}
