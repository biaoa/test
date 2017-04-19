package com.linle.mobileapi.v1.model;

import java.io.Serializable;

public class HomeADShopVO implements Serializable {

	private static final long serialVersionUID = 7240191221172103287L;
	
	private Long shopId;
	
	private String img;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
