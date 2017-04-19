package com.linle.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;
import com.linle.shiro.UserAccountInfo;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.sys.RolePermissionRelation;
import com.linle.entity.sys.UserRole;
import com.linle.entity.sys.Users;
import com.linle.user.service.RolePermissionService;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserRoleService;



@Service("userLoginRealm")
public class UserLoginRealm extends AuthorizingRealm {


	@Autowired UserInfoService userInfoService;
	
	@Autowired UserRoleService userRoleService;
	
	@Autowired RolePermissionService rolePermissionRelationService;

	@Override
	public boolean supports(AuthenticationToken token) {
		if (super.supports(token)) {
			return token instanceof UsernamePasswordToken;
		} else {
			return false;
		}
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection collection) {
		String userName = (String) collection.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Users userInfo = userInfoService.findUserInfoByUserName(userName);
		if(userInfo!=null){
			List<UserRole> userRoles = new ArrayList<UserRole>();
			userRoles = userRoleService.loadUserRoleRelationByUserId(userInfo.getId());
			if(userRoles!=null&&userRoles.size()>0){
				for(UserRole ur : userRoles){
					authorizationInfo.addRole(ur.getEname());
					List<RolePermissionRelation> userPermissions = new ArrayList<RolePermissionRelation>();
					userPermissions = rolePermissionRelationService.loadRolePermissionRelationByRoleId(ur.getId());
					if(userPermissions!=null && userPermissions.size()>0){
						for(RolePermissionRelation up : userPermissions){
							authorizationInfo.addStringPermission(up.getPermissionEname());
						}
					}
				}
			}
		}
		return authorizationInfo;

	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UserLoginToken loginToken = (UserLoginToken) authenticationToken;	
		String userName = (String) loginToken.getPrincipal(); 
		String password = new String((char[]) loginToken.getCredentials()); 
		Users userInfo = userInfoService.findUserInfoByUserName(userName);
		
		if(userInfo==null){
			throw new UnknownAccountException("账户不存在");
		}else if(userInfo.getStatus()!=UserStatusType.normal){
			throw new LockedAccountException("账户不可用");
		}
		if(LoginMode.token == loginToken.getLoginMode()){
			return new UserAccountInfo(1L, userName, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getPassword()), getName() );
		}
		loginToken.setPassword(new Sha1Hash(password,userInfo.getSalt()).toString().toCharArray());
		return new UserAccountInfo(1L, userName, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getPassword()), getName() );
	}

}
