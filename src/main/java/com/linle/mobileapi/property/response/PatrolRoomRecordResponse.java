package com.linle.mobileapi.property.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.patrolRoomRecord.model.PatrolRoomRecord;

public class PatrolRoomRecordResponse extends BaseResponse {

	
	private PatrolRoomRecord data;

	public PatrolRoomRecord getData() {
		return data;
	}

	public void setData(PatrolRoomRecord data) {
		this.data = data;
	}



}
