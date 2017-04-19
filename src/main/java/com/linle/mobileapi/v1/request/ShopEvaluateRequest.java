package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class ShopEvaluateRequest extends BaseRequest {

	private static final long serialVersionUID = -3430918043332529171L;
	
	private Long shopId;
	
	private Float commodityStar;//商品评分
	
	private Float serviceStar;//服务评分
	
	private Float sendStar;//配送评分
	
	private Integer pageSize;
	
	private Integer pageNumber;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Float getCommodityStar() {
		return commodityStar;
	}

	public void setCommodityStar(Float commodityStar) {
		this.commodityStar = commodityStar;
	}

	public Float getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(Float serviceStar) {
		this.serviceStar = serviceStar;
	}

	public Float getSendStar() {
		return sendStar;
	}

	public void setSendStar(Float sendStar) {
		this.sendStar = sendStar;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	
}
