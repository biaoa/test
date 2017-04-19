package com.linle.mobileapi.property.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.property.model.PatrolRoomRecordListVo;

public class PatrolRoomRecordListResponse extends BaseResponse {

	
	private List<PatrolRoomRecordListVo> data;

	public List<PatrolRoomRecordListVo> getData() {
		return data;
	}

	public void setData(List<PatrolRoomRecordListVo> data) {
		this.data = data;
	}


}
