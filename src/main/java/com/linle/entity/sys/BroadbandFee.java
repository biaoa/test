package com.linle.entity.sys;

import java.math.BigDecimal;
import java.util.Date;

public class BroadbandFee extends BaseDomain{

	private static final long serialVersionUID = -4911449134284517945L;

	private Long communityId;

    private String houseOwner;

    private String houseNumber;

    private BigDecimal payable;

    private Integer year;

    private Integer month;

    private Integer status;

    private Integer type;

    private String orderNo;

    private String detail;

    private String feeTime;
    private String broadbandJson;
    
    private BigDecimal sumQuantity;
    private BigDecimal waitQuantity;
    
    private Date payDate; //支付时间 两种情况 1:线上支付的时间  2:下线缴费的操作时间
    
    public BigDecimal getSumQuantity() {
		return sumQuantity;
	}

	public void setSumQuantity(BigDecimal sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	public BigDecimal getWaitQuantity() {
		return waitQuantity;
	}

	public void setWaitQuantity(BigDecimal waitQuantity) {
		this.waitQuantity = waitQuantity;
	}
    public String getBroadbandJson() {
		return broadbandJson;
	}

	public void setBroadbandJson(String broadbandJson) {
		this.broadbandJson = broadbandJson;
	}

	public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(String feeTime) {
        this.feeTime = feeTime;
    }

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}