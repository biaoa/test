package com.linle.mobileapi.v1.response;

import com.linle.mobileapi.base.BaseResponse;

public class VersionResponse extends BaseResponse {

	private static final long serialVersionUID = 1697998928353587909L;

	private int isUpdate;
    
	private String newVersion;

	private String url;
	
	private String content;
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
	
}
