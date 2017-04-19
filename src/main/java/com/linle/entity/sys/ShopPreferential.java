package com.linle.entity.sys;

import java.math.BigDecimal;

/**
 * 
 * @author pangd
 * @Description 商铺优惠
 */
public class ShopPreferential extends BaseDomain{

	private static final long serialVersionUID = 2997166224020734161L;

	private Long shopId;

//    private Long typeId;
	
	private PreferentialType type;

    private BigDecimal meetPrice;

    private BigDecimal preferentialPrice;

    private Integer status;


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getMeetPrice() {
        return meetPrice;
    }

    public void setMeetPrice(BigDecimal meetPrice) {
        this.meetPrice = meetPrice;
    }

    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public PreferentialType getType() {
		return type;
	}

	public void setType(PreferentialType type) {
		this.type = type;
	}
}