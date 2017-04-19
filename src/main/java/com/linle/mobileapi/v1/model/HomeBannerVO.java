package com.linle.mobileapi.v1.model;

import java.io.Serializable;

public class HomeBannerVO implements Serializable{

	private static final long serialVersionUID = -1371118931269467097L;
	
	private String img;
	
	private String url;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
