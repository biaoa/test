package com.linle.user.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.entity.enumType.UserType;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月20日
 **/
public class AccountInfo {
	private String epName;
	@NotEmpty(message="用户名不能为空")
	private String userName;
	@NotEmpty(message="密码不能为空")
	private String password;
	@NotNull(message="类型不能为空")
	private UserType userType;
	@NotNull(message="类型不能为空")
	private Long regionId;
	
	@NotEmpty(message="email不能为空")
	private String email;
	
	/**
	 * logo
	 */
	private String logo;
	private String contactMan;
	private String phone;
	private String address;
	private String intro;
	


	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getEpName() {
		return epName;
	}

	public void setEpName(String epName) {
		this.epName = epName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
