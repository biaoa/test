package com.linle.mobileapi.shop.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 商家状态修改请求
 * @date 2016年7月25日下午3:19:06
 */
public class OperateStatusRequest extends BaseRequest {
	
	private static final long serialVersionUID = 8728914317244098445L;
	
	@NotNull(message="参数错误")
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
