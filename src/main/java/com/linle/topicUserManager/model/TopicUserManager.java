package com.linle.topicUserManager.model;

import java.io.Serializable;
import java.util.Date;

public class TopicUserManager implements Serializable{
    private static final long serialVersionUID = -143613221591579298L;

	private Long id;

    private Long userId;

    private Integer isPublish; //是否能发布话题

    private Integer isReply; //是否能回复话题

    private Long topicTypeId; //发布对应话题类型
    private Long commTopicTypeId; //评论对应话题类型
    
    private Integer isDel; //是否删除
    private Date createTime; //创建时间
    
    private String topicTypeName;
    private String commTopicTypeName;
    
 	private String nickName; //昵称
 	
 	
    public String getCommTopicTypeName() {
		return commTopicTypeName;
	}

	public void setCommTopicTypeName(String commTopicTypeName) {
		this.commTopicTypeName = commTopicTypeName;
	}

	public Long getCommTopicTypeId() {
		return commTopicTypeId;
	}

	public void setCommTopicTypeId(Long commTopicTypeId) {
		this.commTopicTypeId = commTopicTypeId;
	}

	public String getTopicTypeName() {
		return topicTypeName;
	}

	public void setTopicTypeName(String topicTypeName) {
		this.topicTypeName = topicTypeName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public Integer getIsReply() {
        return isReply;
    }

    public void setIsReply(Integer isReply) {
        this.isReply = isReply;
    }

    public Long getTopicTypeId() {
        return topicTypeId;
    }

    public void setTopicTypeId(Long topicTypeId) {
        this.topicTypeId = topicTypeId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}