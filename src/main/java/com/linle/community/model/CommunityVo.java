package com.linle.community.model;

import java.util.List;

import com.linle.entity.sys.BaseDomain;

public class CommunityVo extends BaseDomain {
	  private long city;
	
	  private String regionAddress;//地区
	  
	  private List<Community> communityList;//小区集合

	  
	public long getCity() {
		return city;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public String getRegionAddress() {
		return regionAddress;
	}

	public void setRegionAddress(String regionAddress) {
		this.regionAddress = regionAddress;
	}

	public List<Community> getCommunityList() {
		return communityList;
	}

	public void setCommunityList(List<Community> communityList) {
		this.communityList = communityList;
	}
	  
	  
	  
}
