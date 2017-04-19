package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.ExpressListVO;

public class ExpressListResponse extends BaseResponse {

	private static final long serialVersionUID = 2131263953351333004L;
	
	private List<ExpressListVO> data;

	public List<ExpressListVO> getData() {
		return data;
	}

	public void setData(List<ExpressListVO> data) {
		this.data = data;
	}
}
