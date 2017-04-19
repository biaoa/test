package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;

/**
 * 
 * @author pangd
 * @Description 商铺优惠model
 */
public class ShopPrivilege {
	
	private int privilegesType;//优惠类型
	private String id;
	private BigDecimal bestPrice;//满多少	
	private BigDecimal cutPrice;//减多少
	public int getPrivilegesType() {
		return privilegesType;
	}
	public void setPrivilegesType(int privilegesType) {
		this.privilegesType = privilegesType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(BigDecimal bestPrice) {
		this.bestPrice = bestPrice;
	}
	public BigDecimal getCutPrice() {
		return cutPrice;
	}
	public void setCutPrice(BigDecimal cutPrice) {
		this.cutPrice = cutPrice;
	}
}
