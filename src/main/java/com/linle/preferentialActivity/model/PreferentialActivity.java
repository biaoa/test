package com.linle.preferentialActivity.model;

import java.math.BigDecimal;
import java.util.Date;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 优惠活动
 * @date 2016年9月20日下午5:52:16
 */
public class PreferentialActivity extends BaseDomain {

	private static final long serialVersionUID = 1079141729747475622L;

	private String activityName;

	private Integer type;

	private String activityCommunity;

	private Date beginDate;

	private Date endDate;

	private String beginDateStr;
	private String endDateStr;
	private BigDecimal activityAmount;

	private Integer status;

	private String scope;

	private Date createDate;

	private Integer repeatFlag;

	private Long createUser;

	private String typeNames;
	
	public String getTypeNames() {
		return typeNames;
	}

	public void setTypeNames(String typeNames) {
		this.typeNames = typeNames;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getActivityCommunity() {
		return activityCommunity;
	}

	public void setActivityCommunity(String activityCommunity) {
		this.activityCommunity = activityCommunity;
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

	public BigDecimal getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(BigDecimal activityAmount) {
		this.activityAmount = activityAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getRepeatFlag() {
		return repeatFlag;
	}

	public void setRepeatFlag(Integer repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}