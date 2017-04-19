package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.RepairVo;

public class RepairListResponse extends BaseResponse {

	private static final long serialVersionUID = -8243184842673940324L;
	
	private List<RepairVo> data;

	public List<RepairVo> getData() {
		return data;
	}

	public void setData(List<RepairVo> data) {
		this.data = data;
	}

}
