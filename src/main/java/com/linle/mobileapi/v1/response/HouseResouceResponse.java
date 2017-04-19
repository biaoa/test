package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.HouseResouceVO;

public class HouseResouceResponse extends BaseResponse {

	private static final long serialVersionUID = 2269927383012350766L;
	
	private List<HouseResouceVO> data;

	public List<HouseResouceVO> getData() {
		return data;
	}

	public void setData(List<HouseResouceVO> data) {
		this.data = data;
	}

}
