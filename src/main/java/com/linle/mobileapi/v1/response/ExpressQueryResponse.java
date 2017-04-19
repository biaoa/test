package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ExpressQueryResult;

public class ExpressQueryResponse extends BaseResponse{

	private static final long serialVersionUID = -1564149841524725196L;
	
	private ExpressQueryResult data;

	public ExpressQueryResult getData() {
		return data;
	}

	public void setData(ExpressQueryResult data) {
		this.data = data;
	}


}
