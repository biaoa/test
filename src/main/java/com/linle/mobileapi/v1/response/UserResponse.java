package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UserVO;

public class UserResponse extends BaseResponse {
	
	private UserVO data;

	public UserVO getData() {
		return data;
	}

	public void setData(UserVO data) {
		this.data = data;
	}

	
	
}
