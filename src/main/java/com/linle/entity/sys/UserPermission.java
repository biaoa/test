package com.linle.entity.sys;
/** 
 * @author  杨立忠 
 * @version V1.0 
 * @创建时间：2015-8-1 上午09:57:53 
 */
public class UserPermission extends BaseDomain{
	/**
	 * 用户权限
	 */
	private static final long serialVersionUID = 1L;
	private String ename;
	private String cname;
	private String permissionType;
	private String parentId;
	private String parentIds;
	private String available;
	private String remark;
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
