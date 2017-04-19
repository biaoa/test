package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.AdviceListVO;

/**
 * 
 * @author pangd
 * @Description 意见反馈响应结果
 * @date 2016年8月4日下午3:36:58
 */
public class GetAdviceResponse extends BaseResponse {

	private static final long serialVersionUID = 6998315159419253713L;
	
	private List<AdviceListVO> data;

	public List<AdviceListVO> getData() {
		return data;
	}

	public void setData(List<AdviceListVO> data) {
		this.data = data;
	}
}
