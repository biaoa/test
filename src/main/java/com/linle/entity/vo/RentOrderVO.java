package com.linle.entity.vo;

import java.util.Date;

public class RentOrderVO {
	
	private Long id;
	
	private String orderNo; //订单号
	
	private String addresseeName; //收件人
	
	private String addresseePhone; //收件人电话
	
	private String addresseeAddress; //收件地址
	
	private String expressName; //快递名称
	
	private Date beginDate; //预约开始时间
	
	private Date endDate; //预约结束时间
	
	private Date createDate; //申请时间
	
	private String senderName; //寄件人
	
	private String overall;//寄件人房号
	
	private String remark;//备注
	
	private int orderStatus;//订单状态
	
	private int sentStatus;
	
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAddresseeName() {
		return addresseeName;
	}

	public void setAddresseeName(String addresseeName) {
		this.addresseeName = addresseeName;
	}

	public String getAddresseeAddress() {
		return addresseeAddress;
	}

	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddresseePhone() {
		return addresseePhone;
	}

	public void setAddresseePhone(String addresseePhone) {
		this.addresseePhone = addresseePhone;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(int sentStatus) {
		this.sentStatus = sentStatus;
	}


}
