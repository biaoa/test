package com.linle.disableUser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.disableUser.mapper.DisableUserMapper;
import com.linle.disableUser.model.DisableUser;
import com.linle.disableUser.service.DisableUserService;
@Service
@Transactional
public class DisableUserServiceImpl extends CommonServiceAdpter<DisableUser> implements DisableUserService{

	@Autowired
	private DisableUserMapper mapper;
	
	@Override
	public void deleteByUserId(long userid){
		mapper.deleteByUserId(userid);
	}
}
