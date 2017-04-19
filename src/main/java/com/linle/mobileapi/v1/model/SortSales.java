package com.linle.mobileapi.v1.model;

/**
 * 
 * @author pangd
 * @Description 各大类目下的 销量model
 */
public class SortSales {
	
	private int sortId;
	
	private String sortname;
	
	private int sortSales;

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public int getSortSales() {
		return sortSales;
	}

	public void setSortSales(int sortSales) {
		this.sortSales = sortSales;
	}
}
