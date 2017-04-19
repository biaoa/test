package com.linle.topicReport.model;

import java.util.Date;

public class CTopicReport {
    private Long id;

    private Long topicId;

    private Long userId;

    private Boolean isReport;

    private Date createTime;

    private Integer isDel;

    private String userNameImg;
    
    private String userName;
    
    
    public String getUserNameImg() {
		return userNameImg;
	}

	public void setUserNameImg(String userNameImg) {
		this.userNameImg = userNameImg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsReport() {
        return isReport;
    }

    public void setIsReport(Boolean isReport) {
        this.isReport = isReport;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}