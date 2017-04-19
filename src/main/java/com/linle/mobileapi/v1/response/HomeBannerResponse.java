package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.HomeBannerVO;

public class HomeBannerResponse extends BaseResponse {

	private static final long serialVersionUID = -6078061463997043659L;
	
	private List<HomeBannerVO> bannerList;

	public List<HomeBannerVO> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<HomeBannerVO> bannerList) {
		this.bannerList = bannerList;
	}
}
