package com.linle.user.model;

import java.io.Serializable;

public class CommunityOnlineIM implements Serializable{
	private static final long serialVersionUID = -4375964422294192870L;

	private Long id;
	
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
