package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ShopItem;

public class HomeADResponse extends BaseResponse {

	private static final long serialVersionUID = 8730362092184181734L;
	
	private List<ShopItem> data;

	public List<ShopItem> getData() {
		return data;
	}

	public void setData(List<ShopItem> data) {
		this.data = data;
	}

}
