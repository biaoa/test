package com.linle.mobileapi.v1.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.model.DealAddressVo;
import com.linle.mobileapi.v1.model.OrderProduct;
/**
 * 
 * @author pangd
 * @Description 订单详情模板2
 */
public class OrderDetailVo2 {
		
	private String orderNo;//订单编号
	
	private int status; //订单状态
	
	private Date createDate; //创建时间
	
	private BigDecimal totalMoney; //总价
	
	private String type;//订单类型
	
	private String businessName; //商家名称

	private List<OrderProduct> productList; //商品集合
	
	private String remark; //买家备注
	
	private DealAddressVo buyersAddress;//买家地址
	
	private DealAddressVo sellerAddress;//卖家地址
	
	@JsonIgnore
	private String businessId;//业务ID
	
	@JsonIgnore
	private String buyerAddress;
	
	@JsonIgnore
	private String buyerPhone;
	
	@JsonIgnore
	private Users user;
	
	private BigDecimal preferentialAmount;//优惠金额
	
	private BigDecimal deliveryAmount;//配送费
	
	private BigDecimal communityAmount;//商品总价
	
	private Long payEndDate; //支付剩余时间 (毫秒)
	
	private String refundTime;//商家允许多少小时内可以退款

	private int activityFlag;
	
	public int getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(int activityFlag) {
		this.activityFlag = activityFlag;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DealAddressVo getBuyersAddress() {
		return buyersAddress;
	}

	public void setBuyersAddress(DealAddressVo buyersAddress) {
		this.buyersAddress = buyersAddress;
	}

	public DealAddressVo getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(DealAddressVo sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public BigDecimal getCommunityAmount() {
		return communityAmount;
	}

	public void setCommunityAmount(BigDecimal communityAmount) {
		this.communityAmount = communityAmount;
	}

	public Long getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Long payEndDate) {
		this.payEndDate = payEndDate;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	
}	
