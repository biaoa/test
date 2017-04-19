package com.linle.mobileapi.v1.model;

import java.math.BigDecimal;

public class EvaluateHeadVo {
	
	
	private String storeimage;//商店图片
	
	private String storename;//商店名称
	
	private Float grade;//店铺综合评分
	
	private int sales;//月销量
	
	private BigDecimal startmoney;//起送价
	
	private BigDecimal freight;//配送费

	public String getStoreimage() {
		return storeimage;
	}

	public void setStoreimage(String storeimage) {
		this.storeimage = storeimage;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public BigDecimal getStartmoney() {
		return startmoney;
	}

	public void setStartmoney(BigDecimal startmoney) {
		this.startmoney = startmoney;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
}
