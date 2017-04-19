package com.linle.mobileapi.v1.model;

import java.util.Date;
import java.util.List;

public class RepairVo {
	
	private Long id ;
	
	private String content;//内容
	
	private String typeName;//报修类型
	
	private String beginDate;
	
	private String endDate;
	
	private String status;//状态
	
	private Date createDate;
	
	private List<String> images; //图片集合
	
	private List<String> thumImages; //图片集合
	

	public List<String> getThumImages() {
		return thumImages;
	}

	public void setThumImages(List<String> thumImages) {
		this.thumImages = thumImages;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
