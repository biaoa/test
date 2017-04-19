package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.LittleKnowledgeVO;


public class LittleKnowledgeResponse extends BaseResponse {
	private static final long serialVersionUID = 8560389019754977828L;
	
	private List<LittleKnowledgeVO> data;

	public List<LittleKnowledgeVO> getData() {
		return data;
	}

	public void setData(List<LittleKnowledgeVO> data) {
		this.data = data;
	}
}
