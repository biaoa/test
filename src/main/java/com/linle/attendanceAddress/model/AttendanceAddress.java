package com.linle.attendanceAddress.model;

import com.linle.entity.sys.BaseDomain;

public class AttendanceAddress extends BaseDomain{

    private static final long serialVersionUID = -8949549580954258917L;

	private Long templateId;

    private String address;

    private String lat;

    private String lng;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}