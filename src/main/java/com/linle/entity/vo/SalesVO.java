package com.linle.entity.vo;

import java.io.Serializable;

public class SalesVO implements Serializable{
	
	private static final long serialVersionUID = -846330686467810775L;

	private String date; //日期
	
	private String turnover;//销售额
	
	private String sales;//销量

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

}
