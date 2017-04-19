package com.linle.user.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.util.DateUtil;
import com.linle.entity.sys.LoginToken;
import com.linle.entity.sys.Users;
import com.linle.user.mapper.UserLoginTokenMapper;
import com.linle.user.service.LoginTokenService;
import com.linle.user.service.UserInfoService;

@Service
@Transactional
public class LoginTokenServiceImpl implements LoginTokenService{

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserLoginTokenMapper loginTokenMapper;
	@Override
	public String addToken(String userName) {
		Users user = userInfoService
				.findUserInfoByUserName(userName);
		loginTokenMapper.delByUserId(user.getId());
		LoginToken loginToken = new LoginToken();
		loginToken.setUser(user);
		loginToken.setUserName(userName);
		loginToken.setPassword(user.getPassword());
		loginToken.setExpireTime(DateUtil.addMonth(new Date(), 3));
		String token = UUID.randomUUID().toString();
		loginToken.setToken(token);
		loginTokenMapper.add(loginToken);
		return token;
	}

	@Override
	public boolean delTokenByToken(String token) {
		return loginTokenMapper.delByToken(token)>0;
	}

	@Override
	public LoginToken getByToken(String token) {
		return loginTokenMapper.getByToken(token);
	}

	@Override
	public boolean delTokenByUserId(Long userId) {
		return loginTokenMapper.delByUserId(userId)>0;
	}

	@Override
	public boolean updatePassword(LoginToken token) {
		return loginTokenMapper.updatePassword(token)>0;
	}

	@Override
	public String getSidByUserId(Long uid) {
		return loginTokenMapper.getSidByUserId(uid);
	}

}
