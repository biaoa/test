package com.linle.mobileapi.v1.model;

//商品信息 API
public class ShopGoods {
	private Long id;
	
	private int sort;
	
	private String url;
	
	private String goodName;
	
	private String goodPrice;
	
	private int  goodSales;
	
	private int goodNums;
	
	private int buyNums;
	
	private String introduction;//商品描述
	
	private int genre;
	
	private String serverScope;
	
	private String serverDetail;

	private int activityFlag;
	private int activityEnableNums;//商品活动可购买次数
	

	public int getActivityEnableNums() {
		return activityEnableNums;
	}

	public void setActivityEnableNums(int activityEnableNums) {
		this.activityEnableNums = activityEnableNums;
	}

	public int getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(int activityFlag) {
		this.activityFlag = activityFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}

	public int getGoodSales() {
		return goodSales;
	}

	public void setGoodSales(int goodSales) {
		this.goodSales = goodSales;
	}

	public int getGoodNums() {
		return goodNums;
	}

	public void setGoodNums(int goodNums) {
		this.goodNums = goodNums;
	}

	public int getBuyNums() {
		return buyNums;
	}

	public void setBuyNums(int buyNums) {
		this.buyNums = buyNums;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getGenre() {
		return genre;
	}

	public void setGenre(int genre) {
		this.genre = genre;
	}

	public String getServerScope() {
		return serverScope;
	}

	public void setServerScope(String serverScope) {
		this.serverScope = serverScope;
	}

	public String getServerDetail() {
		return serverDetail;
	}

	public void setServerDetail(String serverDetail) {
		this.serverDetail = serverDetail;
	}
}
