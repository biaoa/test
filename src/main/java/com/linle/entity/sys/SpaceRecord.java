package com.linle.entity.sys;

import java.util.Date;

public class SpaceRecord extends BaseDomain{

	private static final long serialVersionUID = 5296219987115506559L;

	private Long userId;

    private String orderNo;

    private Long communityId;

    private String type;

    private Date beginDate;

    private Date endDate;

    private Integer status;

    private Long grageId;

    private String spaceNo;

    private String spaceInfo;
    
    private ParkingSpace parkingSpace;//车位分布表
    
    private int surplusDays;//剩余天数
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGrageId() {
        return grageId;
    }

    public void setGrageId(Long grageId) {
        this.grageId = grageId;
    }

    public String getSpaceNo() {
        return spaceNo;
    }

    public void setSpaceNo(String spaceNo) {
        this.spaceNo = spaceNo;
    }

    public String getSpaceInfo() {
        return spaceInfo;
    }

    public void setSpaceInfo(String spaceInfo) {
        this.spaceInfo = spaceInfo;
    }

	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public int getSurplusDays() {
		return surplusDays;
	}

	public void setSurplusDays(int surplusDays) {
		this.surplusDays = surplusDays;
	}

}