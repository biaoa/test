package com.linle.entity.sys;

import java.util.Date;

public class Housekeeperad {
    private Long id;

    private String description;

    private String img;

    private Integer type;

    private String communityList;

    private Integer showFlag;

    private Long createUser;

    private Date createDate;

    private Date updateDate;

    private String content;
    
    private Integer hasH5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCommunityList() {
        return communityList;
    }

    public void setCommunityList(String communityList) {
        this.communityList = communityList;
    }

    public Integer getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Integer showFlag) {
        this.showFlag = showFlag;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public Integer getHasH5() {
		return hasH5;
	}

	public void setHasH5(Integer hasH5) {
		this.hasH5 = hasH5;
	}
}