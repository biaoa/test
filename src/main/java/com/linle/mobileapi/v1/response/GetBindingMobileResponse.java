package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年9月9日
 **/
public class GetBindingMobileResponse extends BaseResponse{
	private String mobilePhone;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}
