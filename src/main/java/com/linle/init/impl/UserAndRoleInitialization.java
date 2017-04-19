package com.linle.init.impl;

import com.linle.common.util.ApplicationContextFactory;
import com.linle.common.util.ReaderPermissionConfig;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.RolePermissionRelation;
import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.init.Initialization;
import com.linle.user.service.RolePermissionService;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserRoleService;


public class UserAndRoleInitialization implements Initialization {
	private UserInfoService userInfoService;
	private UserRoleService roleService;
	private RolePermissionService rolePermissionService;

	public UserAndRoleInitialization() {
		userInfoService = (UserInfoService) ApplicationContextFactory
				.getInstance().getBean("userInfoService");
		roleService = (UserRoleService) ApplicationContextFactory.getInstance()
				.getBean("userRoleService");
		rolePermissionService =  (RolePermissionService) ApplicationContextFactory.getInstance()
		.getBean("rolePermissionService");
	}

	@Override
	public void init() throws Exception {
		Users user = createUser();		
		userInfoService.addUserInfo(user);
		UserRole role = createRole();
		roleService.addRole(role);
		createRolePermissionRelation(role);
		UserRoleRelation userRoleRelation = new UserRoleRelation();
		userRoleRelation.setUser(user);
		userRoleRelation.setUserRole(role);
		roleService.addUserRoleRelation(userRoleRelation);
	}
	
	private Users createUser(){
		Users userInfo = new Users();
		userInfo.setUserName("admin");
		userInfo.setPassword("admin");
		userInfo.setMobilePhone("13735433492");
		userInfo.setIdentity(UserType.SYS);
		userInfo.setStatus(UserStatusType.normal);
		return userInfo;
	}
	
	private UserRole createRole(){
		UserRole role = new UserRole();
		role.setAvailable(true);
		role.setCname("系统管理员");
		role.setEname("sys:admin");	
		return role;
	}
	
	private void createRolePermissionRelation(UserRole role){
		String[] enames =  ReaderPermissionConfig.getPermissionENames();
		for (String ename : enames) {
			RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
			rolePermissionRelation.setUserRole(role);
			rolePermissionRelation.setPermissionEname(ename);
			rolePermissionService.addRolePermissionRelation(rolePermissionRelation);
		}
	}
	
}
