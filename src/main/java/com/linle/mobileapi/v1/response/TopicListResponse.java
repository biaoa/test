package com.linle.mobileapi.v1.response;

import java.util.Date;
import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CTopicVO;

public class TopicListResponse extends BaseResponse {

	private static final long serialVersionUID = -1575425978031758523L;

	private Date lastRequestDate; //最后请求时间
	
	private List<CTopicVO> data;

	public List<CTopicVO> getData() {
		return data;
	}

	public void setData(List<CTopicVO> data) {
		this.data = data;
	}

	public Date getLastRequestDate() {
		return lastRequestDate;
	}

	public void setLastRequestDate(Date lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}

}
