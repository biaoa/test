package com.linle.mobileapi.v1.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 
 * @描述
 * @作者  杨立忠 
 * @版本 V1.0 
 * @创建时间：2015-9-8
 */
public class Messages {
	private Long id;
	private String title;
	private String content;
	private int businessType;
	private String ownerId;
	private Date time;
	private int status;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getBusinessType() {
		return businessType;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@JsonFormat(pattern="yyyyMMddHHmmss",timezone="GMT+8")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
