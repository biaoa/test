package com.linle.communityEmployee.model;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;

public class CommunityEmployeeListResponse extends BaseResponse {

	
	private List<CommunityEmployeeListVo> data;

	public List<CommunityEmployeeListVo> getData() {
		return data;
	}

	public void setData(List<CommunityEmployeeListVo> data) {
		this.data = data;
	}


}
