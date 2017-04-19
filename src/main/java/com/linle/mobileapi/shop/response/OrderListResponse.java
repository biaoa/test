package com.linle.mobileapi.shop.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.shop.model.OrderInfoVo;

/**
 * 
 * @author pangd
 * @Description 商家端订单列表响应
 * @date 2016年7月26日下午6:26:05
 */
public class OrderListResponse extends BaseResponse {

	private static final long serialVersionUID = -480562961266983365L;
	
	private List<OrderInfoVo> data;

	public List<OrderInfoVo> getData() {
		return data;
	}

	public void setData(List<OrderInfoVo> data) {
		this.data = data;
	}
}
