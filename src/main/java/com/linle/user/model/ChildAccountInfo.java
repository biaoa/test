package com.linle.user.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月26日
 **/
public class ChildAccountInfo {
	
	@NotEmpty(message="账号不能为空")
	private String userName;
	@NotEmpty(message="联系电话不能空")
	private String phone;
	@NotEmpty(message="职位不能为空")
	private String position;
	@NotEmpty(message="姓名不能为空")
	private String name;
	@NotEmpty(message="密码不能为空")
	private String password;
	@NotNull(message="权限")
	private Long roleId;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
