package com.linle.mobileapi.v1.model;

/**
 * 
 * @author chenk
 * @Description 
 */
public class CTopicTypeVO {
	private Long id;
	private String name;
	private Integer communityPrivg;//是否查看所有小区 1：查看所有小区  0:查看当前小区
	
	public Long getId() {
		return id;
	}

	public Integer getCommunityPrivg() {
		return communityPrivg;
	}

	public void setCommunityPrivg(Integer communityPrivg) {
		this.communityPrivg = communityPrivg;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
