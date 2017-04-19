package com.linle.mobileapi.v1.model;

import java.io.Serializable;
import java.util.Date;

public class CommunityNoticeVO implements Serializable{
	private Long id;
	
	private String title;
	
	private Date createDate;
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    
    
}
