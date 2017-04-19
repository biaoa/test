package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class SearchShopRequest extends BaseRequest {

	private static final long serialVersionUID = 6930467788119946196L;
	
	private Long sortId;//大类ID
	
	private Long childSortId;//子分类ID
	
	private int type;//优惠类型ID
	
	private String search;//模糊搜索 目前的条件有 商家名，商品名
	
	private Integer pageSize;
	
	private Integer pageNumber;
	
	private int sortRules; // 0 默认排序 1 销量最高 2 配送速度最快 3 评分最高

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public Long getChildSortId() {
		return childSortId;
	}

	public void setChildSortId(Long childSortId) {
		this.childSortId = childSortId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSortRules() {
		return sortRules;
	}

	public void setSortRules(int sortRules) {
		this.sortRules = sortRules;
	}

}
