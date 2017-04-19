package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.BaseEntity;

public class RoomCommonResponse extends BaseResponse {
	private List<BaseEntity> list;
	
	public List<BaseEntity> getList() {
		return list;
	}

	public void setList(List<BaseEntity> list) {
		this.list = list;
	}
	
}
