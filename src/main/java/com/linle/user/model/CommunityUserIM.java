package com.linle.user.model;

import java.io.Serializable;

/**
 * 
 * @author pangd
 * @Description 小区居民聊天列表
 */
public class CommunityUserIM implements Serializable{

	private static final long serialVersionUID = -4059295572357607308L;
	
	private Long id;
	
	private String name;
	
	private String portraitUri;

	public Long getId() {
		return id;
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

	public String getPortraitUri() {
		return portraitUri;
	}

	public void setPortraitUri(String portraitUri) {
		this.portraitUri = portraitUri;
	}

}
