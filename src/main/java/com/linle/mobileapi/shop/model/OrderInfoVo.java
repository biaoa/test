package com.linle.mobileapi.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author pangd
 * @Description 商家订单对象
 * @date 2016年7月26日下午4:52:36
 */
public class OrderInfoVo implements Serializable {

	private static final long serialVersionUID = 4636892659112295621L;
	
	private String orderNo;
	
	private Date createDate;
	
	private int orderStatus;
	
	private String buyersName;//买家姓名
	
	private String buyersAddress;//买家地址
	
	private String buyersPhone;//买家电话
	
	private String buyersRemark;//买家备注
	
	private BigDecimal preferentialAmount;//优惠金额
	
	private BigDecimal deliveryAmount;//配送费
	
	private BigDecimal totalAmount;//实付款
	
	private String refundType;//退款类型
	
	private String refundDescription;//退款描述
	
	private String failReason;//商家拒绝退款原因
	
	private Integer refundStatus;//退款状态
	
	private int single;//商家单号
	
	private List<ShopOrderDetailVO> productList; //商品集合
	
	private Date beginDate;
	
	private Date endDate;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBuyersName() {
		return buyersName;
	}

	public void setBuyersName(String buyersName) {
		this.buyersName = buyersName;
	}

	public String getBuyersAddress() {
		return buyersAddress;
	}

	public void setBuyersAddress(String buyersAddress) {
		this.buyersAddress = buyersAddress;
	}

	public String getBuyersRemark() {
		return buyersRemark;
	}

	public void setBuyersRemark(String buyersRemark) {
		this.buyersRemark = buyersRemark;
	}

	public BigDecimal getPreferentialAmount() {
		return preferentialAmount;
	}

	public void setPreferentialAmount(BigDecimal preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}

	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundDescription() {
		return refundDescription;
	}

	public void setRefundDescription(String refundDescription) {
		this.refundDescription = refundDescription;
	}

	public String getBuyersPhone() {
		return buyersPhone;
	}

	public void setBuyersPhone(String buyersPhone) {
		this.buyersPhone = buyersPhone;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public List<ShopOrderDetailVO> getProductList() {
		return productList;
	}

	public void setProductList(List<ShopOrderDetailVO> productList) {
		this.productList = productList;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public int getSingle() {
		return single;
	}

	public void setSingle(int single) {
		this.single = single;
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
	
}
