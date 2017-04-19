package com.linle.communitySuggestionsComment.model;

import java.io.Serializable;
import java.util.Date;

public class CommunitySuggestionsComment implements Serializable{
    private Long id;

    private Long suggestionsId;

    private Long userId;

    private String content;

    private Date createTime;

    private Integer isDel;

    private String userName;
    
    private String userImg;
    
    public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
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

    public Long getSuggestionsId() {
        return suggestionsId;
    }

    public void setSuggestionsId(Long suggestionsId) {
        this.suggestionsId = suggestionsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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