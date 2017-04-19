package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;

public class LoadRoomResponse extends BaseResponse {
	private static final long serialVersionUID = -8484101790093590557L;
	
	private List<BuildResponse> buildList;

	public List<BuildResponse> getBuildList() {
		return buildList;
	}

	public void setBuildList(List<BuildResponse> buildList) {
		this.buildList = buildList;
	}
}
