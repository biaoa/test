package com.linle.mobileapi.v1.request;

import java.util.List;

import com.linle.mobileapi.v1.model.OrderProduct;
import com.linle.mobileapi.v1.model.OrderVo;

public class OrderDetailVo extends OrderVo {
	private String businessName;
	
	private Long shopId;

	private List<OrderProduct> productList;
	
	private int shopRefundTime;//商家允许退款时间

	private int activityFlag;//是否参与活动标识
	  
	public int getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(int activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<OrderProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderProduct> productList) {
		this.productList = productList;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public int getShopRefundTime() {
		return shopRefundTime;
	}

	public void setShopRefundTime(int shopRefundTime) {
		this.shopRefundTime = shopRefundTime;
	}
}
