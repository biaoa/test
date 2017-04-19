package com.linle.cTopicReadRecord.model;

import java.util.Date;

import com.linle.entity.sys.BaseDomain;

public class CTopicReadRecord extends BaseDomain{

    private static final long serialVersionUID = 6128616516449005527L;

	private Long userId;

    private Long typeId;

    private Date lastRequestDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Date getLastRequestDate() {
        return lastRequestDate;
    }

    public void setLastRequestDate(Date lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }
}