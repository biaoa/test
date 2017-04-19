package com.linle.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linle.entity.sys.RolePermissionRelation;


public interface RolePermissionRelationMapper {
	public int addRolePermissionRelation(RolePermissionRelation rolePermissionRelation);
	public int deleteByRoleId(@Param("roleId") Long roleId);
	public RolePermissionRelation getByRoleIdAndEname(@Param("roleId") Long roleId,@Param("ename") String ename);
	public List<RolePermissionRelation> loadRolePermissionRelationByRoleId(Long roleId);
}
