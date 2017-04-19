package com.linle.attendanceRecordMain.model;

import com.linle.entity.sys.BaseDomain;

public class AttendanceRecordMain extends BaseDomain{

    private static final long serialVersionUID = 2461341352677608069L;

	private Long uid;

    private Long templateId;

    private Integer status;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}