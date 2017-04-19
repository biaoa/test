package com.linle.entity.sys;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysOrder extends BaseDomain{

	private static final long serialVersionUID = -3852348231736912077L;

	private String orderNo;

    private String type;

    private String details;

    private BigDecimal totalMoney;
    /**
     * 状态: 0待处理 1待付款 2待收获 3待评价 4已完成 5已关闭6已受理
     */
    private Integer orderStatus;

    private Long userId;

    private String businessType;

    private Long businessId;

    private String businessName;

    private String remark;
    
    private String buyer;
    
    private String buyerPhone; //收货人电话 
    
    private String buyerAddress;//收货人地址
    
    private String buyerMessage; //购买人留言
    
    private BigDecimal preferentialAmount;//优惠金额
    
    private int sendDate; //送达时间
    
    private BigDecimal deliveryFee; //配送费
    
    private String chargeId;//ping++支付chargeid
    
    private int delFlag; //订单删除状态
    
    private List<OrderDetail> detailList;
    
    private Long communityId;//小区ID
    
    private Date payDate;//支付时间
    
    private Date lastRefundDate; //允许最晚退款时间
    
    private Date beginDate; //预约开始时间

    private Date endDate; //预约结束时间
    
    private int single; //商家单号
    
    private String channel;
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    /**
     * 状态: 0待处理 1待付款 2待收获 3待评价 4已完成 5已关闭6已受理
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }
    /**
     * 状态: 0待处理 1待付款 2待收获 3待评价 4已完成 5已关闭6已受理
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public BigDecimal getPreferentialAmount() {
		return preferentialAmount;
	}

	public void setPreferentialAmount(BigDecimal preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}

	public int getSendDate() {
		return sendDate;
	}

	public void setSendDate(int sendDate) {
		this.sendDate = sendDate;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public List<OrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getLastRefundDate() {
		return lastRefundDate;
	}

	public void setLastRefundDate(Date lastRefundDate) {
		this.lastRefundDate = lastRefundDate;
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

	public int getSingle() {
		return single;
	}

	public void setSingle(int single) {
		this.single = single;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}