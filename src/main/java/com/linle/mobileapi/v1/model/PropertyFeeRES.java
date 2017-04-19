package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;

/**
 * 
* @ClassName: PropertyFeeVO 
* @Description: APP response vo 
* @author pangd
* @date 2016年3月24日 下午3:52:57 
*
 */
public class PropertyFeeRES {
	
	 private String communityName; //小区名
		
	 private String houseNumber; //房号
	 
	 private float acreage;// 面积
	 
	 private BigDecimal price;//单价
	 
	 private BigDecimal payable; //应缴
	 
	 private String feeTime; //费用时间
	 
	 private String orderNo;
	 
	 private int status; //1 未交 2已交 

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public float getAcreage() {
		return acreage;
	}

	public void setAcreage(float acreage) {
		this.acreage = acreage;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPayable() {
		return payable;
	}

	public void setPayable(BigDecimal payable) {
		this.payable = payable;
	}

	public String getFeeTime() {
		return feeTime;
	}

	public void setFeeTime(String feeTime) {
		this.feeTime = feeTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	 
}
