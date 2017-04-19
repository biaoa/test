package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ShopEvaluate;

public class ShopEvaluateResponse extends BaseResponse {

	private static final long serialVersionUID = 4311664871348644957L;
	
	private ShopEvaluate shopEvaluate;

	public ShopEvaluate getShopEvaluate() {
		return shopEvaluate;
	}

	public void setShopEvaluate(ShopEvaluate shopEvaluate) {
		this.shopEvaluate = shopEvaluate;
	}

}
