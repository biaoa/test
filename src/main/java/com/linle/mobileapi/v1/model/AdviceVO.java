package com.linle.mobileapi.v1.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author pangd
 * @Description 意见反馈返回mode
 * @date 2016年8月4日下午3:37:15
 */
public class AdviceVO {
	private Long id;
	
	private Long userId;
	
	private Date createDate;
	
	private String advice;
	
	private List<String> imgs;
	
	private List<String> thumImages;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<String> getThumImages() {
		return thumImages;
	}

	public void setThumImages(List<String> thumImages) {
		this.thumImages = thumImages;
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

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
}
