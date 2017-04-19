package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;

public class UtilitiesVO {
	
	 private String communityName; //小区名
	
	 private String houseNumber; //房号
	 
	 private Float lastMeterReading; //上月

	 private Float thisMeterReading;//本月
	 
	 private Float actualConsumption; //实用
	
	 private BigDecimal price; //单价
	 
	 private BigDecimal payable; //应缴
	 
	 private String orderNo;
	 
	 private int status; //状态 1未交 2已交

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

	public Float getLastMeterReading() {
		return lastMeterReading;
	}

	public void setLastMeterReading(Float lastMeterReading) {
		this.lastMeterReading = lastMeterReading;
	}

	public Float getThisMeterReading() {
		return thisMeterReading;
	}

	public void setThisMeterReading(Float thisMeterReading) {
		this.thisMeterReading = thisMeterReading;
	}

	public Float getActualConsumption() {
		return actualConsumption;
	}

	public void setActualConsumption(Float actualConsumption) {
		this.actualConsumption = actualConsumption;
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
