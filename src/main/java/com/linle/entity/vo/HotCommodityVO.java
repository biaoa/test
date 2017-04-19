package com.linle.entity.vo;

import java.math.BigDecimal;

/**
 * 
 * @author pangd
 * @Description 商家首页 热销产品VO
 */
public class HotCommodityVO {
	
	private Long id;
	
	private String logo;
	
	private String name;
	
	private BigDecimal price;
	
	private int sales;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

}
