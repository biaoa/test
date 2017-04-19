package com.linle.user.service;


import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.UserRoleRelation;

public interface UserRoleService extends BaseService<UserRole>{
	public Boolean addRole(UserRole role);
	public Integer addUserRoleRelation(UserRoleRelation userRoleRelation);
	public List<UserRole> loadUserRoleRelationByUserId(Long userId);
	
	public Page<UserRole> findRolesForPage(Page<UserRole> page);
	/**
	 * 根据id获取权限
	 * @param ename
	 * @return
	 */
	public UserRole getRoleById(Long id);
	
	/**
	 * 根据id删除角色
	 * @param id
	 * @return
	 */
	public Integer delRoleById(Long id);
	/**
	 * 根据ename获取权限
	 * @param ename
	 * @return
	 */
	public UserRole getRoleByename(String ename);
	/**
	 * 根據條件查詢角色列表
	 * @param params
	 * @return
	 */
	public List<UserRole> findAllRoleList(Map<String, Object> params);
	
	/**
	 * 检查权限是否已经存在
	 * @param ename
	 * @return
	 */
	public boolean checkRoleExsit(String ename);
	
}
