package com.linle.mobileapi.v1.response;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.AppMenuVO;

public class AppMenuResponse extends BaseResponse {

	private static final long serialVersionUID = 2679189109358327320L;
	
	private List<AppMenuVO> data;

	public List<AppMenuVO> getData() {
		return data;
	}

	public void setData(List<AppMenuVO> data) {
		this.data = data;
	} 

}
