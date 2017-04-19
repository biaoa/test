package com.linle.attendanceRecordDetail.model;

import java.util.Date;

import com.linle.entity.sys.BaseDomain;

public class AttendanceRecordDetail extends BaseDomain{

    private static final long serialVersionUID = 4422700853459704601L;

	private Long mainId;

    private Date dutyDate;

    private String address;//打卡位置

    private String description;

    private Long folderId;

    private Integer status;

    private Integer type;
    
    private String attendanceAddress; //考勤地址

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getAttendanceAddress() {
		return attendanceAddress;
	}

	public void setAttendanceAddress(String attendanceAddress) {
		this.attendanceAddress = attendanceAddress;
	}
}