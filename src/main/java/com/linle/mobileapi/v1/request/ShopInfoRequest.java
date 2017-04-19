package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 商家信息请求信息
 */
public class ShopInfoRequest extends BaseRequest {

	private static final long serialVersionUID = 6968969572839406521L;
	
	private Long shopId;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
}
