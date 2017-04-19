package com.linle.mobileapi.v2.response;

import com.linle.mobileapi.base.BaseResponse;

public class FileResponse extends BaseResponse {

	private static final long serialVersionUID = 7921121530152103528L;
	
	private String fileParams;

	public String getFileParams() {
		return fileParams;
	}

	public void setFileParams(String fileParams) {
		this.fileParams = fileParams;
	}

}
