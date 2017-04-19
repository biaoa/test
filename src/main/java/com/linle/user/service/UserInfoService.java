package com.linle.user.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseLoginToken;
import com.linle.common.util.Page;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.CommunityEmployee;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.UserInfoVO;
import com.linle.mobileapi.v1.request.ChangePhoneRequest;
import com.linle.mobileapi.v1.request.RegisteRequest;
import com.linle.user.model.CommunityOnlineIM;
import com.linle.user.model.CommunityUserIM;



public interface UserInfoService {
	/**
	 * 注册用户
	 * @param userInfo
	 * @return
	 */
	public boolean addUserInfo(Users userInfo);
	/**
	 * 根据手机号查询用户
	 * 
	 * @param userName
	 * @return
	 */
	public Users findUserInfoByPhone(String mobilePhone);
	/**
	 * 分页查询所有系统用户
	 * @param page
	 * @return
	 */
	public List<Users> findAllUsers(Page<Users> page);
	/**
	 * 更新最后登录时间
	 * @param userInfo
	 * @return
	 */
	public Boolean updateLoginTime(Users userInfo);
	
	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 * @return
	 */
	public Users findUserInfoByUserName(String userName);
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public Boolean updatePassword(Users user);
	/**
	 * 验证用户密码
	 * @param password
	 * @param userId
	 * @return
	 */
	public Boolean checkPassword(String password,Long userId);
	
	/**
	 * 删除用户
	 * @param uid
	 * @return
	 */
	public int delAccount(Long uid);
	
	public boolean checkUserExsit(String userName);
		/**
	 * 修改密码操作(手机客户端)
	 * 
	 * @param password
	 * @return
	 */
	public boolean updatePasswordAndToken(Users userInfo);
	
	/**
	 * 修改小区员工时，将同步修改用户表的相关信息
	 * @param communityEmployee
	 * @return
	 */
	public boolean updateUserFromCommunityEmployee(CommunityEmployee communityEmployee);
	
	/**
	 * 忘记密码的修改密码操作
	 * 
	 * @param mobilePhone
	 * @param password
	 * @return
	 */
	public boolean forgotPassword(String mobilePhone, String password);
	
	/**
	 * 通过用户ID得到用户信息
	 * @param id
	 * @return
	 */
	public Users getById(Long id);
	/**
	 * 根据用户名和Email得到用户信息
	 * @param users
	 * @return
	 */
	public Users getUserinfoByUserNameAndEmail(Users users);
	/**
	 * 重置密码
	 * @param uid
	 * @return
	 */
	public int resetPassword(Long uid);
	/**
	 * 
	* @Title: regist 
	* @Description: 注册
	* @param @param user
	* @param @return    设定文件 
	* @return BaseResponse    返回类型 
	* @throws
	 */
	public BaseResponse regist(RegisteRequest req);
	/**
	 * 
	* @Title: modifyUserLogo 
	* @Description: 修改用户头像 
	* @param @param user
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean modifyUserLogo(Users user);
	/**
	 * 
	* @Title: login 
	* @Description: 登陆
	* @param @param token    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void login(BaseLoginToken token);
	
	public boolean updateNameOrSex(Users user);
	/**
	 * 
	* @Description: 更新用户token
	* @param @param user
	* @param @return
	* @return boolean
	 */
	public boolean updateUserToken(Users user);
	/**
	 * 
	* @Description: 根据用户ID 获得昵称 和头像 for 融云
	* @param @param uid
	* @param @return
	* @return UserInfoVO
	 */
	public UserInfoVO getUserInfoForAPI(Long uid);
	/**
	 * 
	* @Description: 修改手机号码
	* @param @param userInfo
	* @param @return
	* @return boolean
	 */
	public boolean updatePhone(Users userInfo);
	/**
	 * 
	* @Description: 设置最后登录时间
	* @param @param user
	* @param @return
	* @return boolean
	 */
	public boolean setLastLoginTime(Users user);
	//根据小区ID 获得用户列表For IM
	public List<CommunityUserIM> selectCommunityUserList(Long id);
	//修改小区
	public boolean chagneCommunity(Users userInfo);
	//根据小区ID 和房号 得到用户列表
	public List<Users> selectUserByRoom(Long communityId, String houseNumber);
	
	public void logout();
	
	public BaseResponse chargePhone(ChangePhoneRequest req);
	
	//通过小区ID 获得用户列表 融云发送消息用到
	public List<String> getUserIdByCommunityId(Long id);
	
	//修改用户状态
	public boolean modifyUserStatus(Users user);
	
	/**
	 * 
	 * @Description 获得小区的注册用户数
	 * @param communityId
	 * @return int
	 * $
	 */
	public int getRegisterCount(Long communityId);
	
	public List<CommunityOnlineIM> selectOnlineUserByCommunityId(Long id);
	
	public List<String> getUserIdByCommunityIdAndThemeId(Map<String, Object> map);
	List<Users> getCommunityUsers(Page<Users> page);
	
	int getRegisterCountByDate(Map<String, Object> map);
	
	List<UserStatistics> getRegisteCommunityUserList(Map<String, Object> map);
	
	UserStatistics getRegisterCountByCharts(Map<String, Object> map);
	UserStatistics getActiveUserStatisticss(Map<String, Object> map);
	UserStatistics getActiveUserStatisticssByYear(Map<String, Object> map);
	UserStatistics getRegisterUserStatisticss(Map<String, Object> map);
	UserStatistics getRegisterUserStatisticssByYear(Map<String, Object> map);
	/**
	 * 
	 * @Description 绑定提现银行卡时验证用户是否存在(目前只验证了物业，小区，商家，代理商，社长)
	 * @param userName
	 * @return Users
	 */
	public Users checkBankUserExsit(String userName);
	
}
