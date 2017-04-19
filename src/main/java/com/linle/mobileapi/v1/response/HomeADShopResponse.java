package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.HomeADShopVO;

public class HomeADShopResponse extends BaseResponse {

	private static final long serialVersionUID = -1582240947523391646L;
	
	private List<HomeADShopVO> HomeADShopList;

	public List<HomeADShopVO> getHomeADShopList() {
		return HomeADShopList;
	}

	public void setHomeADShopList(List<HomeADShopVO> homeADShopList) {
		HomeADShopList = homeADShopList;
	}
}
