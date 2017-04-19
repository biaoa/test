package com.linle.utilitiesChild.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UtilitiesChild implements Serializable {
    private Long id;
    private long refId;
    private Long communityId;

    private String houseOwner;

    private String houseNumber;

    private String phone;

    private Float lastMeterReading;

    private Float thisMeterReading;

    private Float actualConsumption;

    private BigDecimal price;

    private BigDecimal pooledPrice;

    private BigDecimal balance;

    private BigDecimal payable;

    private Date meterReadingDate;

    private Integer year;

    private Integer month;

    private Integer status;

    private String orderNo;

    private Integer type;

    private Integer invoiceStatus;

    private String remark;

    private Date createDate;

    private Date updateDate;
    private BigDecimal amount;
    private BigDecimal lastBalance;
    private BigDecimal actualAmount;
    
    
    public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(BigDecimal lastBalance) {
		this.lastBalance = lastBalance;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public BigDecimal getPooledPrice() {
        return pooledPrice;
    }

    public void setPooledPrice(BigDecimal pooledPrice) {
        this.pooledPrice = pooledPrice;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Date getMeterReadingDate() {
        return meterReadingDate;
    }

    public void setMeterReadingDate(Date meterReadingDate) {
        this.meterReadingDate = meterReadingDate;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}