package com.linle.entity.sys;

import java.io.Serializable;

public class CTopicType implements Serializable{
    private Long topicTypeId;

	private String topicTypeName;//话题类型名称

	private Integer orderNo;//排序

	private Integer communityPrivg;//是否查看所有小区 1：查看所有小区  0:查看当前小区

	private String remark;

	private int isDel;
	
	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Long getTopicTypeId() {
		return topicTypeId;
	}

	public void setTopicTypeId(Long topicTypeId) {
		this.topicTypeId = topicTypeId;
	}

	public String getTopicTypeName() {
		return topicTypeName;
	}

	public void setTopicTypeName(String topicTypeName) {
		this.topicTypeName = topicTypeName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCommunityPrivg() {
		return communityPrivg;
	}

	public void setCommunityPrivg(Integer communityPrivg) {
		this.communityPrivg = communityPrivg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}