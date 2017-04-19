package com.linle.mobileapi.v1.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 商品列表请求
 */
public class CommodityListRequest extends BaseRequest {

	private static final long serialVersionUID = 1674995661796763464L;
	
	@NotEmpty(message="店铺ID不能为空")
	private Long shopId;
	
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
}
