package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author pangd
 * @Description 宽带/有限电视请求
 */
public class BroadbandFeeRequest extends BaseRequest {

	private static final long serialVersionUID = 4069356018674616338L;
	
	@NotNull(message="类型不能为空")
	private Integer type; // 1 宽带 2 有线电视

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
