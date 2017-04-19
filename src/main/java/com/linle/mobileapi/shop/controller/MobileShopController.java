package com.linle.mobileapi.shop.controller;

import java.io.File;
import java.math.BigDecimal;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.linle.common.util.FileUtil;
import com.linle.common.util.SysConfig;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.Users;
import com.linle.entity.sys.Withdraw;
import com.linle.mobileapi.base.BaseMobileapiController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.shop.request.OperateStatusRequest;
import com.linle.mobileapi.shop.request.ShopLoginRequest;
import com.linle.mobileapi.shop.request.WithdrawDepositRequest;
import com.linle.mobileapi.shop.response.ShopLoginResponse;
import com.linle.mobileapi.shop.response.WithdrawDepositResponse;
import com.linle.mobileapi.v1.request.ChangePasswordRequest;
import com.linle.mobileapi.v1.request.ChangeUserLogoRequest;
import com.linle.mobileapi.v1.request.LogourRequest;
import com.linle.mobileapi.v1.response.ChangeUserLogoResponse;
import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;
import com.linle.shop.service.ShopService;
import com.linle.withdraw.service.WithdrawService;

/**
 * 
 * @author pangd
 * @Description 商家移动端相关接口
 */
@Controller
@RequestMapping("/api/1/shop")
public class MobileShopController extends BaseMobileapiController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private WithdrawService withdrawService;

	// 登陆
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse login(@Valid ShopLoginRequest loginRequest, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		ShopLoginResponse loginResponse = new ShopLoginResponse();
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
				if (!user.getIdentity().equals(UserType.SJ)) {
					return new BaseResponse(1, "该账户不是商家");
				}
				// 修改最近登陆时间
				user.setLastLoginDate(new Date());
				userInfoService.setLastLoginTime(user);
				Shop shop = shopService.selectByUserID(user.getId());
				loginResponse.setUserId(user.getId());
				loginResponse.setShopName(shop.getShopName());
				loginResponse.setShopLogo(shop.getShopLogo());
				loginResponse.setShopStatus(shop.getOperateStatus());
				loginResponse.setPhone(shop.getShopPhone());
				loginResponse.setBalance(shopService.getShopBalance(shop.getId()));
				System.out.println(m.writeValueAsString(loginResponse));
				Session session = subject.getSession();
				session.setAttribute("cShop", user);
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

	// 申请体现
	@RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse withdrawDeposit(@Valid WithdrawDepositRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			if (req.getAmount().compareTo(BigDecimal.ZERO)!=1) {
				return new BaseResponse(1, "提现金额错误");
			}
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Shop shop = shopService.selectByUserID(userInfo.getId());
				BigDecimal shopBalance = shopService.getShopBalance(shop.getId());
				if (req.getAmount().compareTo(shopBalance) != 1) {
					WithdrawDepositResponse res = new WithdrawDepositResponse();
					Withdraw withdraw = new Withdraw();
					withdraw.setuId(userInfo.getId());
					withdraw.setAmount(req.getAmount());
					withdraw.setStatuss(0);
					withdraw.setCreateDate(new Date());
					withdrawService.insertSelective(withdraw);
					res.setCode(0);
					res.setMsg("提现成功");
					res.setBalance(shopService.getShopBalance(shop.getId()));
					return res;
				} else {
					return new BaseResponse(1, "提现金额大于可提现金额");
				}
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	// 营业状态修改
	@RequestMapping(value = "/changeOperateStatus", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse changeOperateStatus(OperateStatusRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Shop shop = shopService.selectByUserID(userInfo.getId());
				shop.setOperateStatus(req.getStatus());
				shopService.updateByPrimaryKeySelective(shop);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
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
	
	@ResponseBody
	@RequestMapping(value = "/changeShopLogo", method = RequestMethod.POST)
	public BaseResponse changeUserLogo(@Valid ChangeUserLogoRequest changeUserLogoRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		ChangeUserLogoResponse changeUserLogoResponse = new ChangeUserLogoResponse();
		try {

			Subject subject = SecurityUtils.getSubject();
			Users user = (Users) subject.getSession().getAttribute("cUser");
			Shop shop = shopService.selectByUserID(user.getId());
			CommonsMultipartFile logo = changeUserLogoRequest.getLogo();
			if (null != logo) {
				String originalName = logo.getOriginalFilename();
				if (!originalName.isEmpty()) {
					String path = FileUtil
							.createFilePath(SysConfig.DISK_FOLDER
									+ SysConfig.SHOP_FOLDER, originalName);
					File localFile = new File(path + FileUtil.createNewFileName(originalName));
						if (!localFile.exists()) {
							localFile.createNewFile();
						}
						logo.transferTo(localFile);
						shop.setShopLogo(FileUtil.getDbPath(path) + localFile.getName());
						shopService.updateByPrimaryKeySelective(shop);
						changeUserLogoResponse.setCode(0);
						changeUserLogoResponse.setMsg("修改头像成功");
						changeUserLogoResponse.setLogo(shop.getShopLogo());
				}
			}
			return changeUserLogoResponse;
		} catch (Exception e) {
			e.printStackTrace(); _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}

	}

}
