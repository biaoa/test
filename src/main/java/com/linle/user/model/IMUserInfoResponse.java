package com.linle.user.model;

import com.linle.mobileapi.base.BaseResponse;

public class IMUserInfoResponse extends BaseResponse {
	private static final long serialVersionUID = -7500393724268257126L;
	private CommunityUserIM data;

	public CommunityUserIM getData() {
		return data;
	}

	public void setData(CommunityUserIM data) {
		this.data = data;
	}
}
