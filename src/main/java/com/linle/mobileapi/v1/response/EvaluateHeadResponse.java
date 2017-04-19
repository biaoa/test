package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.EvaluateHeadVo;

public class EvaluateHeadResponse extends BaseResponse {

	private static final long serialVersionUID = 2089601485039999163L;
	
	private EvaluateHeadVo head;

	public EvaluateHeadVo getHead() {
		return head;
	}

	public void setHead(EvaluateHeadVo head) {
		this.head = head;
	}
}
