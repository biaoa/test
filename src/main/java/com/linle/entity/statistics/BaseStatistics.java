package com.linle.entity.statistics;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseStatistics implements Serializable{
	private String name;
	
	private int count;

	private BigDecimal amount;
	
	private String categories;//类别
	
	private String data;//
	
	private String data2;//
	
	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
