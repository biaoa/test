package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RefundOrderDetailVO {
	
	private String refundType; //退款类型
	
	private String description;//退款描述
	
	private Date createDate;//申请时间
	
	private List<String> imgs;//图片列表
	private List<String> thumImages;
	
	private String failReason; //失败原因
	
	private Date operateDate; //操作时间
	
	private int status;//退款订单状态
	
	private BigDecimal totalMoney;//退款金额
	
	private int refundTime;//允许多少小时内退款
	
	private String shopName;//商家名称

	public List<String> getThumImages() {
		return thumImages;
	}

	public void setThumImages(List<String> thumImages) {
		this.thumImages = thumImages;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(int refundTime) {
		this.refundTime = refundTime;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
