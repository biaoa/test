package com.linle.versionManager.model;

import com.linle.entity.sys.BaseDomain;

public class VersionManager extends BaseDomain{
	
    private String softwareFlag;

    private String softwareName;

    private String deviceType;

    private String minVersion;

    private String newVersion;
    
    private Integer isDel;
    
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

	public String getSoftwareFlag() {
        return softwareFlag;
    }

    public void setSoftwareFlag(String softwareFlag) {
        this.softwareFlag = softwareFlag;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}