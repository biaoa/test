package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.v1.model.RongTokenVO;

/**
 * 
* @ClassName: RongTokenResponse 
* @Description: 融云token 完整响应信息
* @author pangd
* @date 2016年3月31日 下午8:14:23 
*
 */
public class RongTokenResponse {
	private String code;
	
	private RongTokenVO result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public RongTokenVO getResult() {
		return result;
	}

	public void setResult(RongTokenVO result) {
		this.result = result;
	}
}
