package com.linle.entity.sys;


import java.util.Date;

public class RolePermissionRelation {
	private String permissionEname;
	private UserRole userRole;
	private Date createDate;

	public String getPermissionEname() {
		return permissionEname;
	}
	public void setPermissionEname(String permissionEname) {
		this.permissionEname = permissionEname;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
