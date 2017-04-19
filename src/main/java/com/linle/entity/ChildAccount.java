package com.linle.entity;

import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.BaseDomain;
import com.linle.entity.sys.Users;

/**
 * @描述:子帐号
 * @作者:杨立忠
 * @创建时间：2015年8月25日
 **/
public class ChildAccount extends BaseDomain{
	
	/**
	 * 绑定账号
	 */
	private Users user;
	/**
	 * 业务类型id
	 */
	private Long objId;
	/**
	 * 业务类型
	 */
	private UserType roleType;
	/**
	 * 用户姓名
	 */
	private String userName;
	private String phone;
	/**
	 * 职务
	 */
	private String position;
	
	private UserStatusType status;
	
	/**
	 * 状态 删除
	 */
	private UserStatusType delFlag;
	
	
	public UserStatusType getStatus() {
		return status;
	}
	public void setStatus(UserStatusType status) {
		this.status = status;
	}
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
	}
	public UserType getRoleType() {
		return roleType;
	}
	public void setRoleType(UserType roleType) {
		this.roleType = roleType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UserStatusType getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(UserStatusType delFlag) {
		this.delFlag = delFlag;
	}
	
}
