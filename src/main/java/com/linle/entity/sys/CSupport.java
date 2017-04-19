package com.linle.entity.sys;

import java.util.Date;

public class CSupport {
    private Long id;

    private Long topicId;//话题id

    private Long userId;//创建者

    private Boolean isSupport;//是否点赞

    private Date createTime;
    
    private int isDel;
    
    private String userName;
    
    private String userNameImg;
    
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameImg() {
		return userNameImg;
	}

	public void setUserNameImg(String userNameImg) {
		this.userNameImg = userNameImg;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

    public Boolean getIsSupport() {
        return isSupport;
    }

    public void setIsSupport(Boolean isSupport) {
        this.isSupport = isSupport;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}