package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ShopInfoVO;

/**
 * 
 * @author pangd
 * @Description 商家信息响应信息
 */
public class ShopInfoResponse extends BaseResponse {

	private static final long serialVersionUID = -4233779733217913348L;
	
	private ShopInfoVO shopInfo;

	public ShopInfoVO getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(ShopInfoVO shopInfo) {
		this.shopInfo = shopInfo;
	}

}
