package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.PropertyFeeRES;

public class PropertyFeeResponse extends BaseResponse {
	private static final long serialVersionUID = 8287528117406327657L;
	
	private PropertyFeeRES data;

	public PropertyFeeRES getData() {
		return data;
	}

	public void setData(PropertyFeeRES data) {
		this.data = data;
	}
	
}
