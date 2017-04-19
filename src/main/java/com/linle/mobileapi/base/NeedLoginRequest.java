package com.linle.mobileapi.base;

import javax.validation.constraints.NotNull;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月28日
 **/
public class NeedLoginRequest extends BaseRequest{
	@NotNull(message="sid不能为空")
	private String sid;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
}
