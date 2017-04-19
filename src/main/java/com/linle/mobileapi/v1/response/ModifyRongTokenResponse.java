package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.RongResVO;

public class ModifyRongTokenResponse extends BaseResponse {

	private static final long serialVersionUID = -6816155792116360609L;
	
	private RongResVO data;

	public RongResVO getData() {
		return data;
	}

	public void setData(RongResVO data) {
		this.data = data;
	}
}
