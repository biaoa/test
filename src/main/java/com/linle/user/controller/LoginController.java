package com.linle.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.linle.common.util.IPUtil;
import com.linle.common.util.ResponseMsg;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.enumType.UserAction;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.Users;
import com.linle.event.UserLogEvent;
import com.linle.io.rong.models.GetTokenResult;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;
import com.linle.system.service.RedisManager;
import com.linle.user.base.BaseUserController;
import com.linle.user.model.Login;
import com.taobao.api.ApiException;

@Controller
public class LoginController extends BaseUserController {

	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private RedisManager redisManager;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/login");
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseMsg login(@Valid Login user, BindingResult errors, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		UserLoginToken loginToken = new UserLoginToken();
		loginToken.setUsername(user.getUserName());
		loginToken.setPassword(user.getPassword() != null ? user.getPassword().toCharArray() : null);
		Subject subject = SecurityUtils.getSubject();
		try {
			loginToken.setLoginMode(LoginMode.password);
			subject.login(loginToken);
			Session session = subject.getSession();
			Users userInfo = userInfoService.findUserInfoByUserName(user.getUserName());
			tokenService.addToken(user.getUserName());
			if (userInfo.getIdentity() == UserType.JM) {
				return new ResponseMsg(1, "无权限登陆", null);
			}
			session.setAttribute("cUser", userInfo);
			if (userInfo.getIdentity() == UserType.SZ) {
				List<Community> communityList = communityService
						.getCommunityByPresident(getCommunityPresident().getId());
				session.setAttribute("communityList", communityList);
				session.setAttribute("selectedCommunity", !communityList.isEmpty() ? communityList.get(0).getId() : 0);
			}
			applicationContext.publishEvent(new UserLogEvent(IPUtil.getIpAddress(request), UserAction.login, userInfo));
			if(userInfo.getIdentity() == UserType.SJ || userInfo.getIdentity() == UserType.XQ){
				//将用户id存入redis中的userMsgList中
				redisManager.sAdd("userMsgList", userInfo.getId());
				//商家/小区登陆后发送一条最新的消息给他们
				
			}
		} catch (UnknownAccountException uae) {
			_logger.debug("账户不存在!");
			mv.setViewName("login");
			return new ResponseMsg(1, "用户名或密码错误", null);
		} catch (IncorrectCredentialsException ice) {
			_logger.debug("密码不正确!");
			mv.setViewName("login");
			return new ResponseMsg(1, "用户名或密码错误", null);
		} catch (LockedAccountException lae) {
			_logger.debug("账户不可用!");
			mv.setViewName("login");
			return new ResponseMsg(2, "账户被禁用,请联系客服", null);
		} catch (AuthenticationException ae) {
			_logger.debug("认证错误!");
			mv.setViewName("login");
			return new ResponseMsg(2, "账户异常", null);
		}
		return new ResponseMsg(0, "登录成功", null);
	}

	@RequiresAuthentication
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Users user = (Users) subject.getSession().getAttribute("cUser");
		applicationContext.publishEvent(new UserLogEvent(request.getRemoteAddr(), UserAction.login, user));
		subject.logout();
		tokenService.delTokenByToken(getSidByUserId(user.getId()));
		redisManager.sRemove("userMsgList", user.getId().toString());
		return "redirect:/login";
	}

	public static void main(String[] args) throws ApiException {

	}

	@RequestMapping(value = "/welcome1", method = RequestMethod.GET)
	public String welcome() {
		return "welcome1";
	}

	@RequestMapping(value = "/webIMtokenFail", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse webIMtokenFail(HttpServletRequest request) {
		try {
			//第一步修改用户token
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			Users user = (Users) subject.getSession().getAttribute("cUser");
			GetTokenResult result = rongService.getUserRongToken(user);
			System.out.println("修改的结果是"+m.writeValueAsString(result));
			//第二步更新session中用户信息
			Users userInfo = userInfoService.findUserInfoByUserName(user.getUserName());
			session.setAttribute("cUser", userInfo);
			return BaseResponse.OperateSuccess;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
}
