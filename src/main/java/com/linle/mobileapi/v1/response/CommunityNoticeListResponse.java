package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CommunityNoticeVO;


public class CommunityNoticeListResponse extends BaseResponse {
	private static final long serialVersionUID = 8560389019754977828L;
	
	private List<CommunityNoticeVO> data;

	public List<CommunityNoticeVO> getData() {
		return data;
	}

	public void setData(List<CommunityNoticeVO> data) {
		this.data = data;
	}
}
