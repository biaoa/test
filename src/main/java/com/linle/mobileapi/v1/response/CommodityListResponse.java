package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.GetAllGoods;

/**
 * 
 * @author pangd
 * @Description 商品列表响应
 */
public class CommodityListResponse extends BaseResponse {

	private static final long serialVersionUID = 2285357622507740298L;
	
	private GetAllGoods allGoods;

	public GetAllGoods getAllGoods() {
		return allGoods;
	}

	public void setAllGoods(GetAllGoods allGoods) {
		this.allGoods = allGoods;
	}
	
}
