package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UtilitiesVO;

public class UtilitiesResponse extends BaseResponse {

	private static final long serialVersionUID = 2771828112111901361L;
	
	private UtilitiesVO data;

	public UtilitiesVO getData() {
		return data;
	}

	public void setData(UtilitiesVO data) {
		this.data = data;
	}

}
