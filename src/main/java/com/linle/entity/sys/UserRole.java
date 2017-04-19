package com.linle.entity.sys;

import com.linle.entity.enumType.UserType;

public class UserRole extends BaseDomain{
	/**
	 * 用户角色
	 */
	private static final long serialVersionUID = 1L;
	private String cname;
	private String ename;
	private boolean available = true;
	private String remark;
	private String category;
	private Long sourceId;
	private UserType sourceType;
	
	

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public UserType getSourceType() {
		return sourceType;
	}
	public void setSourceType(UserType sourceType) {
		this.sourceType = sourceType;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}

	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
