package com.linle.entity.sys;

import java.util.Date;

public class RepairRecord {
    private Long id;

    private String single;

    private Long communityId;

    private Users user; //小区居民信息

    private String content;

    private Date beginDate;

    private Date endDate;

    private Integer status;

    private SysFolder folder;

    private Date createDate;

    private Date updateDate;
    
    private RepairType repairType;

    private String userName;
    private String mobilePhone;
    private String overall;
    
    private Integer parentTypeId;
    private Integer repairPattern;
    
    public Integer getRepairPattern() {
		return repairPattern;
	}

	public void setRepairPattern(Integer repairPattern) {
		this.repairPattern = repairPattern;
	}

	public Integer getParentTypeId() {
		return parentTypeId;
	}

	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public RepairType getRepairType() {
		return repairType;
	}

	public void setRepairType(RepairType repairType) {
		this.repairType = repairType;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public SysFolder getFolder() {
		return folder;
	}

	public void setFolder(SysFolder folder) {
		this.folder = folder;
	}
}