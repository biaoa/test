package com.linle.user.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.base.BaseLoginToken;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.event.CommonEventModel;
import com.linle.common.util.Page;
import com.linle.common.util.StringUtil;
import com.linle.community.model.Community;
import com.linle.community.service.CommunityService;
import com.linle.disableUser.model.DisableUser;
import com.linle.disableUser.service.DisableUserService;
import com.linle.entity.enumType.SmsValidateType;
import com.linle.entity.enumType.UserStatusType;
import com.linle.entity.enumType.UserType;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.LoginToken;
import com.linle.entity.sys.RoomNo;
import com.linle.entity.sys.Users;
import com.linle.event.CreateOrderFeeEvent;
import com.linle.event.RongMessageEvent;
import com.linle.io.rong.models.TxtMessage;
import com.linle.io.rong.service.RongService;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UserInfoVO;
import com.linle.mobileapi.v1.request.ChangePhoneRequest;
import com.linle.mobileapi.v1.request.RegisteRequest;
import com.linle.sms.service.SmsService;
import com.linle.sysOrder.service.SysOrderService;
import com.linle.system.mapper.SysFolderMapper;
import com.linle.system.service.RegionService;
import com.linle.user.mapper.UserMapper;
import com.linle.user.model.CommunityOnlineIM;
import com.linle.user.model.CommunityUserIM;
import com.linle.user.service.LoginTokenService;
import com.linle.user.service.UserInfoService;
import com.linle.user.service.UserRoleService;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl extends CommonServiceAdpter<Users>  implements UserInfoService{
	private final Logger _logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegionService regionService;
	@Autowired
	private UserRoleService roleService;
	@Autowired
	private SysFolderMapper folderMapper;
	@Autowired
	private LoginTokenService  loginTokenService;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private RongService rongService;
	
	@Autowired
	private CommunityService  communityService;
	
	@Value("${rong_appKey}")
	private String appKey;

	@Value("${rong_appSecret}")
	private String appSecret;
	
	private ObjectMapper objMapper = new ObjectMapper();
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SysOrderService orderService;
	
	
	@Autowired
	private DisableUserService disableUserService;
	
	public UserMapper getMapper() {
		return mapper;
	}

	@Override
	public Users findUserInfoByPhone(String mobilePhone) {
		return mapper.getByMobilePhone(mobilePhone);
	}


	@Override
	public boolean addUserInfo(Users userInfo) {
		String salt = getRandomString(16);
		String password = new Sha1Hash(userInfo.getPassword(), salt).toString();
		userInfo.setSalt(salt);
		userInfo.setPassword(password);
		return mapper.insertSelective(userInfo) > 0;
	}
	
	private String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	@Override
	public List<Users> findAllUsers(Page<Users> page) {
		return mapper.getUsers(page);
	}
	
	@Override
	public List<Users> getCommunityUsers(Page<Users> page) {
		return mapper.getCommunityUsers(page);
	}
	
	
	@Override
	public Boolean updateLoginTime(Users userInfo) {
		return mapper.updateLoginTime(userInfo)>0;
	}

	@Override
	public Users findUserInfoByUserName(String userName) {
		return mapper.getByUserName(userName);
	}

	@Override
	public Boolean updatePassword(Users user) {
		String salt = getRandomString(16);
		user.setSalt(salt);
		user.setPassword(new Sha1Hash(user.getPassword(),user.getSalt()).toString());
		return mapper.updatePassword(user)>0;
	}

	@Override
	public Boolean checkPassword(String password, Long userId) {
		Users userInfo = mapper.getByUserId(userId);
		String passwd = new Sha1Hash(password,userInfo.getSalt()).toString();
		if(passwd.equals(userInfo.getPassword())){
			return true;
		}
		return false;
	}

	
	
	@Override
	public int delAccount(Long uid) {
		Users user = mapper.getByUserId(uid);
		user.setDelFlag(UserStatusType.deleted);
		return mapper.deleteUser(user);
	}



	

	@Override
	public boolean checkUserExsit(String userName) {
		return mapper.checkUserExsit(userName)>0;
	}

	@Override
	public boolean updatePasswordAndToken(Users userInfo) {
		String salt = getRandomString(16);
		userInfo.setSalt(salt);
		String passWord = new Sha1Hash(userInfo.getPassword(), salt).toString();
		userInfo.setPassword(passWord);
		mapper.updatePassword(userInfo);
		LoginToken token = new LoginToken();
		token.setUser(userInfo);
		token.setPassword(passWord);
		return loginTokenService.updatePassword(token);
	}


	@Override
	public boolean updateUserFromCommunityEmployee(CommunityEmployee communityEmployee) {
		Users user=new Users();
		user.setId(communityEmployee.getUser().getId());
		user.setUserName(communityEmployee.getName());
		if("11".equals(communityEmployee.getLevel())){
			user.setIdentity(UserType.XQBMFZR);//小区负责人
		}else{
			user.setIdentity(UserType.XQPTYG);//小区普通员工
		}
		return mapper.updateUserFromCommunityEmployee(user);
	}




	@Override
	public boolean forgotPassword(String mobilePhone, String password) {
		Users userInfo = new Users();
		String salt = getRandomString(16);
		userInfo.setSalt(salt);
		userInfo.setPassword(new Sha1Hash(password, salt).toString());
		userInfo.setMobilePhone(mobilePhone);
		return mapper.forgotPassword(userInfo) > 0;
	}

	

	


	@Override
	public Users getById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	

	


	@Override
	public Users getUserinfoByUserNameAndEmail(Users users) {
		return mapper.getUserinfoByUserNameAndEmail(users);
	}

	

	@Override
	public int resetPassword(Long uid) {
		Users user =new Users();
		user = getById(uid);
		user.setPassword("123456");
		return updatePassword(user)?1:0;
	}


	

	@Override
	public BaseResponse regist(RegisteRequest req) {
		// 判断用户是否注册
		Users hasUser = mapper.getByMobilePhone(req.getPhone());
		if (hasUser != null) {
			return new BaseResponse(1, "该用户已注册");
		}
		// 判断验证码是否正确
		boolean smscode = smsService.verifyCode(req.getSmsCode(), req.getPhone(),
				SmsValidateType.registe.getIntValue());
		if (!smscode) {
			return new BaseResponse(1, "验证码错误或过期");
		}
		Users user = new Users();
		Community community = new Community();
		RoomNo no = new RoomNo();
		no.setId(req.getAddressDetails());
		community.setId(req.getCommunityID());

		user.setMobilePhone(req.getPhone());
		user.setPassword(req.getPassword());
		user.setCommunity(community);
		user.setAddressDetails(no);
		user.setUserName(req.getPhone());
		user.setName("LL_" + StringUtil.hidePhoneNum(req.getPhone()));
		user.setIdentity(UserType.JM);
		user.setStatus(UserStatusType.normal);
		
		String salt = getRandomString(16);
		String password = new Sha1Hash(user.getPassword(), salt).toString();
		user.setSalt(salt);
		user.setPassword(password);
		int result = mapper.insertSelective(user);
		//获得融云token
		if(result>0){
			rongService.getUserRongToken(user);
			//融云发送消息
			Community commty=communityService.selectByPrimaryKey(req.getCommunityID());//根据小区id获得小区对象
			//1.小区社长给注册用户发送消息
			TxtMessage message=new TxtMessage("你好,"+req.getPhone()+"用户,我是("+commty.getName()+")小区社长,欢迎你加入我们的邻乐社区!");
			applicationContext.publishEvent(new RongMessageEvent(commty.getPresident().getUserId(), user.getId().toString() , message));
			//2.小区物业给注册用户发送消息
			TxtMessage message2=new TxtMessage("你好,"+req.getPhone()+"用户,我是("+commty.getName()+")小区物业,欢迎你加入我们的邻乐社区!");
			applicationContext.publishEvent(new RongMessageEvent(commty.getPropertyCompany().getUser().getId(), user.getId().toString() , message2));
			
			//判断有没有房号缴费记录（水电费，物业费，宽带费，三大类），若有则产生费用订单
//			orderService.createFeeOrderAndDetailByRoomNo(user);
			applicationContext.publishEvent(new CreateOrderFeeEvent(user));
			
		}
		return result>0?BaseResponse.RegisterSuccess:BaseResponse.RegisterFail;
	}

	@Override
	public boolean modifyUserLogo(Users user) {
		return mapper.modifyUserLogo(user)>0;
	}

	@Override
	public void login(BaseLoginToken token) {
		token.setRememberMe(true);
        getSubject().login(token);
        Users user = (Users) getSubject().getPrincipals().getPrimaryPrincipal();
        getSession().setAttribute("user",user);
        user.setLastLoginDate((new Date()));
        applicationContext.publishEvent(CommonEventModel.LOGIN_EVENT());
	}


	@Override
	public boolean updateNameOrSex(Users user) {
		return mapper.updateNameOrSex(user)>0;
	}

	@Override
	public boolean updateUserToken(Users user) {
		return mapper.updateUserToken(user)>0;
	}

	@Override
	public UserInfoVO getUserInfoForAPI(Long uid) {
		return mapper.getUserInfoForAPI(uid);
	}

	@Override
	public boolean updatePhone(Users userInfo) {
		return mapper.updatePhone(userInfo);
	}

	@Override
	public boolean setLastLoginTime(Users user) {
		return mapper.updateLastLoginTime(user)>0;
	}

	@Override
	public List<CommunityUserIM> selectCommunityUserList(Long id) {
		return mapper.selectCommunityUserList(id);
	}

	@Override
	public boolean chagneCommunity(Users userInfo) {
		return mapper.chagneCommunity(userInfo)>0;
	}

	@Override
	public List<Users> selectUserByRoom(Long communityId, String houseNumber) {
		Map<String, Object> map = new HashMap<>();
		map.put("communityId",communityId);
		map.put("houseNumber",houseNumber);
		return mapper.selectUserByRoom(map);
	}

	@Override
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	public BaseResponse chargePhone(ChangePhoneRequest req) {
		Users hasUser = mapper.getByMobilePhone(req.getPhone());
		if (hasUser != null) {
			return new BaseResponse(1, "该号码已注册");
		}
		Subject subject = SecurityUtils.getSubject();
		Users userInfo = null;
		
		userInfo = mapper.getByUserName(subject.getPrincipal().toString());
		userInfo.setMobilePhone(req.getPhone());
		// 判断验证码是否正确
		boolean smscode = smsService.verifyCode(req.getCode(), req.getPhone(),
				SmsValidateType.changePhone.getIntValue());
		if (!smscode) {
			return new BaseResponse(1, "验证码错误或过期");
		}
		boolean isok = mapper.updatePhone(userInfo);
		_logger.info("用户:" + userInfo.getId() + "修改手机号码的结果是:" + isok);
		return BaseResponse.OperateSuccess;
	}

	@Override
	public List<String> getUserIdByCommunityId(Long id) {
		return mapper.getUserIdByCommunityId(id);
	}
	
	@Override
	public List<String> getUserIdByCommunityIdAndThemeId(Map<String, Object> map) {
		return mapper.getUserIdByCommunityIdAndThemeId(map);
	}
	
	
	@Override
	public boolean modifyUserStatus(Users user) {
		//1.修改状态
		boolean flag=mapper.modifyUserStatus(user)>0;
		
		//2. 插入禁用用户原因表  用户管理也有个禁用用户入口
		if(user.getStatus().getCode()==1){//禁用用户 新增记录
			DisableUser record=new DisableUser();
			record.setUserId(user.getId());
			record.setReason(user.getReason());
			record.setCreateTime(new Date());
			disableUserService.insertSelective(record);
		}else{//启用用户 删除记录
			disableUserService.deleteByUserId(user.getId());
		}
	
		return flag;
	}

	@Override
	public int getRegisterCount(Long communityId) {
		return mapper.getRegisterCount(communityId);
	}
	
	
	@Override
	public int getRegisterCountByDate(Map<String, Object> map) {
		return mapper.getRegisterCountByDate(map);
	}
	
	@Override
	public List<UserStatistics> getRegisteCommunityUserList(Map<String, Object> map) {
		return mapper.getRegisteCommunityUserList(map);
	}
	
	@Override
	public List<CommunityOnlineIM> selectOnlineUserByCommunityId(Long id) {
		return mapper.selectOnlineUserByCommunityId(id);
	}
	
	@Override
	public UserStatistics getRegisterCountByCharts(Map<String, Object> map) {
		return mapper.getRegisterCountByCharts(map);
	}
	@Override
	public UserStatistics getActiveUserStatisticss(Map<String, Object> map) {
		return mapper.getActiveUserStatisticss(map);
	}
	
	@Override
	public UserStatistics getActiveUserStatisticssByYear(Map<String, Object> map) {
		return mapper.getActiveUserStatisticssByYear(map);
	}
	
	@Override
	public UserStatistics getRegisterUserStatisticss(Map<String, Object> map) {
		return mapper.getRegisterUserStatisticss(map);
	}
	
	@Override
	public UserStatistics getRegisterUserStatisticssByYear(Map<String, Object> map) {
		return mapper.getRegisterUserStatisticssByYear(map);
	}

	@Override
	public Users checkBankUserExsit(String userName) {
		return mapper.checkBankUserExsit(userName);
	}
	
}
