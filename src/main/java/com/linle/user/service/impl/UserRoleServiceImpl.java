package com.linle.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.user.mapper.UserRoleMapper;
import com.linle.user.service.UserRoleService;


@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl extends  CommonServiceAdpter<UserRole>  implements UserRoleService{
	
	@Autowired
	private UserRoleMapper mapper;

	@Override
	public Integer addUserRoleRelation(UserRoleRelation userRoleRelation) {
		return mapper.addUserRoleRelation(userRoleRelation);
	}

	@Override
	public List<UserRole> loadUserRoleRelationByUserId(Long userId) {
		return mapper.loadUserRoleRelationByUserId(userId);
	}

	@Override
	public Boolean addRole(UserRole role) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users user = (Users) subject.getSession().getAttribute("cUser");
			role.setSourceType(user.getIdentity());
			role.setSourceId(user.getId());
		}
		return mapper.insertSelective(role) > 0;
	}

	@Override
	public Page<UserRole> findRolesForPage(Page<UserRole> page) {
		 page.setResults(mapper.getAllData(page));
		 return page;
	}

	@Override
	public Integer delRoleById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public UserRole getRoleByename(String ename) {
		return mapper.getRoleByename(ename);
	}

	@Override
	public UserRole getRoleById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserRole> findAllRoleList(Map<String, Object> params) {
		return mapper.getAllRoleList(params);
	}

	@Override
	public boolean checkRoleExsit(String ename) {
		return mapper.checkRoleExsit(ename)>0;
	}

}
