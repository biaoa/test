package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

/**  
 * @Title: GraduateResponse.java
 * @Package com.linle.mobileapi.v1.response
 * @author shangjing
 * @date 2015年8月18日下午1:55:59
 * @comment 请求毕业响应参数
 */
public class GraduateResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private Integer status; // 是否能毕业
	
	private Integer graduateCode; //毕业状态
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGraduateCode() {
		return graduateCode;
	}

	public void setGraduateCode(Integer graduateCode) {
		this.graduateCode = graduateCode;
	}


}
