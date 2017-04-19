package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class VerResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private String maxver;
	
	private String minver;
	
	private String updateTime;
	
	private String size;

	public String getMaxver() {
		return maxver;
	}

	public void setMaxver(String maxver) {
		this.maxver = maxver;
	}

	public String getMinver() {
		return minver;
	}

	public void setMinver(String minver) {
		this.minver = minver;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}
