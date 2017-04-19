package com.linle.appBanner.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author pangd
 * @Description 轮播图统计
 * @date 2016年8月31日上午9:32:35
 */
public class AppBannerStatisticalVo implements Serializable{
	
	private static final long serialVersionUID = 2772684706227339693L;

	private Long id;
	
	private String description;
	
	private int status;
	
	private Date createDate;
	
	private int registereCount;//注册用户点击数
	
	private int unRegistereCount;//非注册用户点击数

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getRegistereCount() {
		return registereCount;
	}

	public void setRegistereCount(int registereCount) {
		this.registereCount = registereCount;
	}

	public int getUnRegistereCount() {
		return unRegistereCount;
	}

	public void setUnRegistereCount(int unRegistereCount) {
		this.unRegistereCount = unRegistereCount;
	}
}
