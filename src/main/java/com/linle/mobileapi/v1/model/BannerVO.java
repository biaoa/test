package com.linle.mobileapi.v1.model;

public class BannerVO {
	private Long id;
	
	private String img;
	
	private String title;
	
	private int hasH5;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHasH5() {
		return hasH5;
	}

	public void setHasH5(int hasH5) {
		this.hasH5 = hasH5;
	}
}
