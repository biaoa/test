package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.BannerVO;



public class BannerResponse extends BaseResponse {

	private static final long serialVersionUID = -5890604015629252130L;
	
	private List<BannerVO> data;

	public List<BannerVO> getData() {
		return data;
	}

	public void setData(List<BannerVO> data) {
		this.data = data;
	}
}
