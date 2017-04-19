package com.linle.mobileapi.v1.request;

import com.linle.mobileapi.base.BaseRequest;

public class ParkingStopRequest extends BaseRequest {

	private static final long serialVersionUID = 2730809383994117417L;
	
	private Long spaceRecordId;

	public Long getSpaceRecordId() {
		return spaceRecordId;
	}

	public void setSpaceRecordId(Long spaceRecordId) {
		this.spaceRecordId = spaceRecordId;
	}

}
