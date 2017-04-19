package com.linle.entity.sys;


import java.util.Date;

/** 
 * @author  杨立忠 
 * @version V1.0 
 * @创建时间：2014-4-1 上午10:32:03 
 */
public class UserRoleRelation {
	private Users user;
	private UserRole userRole;
	private Date createDate;

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
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
