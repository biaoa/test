package com.linle.mobileapi.property.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityEmployee.service.CommunityEmployeeService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseMobileapiController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.property.request.PropertyLoginRequest;
import com.linle.mobileapi.property.response.PropertyLoginResponse;
import com.linle.mobileapi.v1.request.ChangePasswordRequest;
import com.linle.mobileapi.v1.request.LogourRequest;
import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;

/**
 * 
 * @author chenk
 * @Description 物业移动端相关接口
 */
@Controller
@RequestMapping("/api/1/property")
public class MobilePropertyController extends BaseMobileapiController {

	@Autowired
	private CommunityEmployeeService communityEmployeeService;

	@Autowired
	private CommunityService  communityService;
	
	// 登陆
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse login(@Valid PropertyLoginRequest loginRequest, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		PropertyLoginResponse loginResponse = new PropertyLoginResponse();
		try {
			UserLoginToken loginToken = new UserLoginToken();
			loginToken.setUsername(loginRequest.getUserName());
			loginToken.setPassword(loginRequest.getPassword().toCharArray());
			Subject subject = SecurityUtils.getSubject();
			try {
				loginToken.setLoginMode(LoginMode.password);
				subject.login(loginToken);
				loginResponse.setSid(tokenService.addToken(loginRequest.getUserName()));
				Users user = userInfoService.findUserInfoByUserName(loginRequest.getUserName());
				//小区(物业负责人)，小区员工，小区部门负责人
				if (!user.getIdentity().equals(UserType.XQ)&&!user.getIdentity().equals(UserType.XQBMFZR)&&!user.getIdentity().equals(UserType.XQPTYG)) {
					return new BaseResponse(1, "该账户不能登录邻乐物业版");
				}
				// 修改最近登陆时间
				user.setLastLoginDate(new Date());
				userInfoService.setLastLoginTime(user);
				loginResponse.setUserId(user.getId());
				loginResponse.setNickName(user.getName());
				//小区员工，部门负责人
				if (user.getIdentity().equals(UserType.XQBMFZR)||user.getIdentity().equals(UserType.XQPTYG)) {
					CommunityEmployee communityEmployee = communityEmployeeService.selectByUid(user.getId());
					loginResponse.setUserLogo(communityEmployee.getLogo());
					loginResponse.setPhone(communityEmployee.getPhone());
					if(user.getIdentity().equals(UserType.XQBMFZR)){
						loginResponse.setIdentityName("负责人");//部门负责人
						loginResponse.setIdentity("XQBMFZR");
					}else{
						loginResponse.setIdentityName("普通员工");
						loginResponse.setIdentity("XQPTYG");
					}
					loginResponse.setDepartmentName(communityEmployee.getDepartment().getName());
					loginResponse.setCommunityId(communityEmployee.getCommunityId());
				}
				//小区物业负责人
				if (user.getIdentity().equals(UserType.XQ)) {
					Community community = communityService.getCommunityByuserID(user.getId());
					loginResponse.setUserLogo(community.getLogo());
					loginResponse.setPhone(community.getPhone());
					loginResponse.setIdentityName("物业负责人");
					loginResponse.setDepartmentName("自我负责");
					loginResponse.setIdentity("XQ");
					loginResponse.setCommunityId(community.getId());
				}
				
				System.out.println(m.writeValueAsString(loginResponse));
				Session session = subject.getSession();
				session.setAttribute("cUser", user);
			} catch (UnknownAccountException uae) {
				loginResponse.setCode(1);
				loginResponse.setMsg("用户名或密码错误！");
				return loginResponse;
			} catch (IncorrectCredentialsException ice) {
				loginResponse.setCode(1);
				loginResponse.setMsg("用户名或密码错误！");
				return loginResponse;
			} catch (LockedAccountException lae) {
				loginResponse.setCode(1);
				loginResponse.setMsg("账号未启用！");
				return loginResponse;
			} catch (AuthenticationException ae) {
				loginResponse.setCode(1);
				loginResponse.setMsg("登录失败！");
				return loginResponse;
			}
			return loginResponse;
		} catch (Exception e) {
			_logger.error("登录方法执行异常:" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 修改密码
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse changePassword(@Valid ChangePasswordRequest changePasswordRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				String oldPassword = new Sha1Hash(changePasswordRequest.getOldPassword(), userInfo.getSalt())
						.toString();
				if (userInfo == null || userInfo.getId() == null || !oldPassword.equals(userInfo.getPassword())) {
					return new BaseResponse(1, "旧密码输入不正确");
				}
				Users userInfoUp = new Users();
				userInfoUp.setId(userInfo.getId());
				userInfoUp.setPassword(changePasswordRequest.getNewPassword());
				userInfoService.updatePasswordAndToken(userInfoUp);
				Session session = subject.getSession();
				Users userInfos = userInfoService.findUserInfoByUserName(userInfo.getUserName());
				session.setAttribute("cUser", userInfos);
				return BaseResponse.ModifyPasswordSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("changePassword方法执行出错:" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody BaseResponse logout(@Valid LogourRequest logoutRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				subject.logout();
				tokenService.delTokenByToken(logoutRequest.getSid());
				return new BaseResponse(0, "退出成功");
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("logout方法执行出错:" + e);
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	

}
