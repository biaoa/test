package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.OrderVo;

public class OrderResponse extends BaseResponse {

	private static final long serialVersionUID = 3222406484110901047L;
	
	private List<OrderVo> data;

	public List<OrderVo> getData() {
		return data;
	}

	public void setData(List<OrderVo> data) {
		this.data = data;
	}
}
