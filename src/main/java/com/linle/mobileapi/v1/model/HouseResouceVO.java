package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class HouseResouceVO {
	
	private Long id;
	
	private String content;
	
	private BigDecimal price;
	
	private Date createDate;
	
	private List<String> images;

	private List<String> thumImages;
	
	public List<String> getThumImages() {
		return thumImages;
	}

	public void setThumImages(List<String> thumImages) {
		this.thumImages = thumImages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
