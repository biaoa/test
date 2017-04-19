package com.linle.user.mapper;

import com.linle.entity.sys.LoginToken;

/**
 * @描述:
 * @作者:杨立忠
 * @创建时间：2015年8月13日
 **/
public interface UserLoginTokenMapper {
	
	
	public Integer add(LoginToken token);
	
	public Integer delByToken(String token);
	
	public LoginToken getByToken(String token);

	public Integer  delByUserId(Long userId);

	public Integer updatePassword(LoginToken token);

	public String getSidByUserId(Long uid);
	
}
