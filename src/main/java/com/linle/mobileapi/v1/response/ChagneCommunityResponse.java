package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class ChagneCommunityResponse extends BaseResponse {

	private static final long serialVersionUID = 952113829034052214L;
	
	private String address;
	
	private String addressDetails;
	
	private String cityName;
	
	private Long communityRongId;
	
	private String communityLogo;
	
	private String communityName;
	
	private String communityPhone;
	
	private String communityPresidentPhone;

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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCommunityRongId() {
		return communityRongId;
	}

	public void setCommunityRongId(Long communityRongId) {
		this.communityRongId = communityRongId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCommunityLogo() {
		return communityLogo;
	}

	public void setCommunityLogo(String communityLogo) {
		this.communityLogo = communityLogo;
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
