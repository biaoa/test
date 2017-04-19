package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.RefundOrderDetailVO;

public class RefundOrderDetailResponse extends BaseResponse {

	private static final long serialVersionUID = -8143201227916989653L;
	
	private RefundOrderDetailVO data;

	public RefundOrderDetailVO getData() {
		return data;
	}

	public void setData(RefundOrderDetailVO data) {
		this.data = data;
	}
}
