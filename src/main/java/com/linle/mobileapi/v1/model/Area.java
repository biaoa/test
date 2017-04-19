package com.linle.mobileapi.v1.model;

import java.util.List;

import com.linle.community.model.Community;

/**
 * 响应的省数据
 * @author pangd
 *
 */
public class Area {
	private Long id;
	
	private String areaName;
	
	private List<Community> communityList;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<Community> getCommunityList() {
		return communityList;
	}

	public void setCommunityList(List<Community> communityList) {
		this.communityList = communityList;
	}
}
