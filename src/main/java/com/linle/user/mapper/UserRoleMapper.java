package com.linle.user.mapper;


import java.util.List;
import java.util.Map;

import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.UserRoleRelation;

import component.BaseMapper;


/** 
 * @author  杨立忠 
 * @version V1.0 
 * @创建时间：2015-8-1 上午09:51:53 
 */
public interface UserRoleMapper extends BaseMapper<UserRole>{
	public Integer addUserRoleRelation(UserRoleRelation userRoleRelation);
	public List<UserRole> loadUserRoleRelationByUserId(Long userId);
	public UserRole getRoleByename(String ename);
	public List<UserRole> getAllRoleList(Map<String, Object> params);
	public Integer checkRoleExsit(String ename);
}
