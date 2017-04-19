package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;
/**
 * 
* @ClassName: BroadbandFeeRES 
* @Description: 宽带费，有线电视返回信息
* @author pangd
* @date 2016年3月25日 下午4:28:00 
*
 */
public class BroadbandFeeRES {
	
	private String	orderNo; //订单号
	
	private String communityName; // 小区名

	private String houseNumber; // 房号

	private BigDecimal payable; // 应缴

	private String feeTime; // 费用时间
	
	private int status; //1 未交 2 已交

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
