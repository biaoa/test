package com.linle.user.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.StringUtil;
import com.linle.entity.sys.RolePermissionRelation;
import com.linle.entity.sys.UserRole;
import com.linle.user.mapper.RolePermissionRelationMapper;
import com.linle.user.service.RolePermissionService;

/** 
 * @author  杨立忠 
 * @version V1.0 
 * @创建时间：2015-8-1 下午02:21:21 
 */
@Transactional
@Service("rolePermissionService")
public class RolePermissionServiceImpl  implements RolePermissionService{

	
	@Autowired
	private RolePermissionRelationMapper mapper;
	
	public RolePermissionRelationMapper getMapper() {
		return mapper;
	}

	@Override
	public Integer addRolePermissionRelation(
			RolePermissionRelation rolePermissionRelation) {
		return mapper.addRolePermissionRelation(rolePermissionRelation);
	}

	@Override
	public List<RolePermissionRelation> loadRolePermissionRelationByRoleId(
			Long roleId) {
		return mapper.loadRolePermissionRelationByRoleId(roleId);
	}

	@Override
	public boolean addRolePermissions(Long roleId, String enameStr) {
		UserRole userRole = new UserRole();
		userRole.setId(roleId);
		if(StringUtil.isNotNull(enameStr)){
			mapper.deleteByRoleId(roleId);
			String[] enames = enameStr.split(",");
			for (String ename : enames) {
				if(ename!=null&&!"".equals(ename)){
					RolePermissionRelation rolePermissionRelation =new RolePermissionRelation();
					rolePermissionRelation.setUserRole(userRole);
					rolePermissionRelation.setPermissionEname(ename);
					mapper.addRolePermissionRelation(rolePermissionRelation);
				}
			}
		}
		return true;
	}


}
