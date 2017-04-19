package com.linle.entity.sys;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
* @ClassName: PropertyFee 
* @Description: 物业费
* @author pangd
* @date 2016年3月24日 下午2:13:46 
*
 */
public class PropertyFee  extends BaseDomain{

	private static final long serialVersionUID = -6193785920840556387L;

	private String houseOwner;

    private String houseNumber;

    private Float houseAcreage;

    private BigDecimal price;

    private BigDecimal payable;

    private String orderNo;

    private Integer status;

    private String feeTime;

    private Integer year;

    private Integer month;

    private Long communityId;

    private String propertyJson;
    
    private BigDecimal sumQuantity;
    private BigDecimal waitQuantity;
    
    private Date payDate; //支付时间 两种情况 1:线上缴费的支付时间 2 线下缴费的操作时间
    
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
	public String getPropertyJson() {
		return propertyJson;
	}

	public void setPropertyJson(String propertyJson) {
		this.propertyJson = propertyJson;
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

    public Float getHouseAcreage() {
        return houseAcreage;
    }

    public void setHouseAcreage(Float houseAcreage) {
        this.houseAcreage = houseAcreage;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(String feeTime) {
        this.feeTime = feeTime;
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
}