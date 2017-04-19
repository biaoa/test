package com.linle.entity.sys;

import java.math.BigDecimal;
import java.util.Date;

import com.linle.utilitiesChild.model.UtilitiesChild;
/**
 * 
* @ClassName: Utilities 
* @Description: 水电费
* @author pangd
* @date 2016年3月22日 下午1:19:19 
*
 */
public class Utilities extends BaseDomain{

	private static final long serialVersionUID = -7961739627528652969L;

	private Long communityId;

    private String houseOwner;

    private String houseNumber;

    private String phone;

    private Float lastMeterReading;

    private Float thisMeterReading;

    private Float actualConsumption;
    private Float  newThisMeterReading;
    private BigDecimal price;

    private BigDecimal pooledPrice;

    private BigDecimal balance;

    private BigDecimal payable;

    private Date meterReadingDate;

    private Integer year;

    private Integer month;
    
    private Integer status; // 0 全部  1 未交 2 已交
    
    private String orderNo;
    
    private Integer type; // 类型 1 水费  2 电费

    private String utilitiesJson;
    
    private BigDecimal sumQuantity;
    private BigDecimal waitQuantity;
    
    private int invoiceStatus;
    
    private String remark;
    
    private UtilitiesChild utilitiesChild;
    
    private long refId;
    
    private BigDecimal amount;
    private BigDecimal lastBalance;
    private BigDecimal actualAmount;
    
    private Date payDate; //缴费时间 两种情况 1:线上缴费的支付时间 2:线下缴费的操作时间
    
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

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public Float getNewThisMeterReading() {
		return newThisMeterReading;
	}

	public void setNewThisMeterReading(Float newThisMeterReading) {
		this.newThisMeterReading = newThisMeterReading;
	}

	public UtilitiesChild getUtilitiesChild() {
		return utilitiesChild;
	}

	public void setUtilitiesChild(UtilitiesChild utilitiesChild) {
		this.utilitiesChild = utilitiesChild;
	}

	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getUtilitiesJson() {
		return utilitiesJson;
	}

	public void setUtilitiesJson(String utilitiesJson) {
		this.utilitiesJson = utilitiesJson;
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

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
}