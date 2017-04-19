package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ProvinceVO;

public class LoadAreaResponse extends BaseResponse {

	private static final long serialVersionUID = 3238870482996076259L;
	
	private List<ProvinceVO> areaList;

	public List<ProvinceVO> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<ProvinceVO> areaList) {
		this.areaList = areaList;
	}

}
