package com.linle.mobileapi.v1.model;

import java.io.Serializable;

public class ShopVO implements Serializable {

	private static final long serialVersionUID = 7714371976396824480L;
	
	private Long id;
	
	private String shopName;
	
	private String logo;
	
	private int monthSales;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(int monthSales) {
		this.monthSales = monthSales;
	}

}
