package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import com.linle.mobileapi.base.BaseRequest;

public class FeedbackRequest extends BaseRequest {

	private static final long serialVersionUID = -3541817920111037427L;
	
	@NotNull(message="反馈内容不能为空")
	private String content;
	
	@NotNull(message="手机信息不能为空")
	private String mobileInfo;
	
	@NotNull(message="系统版本不能为空")
	private String sysVersion;
	
	@NotNull(message="设备类型不能为空")
	private String type;
	
	@NotNull(message="手机系统版本信息不能为空")
	private String mobileVersion;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobileInfo() {
		return mobileInfo;
	}

	public void setMobileInfo(String mobileInfo) {
		this.mobileInfo = mobileInfo;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobileVersion() {
		return mobileVersion;
	}

	public void setMobileVersion(String mobileVersion) {
		this.mobileVersion = mobileVersion;
	}
	

}
