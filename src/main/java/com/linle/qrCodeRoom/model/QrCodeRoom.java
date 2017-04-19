package com.linle.qrCodeRoom.model;

import java.util.Date;

public class QrCodeRoom {
    private Long id;

    private Long communityId;

    private String building;

    private String unit;

    private String qrCodeName;

    private String path;

    private Date createDate;
    
    private String  fileName;
    
    
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQrCodeName() {
        return qrCodeName;
    }

    public void setQrCodeName(String qrCodeName) {
        this.qrCodeName = qrCodeName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}