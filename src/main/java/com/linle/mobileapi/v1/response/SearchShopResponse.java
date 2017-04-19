package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ShopItem;

/**
 * 
 * @author pangd
 * @Description 搜索店铺返回结果
 */
public class SearchShopResponse extends BaseResponse {

	private static final long serialVersionUID = 3000339439977166837L;
	
	private List<ShopItem>	shopList;

	public List<ShopItem> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopItem> shopList) {
		this.shopList = shopList;
	}
}
