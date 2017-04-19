package com.linle.user.service;

import com.linle.entity.sys.LoginToken;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月14日
 **/
public interface LoginTokenService {
	
	public String addToken(String userName);
	
	public boolean delTokenByToken(String token);
	
	public boolean delTokenByUserId(Long userId);
	
	public LoginToken getByToken(String token);

	public boolean updatePassword(LoginToken token);

	public String getSidByUserId(Long uid);
	
	
}
