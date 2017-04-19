package com.linle.user.service.impl;

import java.util.List;
import java.util.Random;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.Page;
import com.linle.entity.ChildAccount;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.UserRoleRelation;
import com.linle.entity.sys.Users;
import com.linle.user.mapper.ChildAccountMapper;
import com.linle.user.model.ChildAccountInfo;
import com.linle.user.service.ChildAccountService;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserRoleService;

/**
 * @描述:子帐号业务类
 * @作者:杨立忠
 * @创建时间：2015年8月25日
 **/
@Service
@Transactional
public class ChildAccountServiceImpl implements ChildAccountService{

	@Autowired
	private ChildAccountMapper mapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserRoleService roleService;
	
	
	@Override
	public Boolean addChildAccount(ChildAccountInfo childAccountInfo, Users u, Long orgId) {
		Users user = new Users();
		user.setUserName(childAccountInfo.getUserName());
		user.setIdentity(u.getIdentity());
		user.setName(childAccountInfo.getName());
		user.setStatus(UserStatusType.normal);
		user.setPassword(childAccountInfo.getPassword());
		if(userInfoService.addUserInfo(user)){
			UserRoleRelation userRoleRelation = new UserRoleRelation();
			userRoleRelation.setUser(user);
			userRoleRelation.setUserRole(roleService.getRoleById(childAccountInfo.getRoleId()));
			roleService.addUserRoleRelation(userRoleRelation);
			//子帐号新增
			ChildAccount childAccount = new ChildAccount();
			childAccount.setPhone(childAccountInfo.getPhone());
			childAccount.setPosition(childAccountInfo.getPosition());
			childAccount.setUserName(childAccountInfo.getName());
			childAccount.setRoleType(u.getIdentity());
			childAccount.setObjId(orgId);
			childAccount.setUser(user);
			childAccount.setStatus(UserStatusType.normal);
			return mapper.insertSelective(childAccount)>0;
		}
		return false;	
		
	}

	@Override
	public Boolean updateChildAccount(ChildAccount childAccount) {
		return mapper.updateByPrimaryKeySelective(childAccount)>0;
	}

	@Override
	public Boolean delChildAccount(Long childAccount) {
		return mapper.deleteByPrimaryKey(childAccount)>0;
	}

	@Override
	public List<ChildAccount> findAllAccount(Page<ChildAccount> page) {
		return mapper.getChildAccount(page);
	}

	@Override
	public ChildAccount getByUserId(Long id) {
		return mapper.getByUserId(id);
	}

}
