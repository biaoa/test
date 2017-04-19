package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class TopicReplyResponse extends BaseResponse {

	
	private int unReadCount;

	public int getUnReadCount() {
		return unReadCount;
	}

	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}
	

}
