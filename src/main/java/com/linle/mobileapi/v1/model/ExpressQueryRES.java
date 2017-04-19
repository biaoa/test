package com.linle.mobileapi.v1.model;

import java.util.List;

public class ExpressQueryRES {
	
	private String number;
	
	private String type;
	
	private String issign;
	
	private String deliverystatus;
	
	private List<ExpressVO> list;
	
	public ExpressQueryRES(){
		
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIssign() {
		return issign;
	}

	public void setIssign(String issign) {
		this.issign = issign;
	}

	public List<ExpressVO> getList() {
		return list;
	}

	public void setList(List<ExpressVO> list) {
		this.list = list;
	}

	public String getDeliverystatus() {
		return deliverystatus;
	}

	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}
}
