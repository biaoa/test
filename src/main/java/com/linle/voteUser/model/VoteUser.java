package com.linle.voteUser.model;

import java.util.Date;

import com.linle.entity.sys.Users;

public class VoteUser {
    private Long id;

    private Long themeId;

    private Long optionsId;

    private Long userId;

    private Date createDate;

    private long optionsCount;
    
    private String optionsContent;
    
    private String createDateStr;
    
    private Users user;
    
    
 	public Users getUser() {
 		return user;
 	}

 	public void setUser(Users user) {
 		this.user = user;
 	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public long getOptionsCount() {
		return optionsCount;
	}

	public void setOptionsCount(long optionsCount) {
		this.optionsCount = optionsCount;
	}

	public String getOptionsContent() {
		return optionsContent;
	}

	public void setOptionsContent(String optionsContent) {
		this.optionsContent = optionsContent;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Long getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId = optionsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}