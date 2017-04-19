package com.linle.shiro;


import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;

public class UserAccountInfo extends SimpleAuthenticationInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7188519148161217412L;
	private Long userId;

	public UserAccountInfo(Long userId, Object principal,
			Object hashedCredentials, ByteSource credentialsSalt,
			String realmName) {
		super(principal, hashedCredentials, credentialsSalt, realmName);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
