package com.linle.user.service;


import java.util.List;

import com.linle.entity.sys.RolePermissionRelation;

/** 
 * @author  杨立忠 
 * @version V1.0 
 * @创建时间：2015-8-1 上午11:27:14 
 */
public interface RolePermissionService {
	/**
	 * 批量新增角色权限
	 * @param enameStr
	 * @return
	 */
	public boolean addRolePermissions(Long roleId, String enameStr);
	public Integer addRolePermissionRelation(RolePermissionRelation rolePermissionRelation);
	public List<RolePermissionRelation> loadRolePermissionRelationByRoleId(Long roleId);
}
