package com.linle.mobileapi.v1.model;

import java.util.List;

// 商品分类 API
public class GoodsSort {
	private int id;
	
	private String sort;
	
	private List<ShopGoods> goodsList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<ShopGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<ShopGoods> goodsList) {
		this.goodsList = goodsList;
	}
}
