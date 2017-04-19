package com.linle.mobileapi.v1.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.linle.mobileapi.base.BaseRequest;

/**
 * 
 * @author chenk
 * @Description 检查是否更新版本
 */
public class VersionRequest extends BaseRequest {
	
	@NotEmpty(message="软件标识不能为空")
	private String softwareFlag;
	
	@NotEmpty(message="当前版本号不能为空")
	private String currVersion;

	@NotEmpty(message="设备类型不能为空")
	private String deviceType;
	
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSoftwareFlag() {
		return softwareFlag;
	}

	public void setSoftwareFlag(String softwareFlag) {
		this.softwareFlag = softwareFlag;
	}

	public String getCurrVersion() {
		return currVersion;
	}

	public void setCurrVersion(String currVersion) {
		this.currVersion = currVersion;
	}

	
}
