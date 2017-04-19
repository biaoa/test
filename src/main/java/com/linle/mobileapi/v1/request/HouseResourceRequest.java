package com.linle.mobileapi.v1.request;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.mobileapi.base.BaseRequest;

public class HouseResourceRequest extends BaseRequest{

	private static final long serialVersionUID = 7832270061770496284L;
	
	/**
	 *房屋信息描述
	 */
	private String content;
	
	/**
	 * 类型ID
	 */
	private Integer type;
	
	private BigDecimal price;
	
	private	List<CommonsMultipartFile> list;
	
	private Long id;
	
	private  Long folderId;
	

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<CommonsMultipartFile> getList() {
		return list;
	}

	public void setList(List<CommonsMultipartFile> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
