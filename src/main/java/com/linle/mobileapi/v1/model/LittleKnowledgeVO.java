package com.linle.mobileapi.v1.model;

import java.util.Date;

public class LittleKnowledgeVO {
	private Long id;
	
	private String title;
	
	private String description;
	
	private String img;
	
	private Date createDate;
	
	private int readCount; //阅读量
	
	private int thumbCount;//点赞数量
	
	private int thumbStatus; //0  已点赞  1 未点赞

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getThumbCount() {
		return thumbCount;
	}

	public void setThumbCount(int thumbCount) {
		this.thumbCount = thumbCount;
	}

	public int getThumbStatus() {
		return thumbStatus;
	}

	public void setThumbStatus(int thumbStatus) {
		this.thumbStatus = thumbStatus;
	}
}
