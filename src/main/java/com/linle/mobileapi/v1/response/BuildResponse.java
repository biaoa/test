package com.linle.mobileapi.v1.response;

import java.util.List;

public class BuildResponse  {
	
	private String buildName;
	
	private List<UnitResponse> unitList;
	
	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public List<UnitResponse> getUnitList() {
		return unitList;
	}

	public void setUnitList(List<UnitResponse> unitList) {
		this.unitList = unitList;
	}
}
