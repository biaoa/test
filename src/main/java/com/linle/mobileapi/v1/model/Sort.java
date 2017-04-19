package com.linle.mobileapi.v1.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sort {
	
	private Long sortId;
	
	private String sortName;
	
	private int sortNums;
	
	@JsonIgnore
	private Long communityId;
	
	private List<ChildSort> childSortList;
	
	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public int getSortNums() {
		return sortNums;
	}

	public void setSortNums(int sortNums) {
		this.sortNums = sortNums;
	}

	public List<ChildSort> getChildSortList() {
		return childSortList;
	}

	public void setChildSortList(List<ChildSort> childSortList) {
		this.childSortList = childSortList;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

}