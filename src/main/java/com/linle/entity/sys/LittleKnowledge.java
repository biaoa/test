package com.linle.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author pangd
 * @Description app 邻乐速报
 */
public class LittleKnowledge extends BaseDomain{

	private static final long serialVersionUID = 1249751467790382162L;

	private String imgUrl;

    private String title;

    private String description;

    private Integer delFlag;


    private Long userId;

    private String content;

    private Integer orderNo;//排序
    
    @JsonIgnore
    private Integer thumbCount; //点赞数量
    
    @JsonIgnore
    private Integer readCount;//阅读总数
    
    @JsonIgnore
    private Integer distinguishUserCount;//按用户区分统计
    
    public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getDistinguishUserCount() {
		return distinguishUserCount;
	}

	public void setDistinguishUserCount(Integer distinguishUserCount) {
		this.distinguishUserCount = distinguishUserCount;
	}

	public Integer getThumbCount() {
		return thumbCount;
	}

	public void setThumbCount(Integer thumbCount) {
		this.thumbCount = thumbCount;
	}
}