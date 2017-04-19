package com.linle.attendanceTemplate.model;

import com.linle.entity.sys.BaseDomain;

/**
 * 
 * @author pangd
 * @Description 考勤规则表
 * @date 2016年8月11日下午2:57:23
 */
public class AttendanceTemplate extends BaseDomain {

	private static final long serialVersionUID = -3115347696725644928L;

	private String name;

	private String uids;

	private String workDays;

	private String onDuty;

	private String offDuty;

	private Integer remind;

	private String address;

	private Long communityId;

	private Long createUser;
	
	private Integer delFlg;
	
	private String showDate;
	
	private String specialdateList;
	
	private Integer type; //模板类型 0 默认 1 用户自定义
	
	private String addressList; //考勤地址集合
	
	private Integer distance;//多少米内考勤有效

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	public String getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(String onDuty) {
		this.onDuty = onDuty;
	}

	public String getOffDuty() {
		return offDuty;
	}

	public void setOffDuty(String offDuty) {
		this.offDuty = offDuty;
	}

	public Integer getRemind() {
		return remind;
	}

	public void setRemind(Integer remind) {
		this.remind = remind;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getSpecialdateList() {
		return specialdateList;
	}

	public void setSpecialdateList(String specialdateList) {
		this.specialdateList = specialdateList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAddressList() {
		return addressList;
	}

	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}
}