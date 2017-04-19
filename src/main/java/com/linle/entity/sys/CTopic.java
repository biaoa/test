package com.linle.entity.sys;

import java.util.Date;
import java.util.List;

import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;

public class CTopic {
    private Long topicId;

    private Long userId;//创建者

    private String topicContent;//话题内容

    private Long folderId;//图片

    private Long topicTypeId;//话题类型id

    private Long supportNum;//点赞数

    private Long commentNum;//评论数

    private String price;//价格

    private Date createTime;

    private Integer isTop;
    
    private int isDel;
    
    private String topicTypeName;
    
	private String communityName; //楼主地址（小区名称）
	
	private String nickName; //昵称
	
	private List<String> topicImages;//图片集合
	private SysFolder folder;
	private UserType identity;
	
	private UserStatusType status;
	
	public UserStatusType getStatus() {
		return status;
	}

	public void setStatus(UserStatusType status) {
		this.status = status;
	}

	private int reportCount;
	
	
    public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

	public SysFolder getFolder() {
		return folder;
	}

	public void setFolder(SysFolder folder) {
		this.folder = folder;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getTopicTypeName() {
		return topicTypeName;
	}

	public void setTopicTypeName(String topicTypeName) {
		this.topicTypeName = topicTypeName;
	}

	public UserType getIdentity() {
		return identity;
	}

	public void setIdentity(UserType identity) {
		this.identity = identity;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<String> getTopicImages() {
		return topicImages;
	}

	public void setTopicImages(List<String> topicImages) {
		this.topicImages = topicImages;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getTopicTypeId() {
        return topicTypeId;
    }

    public void setTopicTypeId(Long topicTypeId) {
        this.topicTypeId = topicTypeId;
    }

    public Long getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Long supportNum) {
        this.supportNum = supportNum;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}