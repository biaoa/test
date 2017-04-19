package com.linle.common.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.util.FileUtil;
import com.linle.common.util.SysConfig;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.communityPresident.service.CommunityPresidentService;
import com.linle.entity.enumType.UserType;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.LoginToken;
import com.linle.entity.sys.PropertyCompany;
import com.linle.entity.sys.RegionalAgency;
import com.linle.entity.sys.Shop;
import com.linle.entity.sys.SysFile;
import com.linle.entity.sys.SysFolder;
import com.linle.entity.sys.Users;
import com.linle.io.rong.service.RongService;
import com.linle.propertyCompany.service.PropertyCompanyService;
import com.linle.regionalAgency.service.RegionalAgencyService;
import com.linle.shiro.UserLoginToken;
import com.linle.shop.service.ShopService;
import com.linle.system.service.SysFileService;
import com.linle.user.service.LoginTokenService;
import com.linle.user.service.UserInfoService;

public abstract class BaseController {

	@Autowired
	protected ApplicationContext applicationContext;

	protected final Logger _logger = LoggerFactory.getLogger(getClass());

	public ObjectMapper m = new ObjectMapper();
	
	@Autowired
	protected HttpServletRequest request;

	protected String basePath;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private PropertyCompanyService companyService;

	@Autowired
	private RegionalAgencyService agencyService;

	@Autowired
	protected LoginTokenService tokenService;

	@Autowired
	public UserInfoService userInfoService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private SysFileService fileService;

	@Autowired
	public RongService rongService;

	@Autowired
	private CommunityPresidentService communityPresidentService;
	
	public String getBasePath() {
		basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return basePath;
	}

	/**
	 * 获取当前登陆的用户
	 *
	 * @return
	 */
	public Users getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			return userInfo;
		}
		return null;
	}

	public static String getAbsolutePath(String path) throws FileNotFoundException {
		try {
			return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getParentFile().getParent() + path;
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
			return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getAbsolutePath();
		}
	}

	@ExceptionHandler(value = UnknownAccountException.class)
	@ResponseBody
	public CommonResponseMsg unknownAccountExceptionHandler() {
		return CommonResponseMsg.UnknownAccount;
	}

	@ExceptionHandler(value = IncorrectCredentialsException.class)
	@ResponseBody
	public CommonResponseMsg incorrectCredentialsExceptionHandler() {
		return CommonResponseMsg.PasswordIllegal;
	}

	@ExceptionHandler(value = LockedAccountException.class)
	@ResponseBody
	public CommonResponseMsg lockedAccountExceptionHandler() {
		return CommonResponseMsg.LockedAccount;
	}

	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseBody
	public CommonResponseMsg authenticationExceptionHandler() {
		return CommonResponseMsg.NotPermit;
	}

	/**
	 * 
	 * @Description: 根据用户ID 获得到对应的小区信息
	 * @param @return
	 * @return RegionalAgency
	 */
	public Community getCommunity() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			if (userInfo.getIdentity() == UserType.SZ) {
				return communityService
						.selectByPrimaryKey((Long) (subject.getSession().getAttribute("selectedCommunity")));
			}
			return communityService.getCommunityByuserID(userInfo.getId());
		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据用户ID 获得到对应的物业信息
	 * @param @return
	 * @return RegionalAgency
	 */
	public PropertyCompany getPopertyCompany() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			return companyService.getPopertyCompanyByuserID(userInfo.getId());
		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据用户ID 获得到对应的区域代理信息
	 * @param @return
	 * @return RegionalAgency
	 */
	public RegionalAgency getRegionalAgency() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			return agencyService.getRegionalAgencyByuserID(userInfo.getId());
		}
		return null;
	}

	/**
	 * 
	 * @Description: 根据用户ID 获得到对应的小区信息
	 * @param @return
	 * @return RegionalAgency
	 */
	public Community getUserCommunity() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Users userInfo = (Users) subject.getSession().getAttribute("cUser");
			return userInfoService.getById(userInfo.getId()).getCommunity();
		}
		return null;
	}

	/**
	 * 
	 * @Description 根据用户ID 获得对应的商铺信息
	 * @return Shop $
	 */
	public Shop getShopByUserID() {
		Users user = getCurrentUser();
		Shop shop = new Shop();
		if (user != null) {
			shop = shopService.selectByUserID(user.getId());
			return shop;
		}
		return null;
	}

	/**
	 * 
	 * @Description 通过用户ID 获得社长信息
	 * @return CommunityPresident $
	 */
	public CommunityPresident getCommunityPresident() {
		Users user = getCurrentUser();
		if (user != null) {
			CommunityPresident communityPresident = communityPresidentService.selectByUserId(user.getId());
			return communityPresident;
		}
		return null;
	}

	public Long doFile(List<CommonsMultipartFile> imgs, HttpServletRequest servletRequest, String folderName) {
		long startTime=System.currentTimeMillis();   //获取开始时间  
		List<SysFile> sysFiles = new ArrayList<SysFile>();
		if (null != imgs && imgs.size() > 0) {
			for (CommonsMultipartFile multipartFile : imgs) {
				String originalName = multipartFile.getOriginalFilename();
				if (!multipartFile.isEmpty()) {
					String path = FileUtil.createFilePath(
							SysConfig.DISK_FOLDER + folderName,
							originalName);
					File localFile = new File(path + FileUtil.createNewFileName(originalName));
					try {
						if (!localFile.exists()) {
							localFile.createNewFile();
						}
						multipartFile.transferTo(localFile);
					} catch (Exception e) {
						e.printStackTrace();
						_logger.error("出错了", e);
					}
					SysFile sysFile = new SysFile();
					sysFile.setOriginalName(originalName);
					sysFile.setFileName(localFile.getName());
					sysFile.setPath(FileUtil.getDbPath(path) + localFile.getName());
					sysFile.setCreateDate(new Date());
					sysFiles.add(sysFile);
				}
			}

		}
		SysFolder folder = fileService.addFiles(sysFiles);
		if (folder == null) {
			return null;
		}
		long endTime=System.currentTimeMillis(); //获取结束时间  
	    System.out.println("上传文件共使用时间："+(endTime-startTime));  
		return folder.getId();
	}
	
	   /**
	  * 处理sid 第一次登陆,保存sid到sookie 第二次登陆,sid为空则从cookie中取（(H5)
	  * <p>
	  * sid登陆,为空则报异常
	  */
	 public void processSidCookie(HttpServletRequest request, HttpServletResponse response, String sid) {
	     if (StringUtils.isBlank(sid) && request.getCookies() != null) {// sid为空,第二次访问,则从cookie中获取
	         for (Cookie cookie : request.getCookies()) {
	             _logger.debug("获取CookieName:{},Cookie值:{}", cookie.getName(), cookie.getValue());
	             if (cookie.getName().equals("sid")) {
	                 sid = cookie.getValue();
	             }
	         }
	     } else if (StringUtils.isNotBlank(sid)) {// sid不为空,第一次访问,保存cookie
	         Cookie sidCookie = new Cookie("sid", sid);
	         sidCookie.setMaxAge(3600);
	         sidCookie.setPath("/");
	         response.addCookie(sidCookie);
	         _logger.debug("保存Cookie:Sid:{}", sid);
	     }
	     _logger.debug("Sid:{},Cookie为空:{}", sid, request.getCookies() == null);
	     try {
	         validatorSid(sid);
	     } catch (Throwable throwable) {
	         throwable.printStackTrace(); _logger.error("出错了", throwable);
	     }
	     
	 }
	 
	public void validatorSid(String sid) throws Throwable {
		_logger.debug("Sid:{}", sid);
		if (StringUtils.isBlank(sid)) {
			throw new RuntimeException("请先登陆!");
		}
		Subject subject = SecurityUtils.getSubject();
		if (StringUtils.isNotEmpty(sid)) {
			LoginToken dbToken = tokenService.getByToken(sid);
			if (null != dbToken) {
				UserLoginToken token = new UserLoginToken();
				token.setUsername(dbToken.getUserName());
				token.setPassword(dbToken.getPassword().toCharArray());
				token.setLoginMode(UserLoginToken.LoginMode.token);
				try {
					subject.login(token);
					Session session = subject.getSession();
					Users userInfo = userInfoService.findUserInfoByUserName(dbToken.getUserName());
					session.setAttribute("cUser", userInfo);
					// System.out.println(m.writeValueAsString(userInfo));
				} catch (AuthenticationException e) {
					_logger.debug("登录已过期，请重新登录");
				}
			}
		}
	}
	/**
	 * 
	 * @Description 获得设备类型
	 * @param servletRequest
	 * @return Device
	 * $
	 */
	public Device getDevice(HttpServletRequest servletRequest){
		Device device = DeviceUtils.getCurrentDevice(servletRequest);
		return device;
	}
	
	public String getSidByUserId(Long uid){
		String sid ="";
		sid = tokenService.getSidByUserId(uid);
		return sid;
	}
}
