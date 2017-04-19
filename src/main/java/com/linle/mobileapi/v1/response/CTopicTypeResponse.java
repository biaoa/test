package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.entity.sys.CTopicType;
import com.linle.mobileapi.base.BaseResponse;

public class CTopicTypeResponse extends BaseResponse{

	private static final long serialVersionUID = 3246461383056599230L;
	
	private List<CTopicType> data;

	public List<CTopicType> getData() {
		return data;
	}

	public void setData(List<CTopicType> data) {
		this.data = data;
	}


}
