package com.linle.patrolRoomRecord.model;

import java.util.Date;

import com.linle.entity.sys.Users;

public class PatrolRoomRecord {
    private Long id;

    private Long uId;

    private String checkAddress;

    private String scanAddress;

    private Integer status;

    private String remark;

    private String images;

    private Date createDate;

    private Users user;
    
    private String deptName;
    
    
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getScanAddress() {
        return scanAddress;
    }

    public void setScanAddress(String scanAddress) {
        this.scanAddress = scanAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}