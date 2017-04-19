package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.BroadbandFeeRES;

public class BroadbandFeeResponse extends BaseResponse{
	
	private static final long serialVersionUID = 2813600888953611579L;
	private BroadbandFeeRES data;

	public BroadbandFeeRES getData() {
		return data;
	}

	public void setData(BroadbandFeeRES data) {
		this.data = data;
	}

}
