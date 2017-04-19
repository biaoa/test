package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.entity.sys.RepairType;
import com.linle.mobileapi.base.BaseResponse;

public class RepairTypeResponse extends BaseResponse{

	private static final long serialVersionUID = 3246461383056599230L;
	
	private List<RepairType> data;

	public List<RepairType> getData() {
		return data;
	}

	public void setData(List<RepairType> data) {
		this.data = data;
	}


}
