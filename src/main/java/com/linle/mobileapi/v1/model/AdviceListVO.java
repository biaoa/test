package com.linle.mobileapi.v1.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @Description 反馈建议列表返回
 */
public class AdviceListVO {
	private long id;
	
	private Date createDate;
	
	private String advice;
	
	private String newsAdvice;//最新消息
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNewsAdvice() {
		return newsAdvice;
	}

	public void setNewsAdvice(String newsAdvice) {
		this.newsAdvice = newsAdvice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

}
