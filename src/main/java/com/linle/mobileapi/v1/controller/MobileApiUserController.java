package com.linle.mobileapi.v1.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linle.bindBankCard.service.BindBankCardService;
import com.linle.commodityAppMenu.service.CommodityAppMenuService;
import com.linle.common.util.BankUtil;
import com.linle.common.util.BankVO;
import com.linle.common.util.DateUtil;
import com.linle.common.util.FileUtil;
import com.linle.common.util.IPUtil;
import com.linle.common.util.PhoneUtil;
import com.linle.common.util.StringUtil;
import com.linle.common.util.SysConfig;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.entity.enumType.SmsValidateType;
import com.linle.entity.enumType.UserAction;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.BindBankCard;
import com.linle.entity.sys.Feedback;
import com.linle.entity.sys.NameCertification;
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.SysRegion;
import com.linle.entity.sys.Users;
import com.linle.event.CreateOrderFeeEvent;
import com.linle.event.UserLogEvent;
import com.linle.feedback.service.FeedbackService;
import com.linle.io.rong.models.GetTokenResult;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseMobileapiController;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.base.ErrorResponse;
import com.linle.mobileapi.v1.model.RongResVO;
import com.linle.mobileapi.v1.model.TopicUserManagerVo;
import com.linle.mobileapi.v1.request.APPNeedLoginRequest;
import com.linle.mobileapi.v1.request.AppMenuRequest;
import com.linle.mobileapi.v1.request.BankCardRequest;
import com.linle.mobileapi.v1.request.BindBankCardRequest;
import com.linle.mobileapi.v1.request.ChagneCommunityRequest;
import com.linle.mobileapi.v1.request.ChangePasswordRequest;
import com.linle.mobileapi.v1.request.ChangePhoneRequest;
import com.linle.mobileapi.v1.request.ChangeUserInfoRequest;
import com.linle.mobileapi.v1.request.ChangeUserLogoRequest;
import com.linle.mobileapi.v1.request.CommunityListRequest;
import com.linle.mobileapi.v1.request.FeedbackRequest;
import com.linle.mobileapi.v1.request.ForgotPasswordRequest;
import com.linle.mobileapi.v1.request.GetNameCertificationRequest;
import com.linle.mobileapi.v1.request.LoginRequest;
import com.linle.mobileapi.v1.request.LogourRequest;
import com.linle.mobileapi.v1.request.NameCertificationRequest;
import com.linle.mobileapi.v1.request.RegisteRequest;
import com.linle.mobileapi.v1.request.UserInfoRequest;
import com.linle.mobileapi.v1.request.VerifySMSCodeRequest;
import com.linle.mobileapi.v1.response.ApiResponse;
import com.linle.mobileapi.v1.response.AppMenuResponse;
import com.linle.mobileapi.v1.response.BankListResponse;
import com.linle.mobileapi.v1.response.ChagneCommunityResponse;
import com.linle.mobileapi.v1.response.ChangeUserLogoResponse;
import com.linle.mobileapi.v1.response.LoadAreaResponse;
import com.linle.mobileapi.v1.response.LoadRoomResponse;
import com.linle.mobileapi.v1.response.LoginResponse;
import com.linle.mobileapi.v1.response.LogoutResponse;
import com.linle.mobileapi.v1.response.ModifyRongTokenResponse;
import com.linle.mobileapi.v1.response.NameCertificationResponse;
import com.linle.mobileapi.v1.response.RoomCommonResponse;
import com.linle.mobileapi.v1.response.UserInfoResponse;
import com.linle.mobileapi.v1.response.VerResponse;
import com.linle.nameCertification.service.NameCertificationService;
import com.linle.roomno.service.RoomService;
import com.linle.shiro.UserLoginToken;
import com.linle.shiro.UserLoginToken.LoginMode;
import com.linle.sms.service.SmsService;
import com.linle.system.service.RegionService;
import com.linle.topicUserManager.service.TopicUserManagerService;
import com.linle.user.service.LoginTokenService;

/**
 * @描述:客户端用户相关接口
 **/
@Controller
@RequestMapping("/api/1")
public class MobileApiUserController extends BaseMobileapiController {
	@Autowired
	protected LoginTokenService tokenService;
	@Autowired
	private SmsService smsService;

	@Autowired
	private RegionService regioServer;

	@Autowired
	private RoomService romeService;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private BindBankCardService bindBankCardService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private NameCertificationService nameCertificationService;
	
	@Autowired
	private RongService rongService;
	
	@Autowired
	private CommodityAppMenuService appMenuService;
	
	@Autowired
	private TopicUserManagerService topicUserManagerService;

	@Value("${ver}")
	private String ver;
	// 系统有图片的银行卡列表
	static String bankArray[] = { "ABC", "COMM", "BJBANK", "BOC", "CCB", "CEB", "CIB", "CITIC", "CMB", "CMBC", "GDB",
			"HURCB", "HXBANK", "ICBC", "PAB", "PSBC", "SPDB", "HZCB" };

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody BaseResponse logout(@Valid LogourRequest logoutRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			tokenService.delTokenByToken(logoutRequest.getSid());
			return new LogoutResponse();
		} catch (Exception e) {
			_logger.error("logout方法执行出错:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/changeUserLogo", method = RequestMethod.POST)
	public BaseResponse changeUserLogo(@Valid ChangeUserLogoRequest changeUserLogoRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		ChangeUserLogoResponse changeUserLogoResponse = new ChangeUserLogoResponse();
		try {

			Subject subject = SecurityUtils.getSubject();
			Users user = (Users) subject.getSession().getAttribute("cUser");
			CommonsMultipartFile logo = changeUserLogoRequest.getLogo();
			if (null != logo) {
				String originalName = logo.getOriginalFilename();
				if (!originalName.isEmpty()) {
					String path = FileUtil
							.createFilePath(SysConfig.DISK_FOLDER
									+ SysConfig.USERLOGO_FOLDER, originalName);
					File localFile = new File(path + FileUtil.createNewFileName(originalName));
					try {
						if (!localFile.exists()) {
							localFile.createNewFile();
						}
						logo.transferTo(localFile);
						user.setLogo(FileUtil.getDbPath(path) + localFile.getName());
						boolean modifyisok = userInfoService.modifyUserLogo(user);
						_logger.info("用户：" + user.getMobilePhone() + "修改头像的结果是：" + modifyisok + "");
						changeUserLogoResponse.setCode(0);
						changeUserLogoResponse.setMsg("修改头像成功");
						changeUserLogoResponse.setLogo(user.getLogo());
					} catch (Exception e) {
						_logger.error("修改头像失败了：" + e);
						e.printStackTrace();
						changeUserLogoResponse.setCode(1);
						changeUserLogoResponse.setMsg("修改头像失败了");
						return changeUserLogoResponse;
					}
				}
			}
			return changeUserLogoResponse;
		} catch (Exception e) {
			_logger.error("changeUserLogo方法执行出错:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/changeUserNameOrSex", method = RequestMethod.POST)
	public BaseResponse changeUserInfo(@Valid ChangeUserInfoRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		BaseResponse res = new BaseResponse();
		try {

			Subject subject = SecurityUtils.getSubject();
			Users user = (Users) subject.getSession().getAttribute("cUser");
			if (null != req.getSex() && !"".equals(req.getSex())) {
				user.setSex(req.getSex());
			}
			if (null != req.getName() && !"".equals(req.getName())) {
				user.setName(req.getName());
			}
			boolean modifyisok = userInfoService.updateNameOrSex(user);
			_logger.info("用户：" + user.getMobilePhone() + "修改昵称orsex的结果是：" + modifyisok + "");
			res.setCode(0);
			res.setMsg("修改成功");
			return res;
		} catch (Exception e) {
			_logger.error("changeUserLogo方法执行出错:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}

	}

	/**
	 * 获取版本
	 */
	@ResponseBody
	@RequestMapping(value = "/getver/{type}", method = RequestMethod.GET)
	public BaseResponse getver(@PathVariable("type") Integer type) {
		VerResponse response = new VerResponse();
		try {
			if (type == 0) {
				response.setMaxver(ver.split("\\|")[0]);
				response.setMinver(ver.split("\\|")[1]);
				response.setUpdateTime(ver.split("\\|")[2]);
				response.setSize(ver.split("\\|")[3]);
			} else {
				response.setMaxver(ver.split("\\|")[4]);
				response.setMinver(ver.split("\\|")[5]);
				response.setUpdateTime(ver.split("\\|")[6]);
				response.setSize(ver.split("\\|")[7]);
			}
			response.setCode(0);
			response.setMsg("获取版本成功");
		} catch (Exception e) {
			response.setCode(1);
			response.setMsg("出错了");
		}
		return response;
	}

	/**
	 * 加载区域
	 */
	@ResponseBody
	@RequestMapping(value = "/loadArea")
	public BaseResponse getver() {
		try {
			LoadAreaResponse res = new LoadAreaResponse();
			res.setAreaList(regioServer.getAllData());
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}

	/**
	 * 加载小区楼层
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/loadRoom", method = RequestMethod.POST)
	public BaseResponse getRoom(Long community_id) {
		try {
			LoadRoomResponse res = new LoadRoomResponse();
			res.setBuildList(romeService.getRomeForAPI(community_id));
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	
	 //1.加载某小区所有幢
	@ResponseBody
	@RequestMapping(value = "/loadBuildName", method = RequestMethod.POST)
	public BaseResponse getBuildName(Long community_id) {
		try {
			RoomCommonResponse res = new RoomCommonResponse();
			res.setList(romeService.getBuildForAPI(community_id));
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	 //2.加载某小区某幢所有单元
	@ResponseBody
	@RequestMapping(value = "/loadUnitName", method = RequestMethod.POST)
	public BaseResponse getUnitName(Long community_id,String building) {
		try {
			RoomCommonResponse res = new RoomCommonResponse();
			res.setList(romeService.getUnitForAPI(community_id,building));
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	 //3.加载某小区某幢某单元下所有房号
	@ResponseBody
	@RequestMapping(value = "/loadRoomName", method = RequestMethod.POST)
	public BaseResponse getRoomName(Long community_id,String building,String unit) {
		try {
			RoomCommonResponse res = new RoomCommonResponse();
			res.setList(romeService.getRoomForAPI(community_id,building,unit));
			res.setCode(0);
			res.setMsg("成功");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 获得验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	public BaseResponse getCode(@Valid String phone, String type) {
		try {
			String templteCode = "";
			String param = "";
			String smsFreeSignName = "";
			int smsType = 0; // 1 注册 2 忘记密码
			if (!PhoneUtil.verifyPhone(phone)) {
				return ApiResponse.PhoneNumberWrongInstance;
			}
			if (!StringUtil.isNotNull(type)) {
				return ApiResponse.SMSTypeISNotEmpty;
			}
			String code = String.valueOf(PhoneUtil.CreateValidateCode());
			// 注册
			if ("register".equals(type)) {
				// 判断是否注册过
				Users user = new Users();
				user = userInfoService.findUserInfoByPhone(phone);
				if (user != null) {
					return ApiResponse.PhoneExist;
				}
				templteCode = "SMS_5620216";
				param = "{'code':'" + code + "','product':'邻乐社区'}";
				smsFreeSignName = "注册验证";
				smsType = SmsValidateType.registe.getIntValue();
			}
			if ("forgetPassword".equals(type)) {
				Users user = new Users();
				user = userInfoService.findUserInfoByPhone(phone);
				if (user == null) {
					return ApiResponse.UserNotExist;
				}
				templteCode = "SMS_5620214";
				param = "{'code':'" + code + "','product':'邻乐社区'}";
				smsFreeSignName = "变更验证";
				smsType = SmsValidateType.forgotPassword.getIntValue();
			}
			if ("changePhone".equals(type)) {
				templteCode = "SMS_5620214";
				param = "{'code':'" + code + "','product':'邻乐社区'}";
				smsFreeSignName = "变更验证";
				smsType = SmsValidateType.changePhone.getIntValue();
			}
			boolean sendResult = smsService.sendCode(smsFreeSignName, param, phone, templteCode, code, smsType);
			return sendResult ? new BaseResponse(0, "短信发送成功") : new BaseResponse(1, "短信发送失败");
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}

	/**
	 * 注册
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BaseResponse register(@Valid RegisteRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			return userInfoService.regist(req);
		} catch (Exception e) {
			try {
				_logger.error("注册出现错误参数为:"+m.writeValueAsString(req));
				return BaseResponse.ServerException;
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
				return BaseResponse.ServerException;
			}
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody BaseResponse login(@Valid LoginRequest loginRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		LoginResponse loginResponse = new LoginResponse();
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
				if(!user.getIdentity().equals(UserType.JM)){
					return new BaseResponse(1, "不能登陆");
				}
				applicationContext.publishEvent(new CreateOrderFeeEvent(user));
				applicationContext.publishEvent(new UserLogEvent(IPUtil.getIpAddress(request), UserAction.login, user));
				Community community = user.getCommunity();
				SysRegion region = community.getSysRegion();
				loginResponse.setUserId(user.getId());
				loginResponse.setUsername(user.getName());
				loginResponse.setUserLogo(user.getLogo());
				loginResponse.setPhone(user.getMobilePhone());
				loginResponse.setAddress(region.getParent().getParent().getName()+ region.getParent().getName()
						+ region.getName()
						+ community.getName());
				loginResponse.setAddressDetails(user.getAddressDetails().getBuilding() + "-"
						+ user.getAddressDetails().getUnit() + "-" + user.getAddressDetails().getRoom());
				loginResponse.setSex(user.getSex());
				loginResponse.setRongToken(user.getToken());
				loginResponse.setCommunityRongId(community.getUser().getId().toString());
				loginResponse.setCommunityLogo(community.getLogo());
				loginResponse.setCommunityName(community.getName());
				loginResponse.setCommunityPhone(community.getPhone());
				loginResponse.setCommunityPresidentPhone(community.getPresident().getPhone());
				String city = "";
				SysRegion sysregion = region.getParent();
				if (sysregion != null) {
					city = sysregion.getName();
				} else {
					city = region.getName();
				}
				loginResponse.setCityName(city);
				try {
					TopicUserManagerVo topicUserManager=topicUserManagerService.selectByIdForApi(user.getId());
					loginResponse.setTopicUserManager(topicUserManager);
				} catch (Exception e) {
					_logger.error("获取话题用户权限执行异常:" + e);
					e.printStackTrace();
				}
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
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public @ResponseBody BaseResponse forgotPassword(@Valid ForgotPasswordRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		ErrorResponse errorResponse = new ErrorResponse();
		try {
			boolean smscode = smsService.verifyCode(req.getValidateCode(), req.getMobilePhone(),
					SmsValidateType.forgotPassword.getIntValue());
			if (!smscode) {
				return new BaseResponse(1, "验证码错误或过期");
			}
			if (!userInfoService.forgotPassword(req.getMobilePhone(), req.getPassword())) {
				errorResponse.setCode(1);
				errorResponse.setMsg("修改密码失败");
				return errorResponse;
			}
			return BaseResponse.ForgotPasswordSuccess;
		} catch (Exception e) {
			_logger.error("forgotPassword方法执行出错:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody BaseResponse changePassword(@Valid ChangePasswordRequest changePasswordRequest, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		ErrorResponse errorResponse = new ErrorResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
			}
			String oldPassword = new Sha1Hash(changePasswordRequest.getOldPassword(), userInfo.getSalt()).toString();
			if (userInfo == null || userInfo.getId() == null || !oldPassword.equals(userInfo.getPassword())) {
				_logger.error("旧密码输入不正确");
				errorResponse.setCode(1);
				errorResponse.setMsg("旧密码输入不正确");
				return errorResponse;
			}
			Users userInfoUp = new Users();
			userInfoUp.setId(userInfo.getId());
			userInfoUp.setPassword(changePasswordRequest.getNewPassword());
			userInfoService.updatePasswordAndToken(userInfoUp);
			Session session = subject.getSession();
			Users userInfos = userInfoService.findUserInfoByUserName(userInfo.getUserName());
			session.setAttribute("cUser", userInfos);
			return BaseResponse.ModifyPasswordSuccess;
		} catch (Exception e) {
			_logger.error("changePassword方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	/**
	 * 根据用户ID 获得头像和昵称
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfoById", method = RequestMethod.POST)
	public BaseResponse getUserInfoById(UserInfoRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			UserInfoResponse res = new UserInfoResponse();
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				_logger.info("用户ID：" + user.getId() + "获取 用户ID为" + req.getUid() + "的头像昵称,时间：" + new Date().getTime() + "");
				res.setData(userInfoService.getUserInfoForAPI(req.getUid()));
			} else {
				return BaseResponse.PleaseSignIn;
			}
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCommunityList", method = RequestMethod.POST)
	public BaseResponse getCommunityList(CommunityListRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			BaseResponse res = new BaseResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				Map<String, Object> map = new HashMap<>();
				map.put("cityList", communityService.getCommunityListByCityName(req.getCityName()));
				res.setResult(map);
			return res;
		} catch (Exception e) {
			 _logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}

	@RequestMapping(value = "/changePhone", method = RequestMethod.POST)
	public @ResponseBody BaseResponse changePhone(@Valid ChangePhoneRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				return userInfoService.chargePhone(req);
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("changePhone方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 绑定银行卡
	@ResponseBody
	@RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
	public BaseResponse bindBankCard(@Valid BindBankCardRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if (bindBankCardService.getByCardNo(req.getCardNo()) != null) {
					return BaseResponse.CardExist;
				}
				BindBankCard card = new BindBankCard();
				card.setCardNo(req.getCardNo());
				card.setuId(userInfo.getId());
				card.setCreateDate(new Date());
				String result = BankUtil.getBankInfo(req.getCardNo());
				_logger.info("卡号：" + req.getCardNo() + "验证结果是：" + result);
				BankVO b = m.readValue(result, BankVO.class);
				if ("ok".equals(b.getStat()) && "true".equals(b.getValidated())) {
					card.setBankName(b.getBank());
					card.setCardType(b.getCardType()); // DC储蓄卡 CC //信用卡
					if (StringUtil.contains(bankArray, b.getBank())) {
						card.setLogo("/resources/images/bank/" + b.getBank() + ".png");
					} else {
						card.setLogo("");
					}
					boolean isok = bindBankCardService.insertSelective(card) > 0;
					_logger.info("用户：" + userInfo.getId() + "绑定银行卡的结果是：" + isok);
					return BaseResponse.OperateSuccess;
				}
				return BaseResponse.CardError;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("changePhone方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 取消绑定
	@ResponseBody
	@RequestMapping(value = "/unBindBankCard", method = RequestMethod.POST)
	public BaseResponse unBindBankCard(@Valid BindBankCardRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("cardNo", req.getCardNo());
				boolean isok = bindBankCardService.deleteCardNo(map);
				_logger.info("用户：" + userInfo.getId() + "解绑银行卡的结果是：" + isok);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("unBindBankCard方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 银行卡列表
	@ResponseBody
	@RequestMapping(value = "/getBankCard", method = RequestMethod.POST)
	public BaseResponse getBankCard(BankCardRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				BankListResponse res = new BankListResponse();
				res.setCode(0);
				res.setMsg("获取成功");
				Map<String, Object> map = new HashMap<>();
				map.put("uid", userInfo.getId());
				map.put("data", bindBankCardService.getCardList(map));
				res.setResult(map);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("getBankCard方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 意见反馈
	@ResponseBody
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public BaseResponse feedback(@Valid FeedbackRequest req, Errors errors, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				Feedback back = new Feedback();
				back.setContent(req.getContent());
				back.setMobileInfo(req.getMobileInfo());
				back.setSysVersion(req.getSysVersion());
				back.setUserId(userInfo.getId());
				back.setCreateDate(new Date());
				back.setType(req.getType());
				back.setMobileVersion(req.getMobileVersion());
				int result = feedbackService.insertSelective(back);
				_logger.info("用户：" + userInfo.getId() + "" + "提交意见反馈的结果是：" + result);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("feedback方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 实名认证
	@ResponseBody
	@RequestMapping(value = "/nameCertification", method = RequestMethod.POST)
	public BaseResponse nameCertification(@Valid NameCertificationRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if (nameCertificationService.selectByUserId(userInfo.getId()) != null) {
					return BaseResponse.NameExist;
				}
				NameCertification certification = new NameCertification();
				certification.setUserId(userInfo.getId());
				certification.setName(req.getName());
				certification.setCradNo(req.getCradNo());
				certification.setCreateDate(new Date());
				int result = nameCertificationService.insertSelective(certification);
				_logger.info("用户：" + userInfo.getId() + "" + "实名认证的结果是：" + result);
				return BaseResponse.OperateSuccess;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("nameCertification方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}

	// 获得实名认证细信息
	@ResponseBody
	@RequestMapping(value = "/getNameCertification", method = RequestMethod.POST)
	public BaseResponse getNameCertification(@Valid GetNameCertificationRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		NameCertificationResponse res = new NameCertificationResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			Users userInfo = null;
			if (subject.isAuthenticated()) {
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				NameCertification certification = nameCertificationService.selectByUserId(userInfo.getId());
				if (certification == null) {
					return BaseResponse.NameNotExist;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name",certification.getName());
				map.put("cradNo",certification.getCradNo());
				res.setResult(map);
				res.setCode(0);
				res.setMsg("获取成功");
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("getNameCertification方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}
	
	// 判断是否需要登录
	@ResponseBody
	@RequestMapping(value = "/needLogin", method = RequestMethod.POST)
	public BaseResponse needLogin(@Valid APPNeedLoginRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				//如果不用登陆的话，也要去检查一下水电费的账单
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				applicationContext.publishEvent(new CreateOrderFeeEvent(user));
				applicationContext.publishEvent(new UserLogEvent(IPUtil.getIpAddress(request), UserAction.login, user));
				return BaseResponse.NotNeedLogin;
			}
			return new BaseResponse(1,"需要登录");
		} catch (Exception e) {
			_logger.error("needLogin方法执行出错:" + e);
			e.printStackTrace();
			return BaseResponse.ServerException;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/modifyRongToken", method = RequestMethod.POST)
	public BaseResponse modifyRongToken(@Valid APPNeedLoginRequest req, Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		ModifyRongTokenResponse res = new ModifyRongTokenResponse();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				Users user = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				GetTokenResult result = rongService.getUserRongToken(user);
				RongResVO vo = new RongResVO();
				vo.setToken(result.getToken());
				res.setCode(0);
				res.setMsg("success");
				res.setData(vo);
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("needLogin方法执行出错:" + e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="chagneCommunity",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse ChagneCommunity(@Valid ChagneCommunityRequest req,Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			ChagneCommunityResponse res  = new ChagneCommunityResponse();
			if (subject.isAuthenticated()) {
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				if(!verifyChangeCommunityDate(userInfo.getLastChageAddressDate())){
					return new BaseResponse(1, "30天内只能修改一次地址信息");
				}
				Community community = new Community();
				community.setId(req.getCommunityId());
				userInfo.setCommunity(community);
				RoomNo roomNo = new RoomNo();
				roomNo.setId(req.getAddressDetails());
				userInfo.setAddressDetails(roomNo);
				userInfo.setLastChageAddressDate(new Date());
				userInfoService.chagneCommunity(userInfo);
				userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				community = userInfo.getCommunity();
				res.setAddress(community.getSysRegion().getParent().getParent().getName()
						+ community.getSysRegion().getParent().getName()
						+ community.getSysRegion().getName()
						+ community.getName());
				res.setAddressDetails(userInfo.getAddressDetails().getBuilding() + "-"
						+ userInfo.getAddressDetails().getUnit() + "-" + userInfo.getAddressDetails().getRoom());
				res.setCommunityRongId(community.getUser().getId());
				res.setCommunityLogo(community.getLogo());
				res.setCommunityName(community.getName());
				res.setCommunityPhone(community.getPhone());
				res.setCommunityPresidentPhone(community.getPresident().getPhone());
				SysRegion region = community.getSysRegion();
				String city = "";
				SysRegion sysregion = region.getParent();
				if (sysregion != null) {
					city = sysregion.getName();
				} else {
					city = region.getName();
				}
				res.setCityName(city);
				res.setCode(0);
				res.setMsg("修改成功");
				System.out.println(m.writeValueAsString(res));
				return res;
			}
			return BaseResponse.PleaseSignIn;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
	}
	
	@RequestMapping(value="/getAppMenu",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getAppMenu(AppMenuRequest req,Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		try {
			Subject subject = SecurityUtils.getSubject();
			AppMenuResponse res = new AppMenuResponse();
			Map<String, Object> map = new HashMap<>();
			if(subject.isAuthenticated()) {
				Users userInfo = userInfoService.findUserInfoByUserName(subject.getPrincipal().toString());
				map.put("commodityId", userInfo.getCommunity().getId());
			}
			res.setData(appMenuService.getAppMenuForAPI(map));
			res.setCode(0);
			res.setMsg("");
			return res;
		} catch (Exception e) {
			_logger.error("出错了", e);
			return BaseResponse.ServerException;
		}
		
	}
	/**
	 * 
	 * @Description 验证是否能修改小区地址
	 * @param lastChageDate
	 * @return boolean
	 * $
	 */
	public boolean verifyChangeCommunityDate(Date lastChageDate){
		Date d = new Date();
		if(lastChageDate==null){
			return true;
		}
		if(DateUtil.DateDiffReturnDay(d, lastChageDate)>=30){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value="/verifySMSCode",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse verifySMSCode(VerifySMSCodeRequest req,Errors errors,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		try {
			boolean smscode = smsService.verifyCode(req.getSmsCode(), req.getPhone(),SmsValidateType.registe.getIntValue());
			if (!smscode) {
				return new BaseResponse(1, "验证码错误或过期");
			}
			return new BaseResponse(0, "验证成功");
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error("验证验证码是否正确方法出错:"+e);
			return BaseResponse.ServerException;
		}
	}
	
}
