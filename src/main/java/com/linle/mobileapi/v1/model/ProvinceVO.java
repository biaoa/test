package com.linle.mobileapi.v1.model;

import java.util.List;

public class ProvinceVO {
	
	private Long id;
		
	private String name;
	
	private List<Area> cityList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Area> getCityList() {
		return cityList;
	}

	public void setCityList(List<Area> cityList) {
		this.cityList = cityList;
	}
}
