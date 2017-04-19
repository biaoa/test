package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UserInfoVO;

public class UserInfoResponse extends BaseResponse {

	private static final long serialVersionUID = -1491837663919039713L;
	
	private UserInfoVO data;

	public UserInfoVO getData() {
		return data;
	}

	public void setData(UserInfoVO data) {
		this.data = data;
	}

}
