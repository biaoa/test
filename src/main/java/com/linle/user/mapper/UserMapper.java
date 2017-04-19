package com.linle.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.linle.common.util.Page;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.v1.model.UserInfoVO;
import com.linle.user.model.CommunityOnlineIM;
import com.linle.user.model.CommunityUserIM;

import component.BaseMapper;


public interface UserMapper extends BaseMapper<Users> {

    public Users getByMobilePhone(String mobilePhone);

    public List<Users> getUsers(Page<Users> page);

    public Integer updateLoginTime(Users user);

    public Users getByUserName(String userName);

    public Users getByUserId(Long userId);

    public Integer updatePassword(Users user);

    public Integer deleteUser(Users user);










    public Integer checkUserExsit(String userName);

    /**
     * 用户审核
     *
     * @param user
     * @return
     */
    public int hatchCheck(Users user);

    public int countResourseByCenterid(Long findOrgId);

    public int countIncubatorByCenterid(Long findOrgId);

    /**
     * 通过公司ID得到用户信息
     *
     * @param id
     * @return
     */
    public Users getByepId(long id);


    public Integer forgotPassword(Users userInfo);

    public Integer binding(Users user);

    /**
     * 企业数量
     *
     * @param findOrgId
     * @return
     */
    public int countEpByCenterId(Long findOrgId);

    /**
     * 中介服务数量
     *
     * @param findOrgId
     * @return
     */
    public int countServiceNumByCenter(Long findOrgId);

    /**
     * 修改孵化器信息
     *
     * @param incubator
     * @return
     */

    /**
     * 通过用户ID查询孵化器信息
     *
     * @param userid
     * @return
     */

    /**
     * 通过需求ID 得到发出需求的用户信息
     *
     * @param requirementId
     * @return
     */
    public Users getUserByRequirementid(Long requirementId);

    /**
     * 通过创业咨询ID 得到提问题人的信息
     *
     * @param consultationId
     * @return
     */
    public Users getUserByconsultationId(Long consultationId);

    /**
     * 通过订单编号得到 订单发起人信息
     *
     * @param orderID
     * @return
     */
    public Users getUserByOrderID(Long orderID);


    /**
     * 根据用户名和Email得到用户信息
     *
     * @param users
     * @return
     */
    public Users getUserinfoByUserNameAndEmail(Users users);

    /**
     * 根据孵化器ID 得到用户信息
     */
    public Users getUserInfoByFHQID(Long id);

    @Select("SELECT * FOR UPDATE ")
    List<Users> findUsersByIncubatorId(Long incubatorId);

    @Insert("INSERT INTO user_temp_plan VALUES (#{userId},#{planId})")
    Integer insertUserTempPlan(@Param("userId") Long userId, @Param("planId") Long planId);
    
	public int modifyUserLogo(Users user);

	public int updateNameOrSex(Users user);
	
	public int updateUserToken(Users user);
	
	@Select("select id,name,logo from users where id = #{uid}")
	public UserInfoVO getUserInfoForAPI(Long uid);
	
	public boolean updatePhone(Users userInfo);
	
	public int updateLastLoginTime(Users user);
	
	/**
	 * 
	 * @Description 根据小区ID 获得聊天用户列表
	 * @param id
	 * @return List<CommunityUserIM>
	 * $
	 */
	public List<CommunityUserIM> selectCommunityUserList(Long id);
	//修改小区
	public int chagneCommunity(Users userInfo);
	//根据小区ID，房号获得用户列表
	public List<Users> selectUserByRoom(Map<String, Object> map);
	
	public List<String> getUserIdByCommunityId(Long id);
	
	//修改用户状态
	public int modifyUserStatus(Users user);
	
	public int getRegisterCount(Long communityId);

	public List<CommunityOnlineIM> selectOnlineUserByCommunityId(Long id);

	public List<String> getUserIdByCommunityIdAndThemeId(Map<String, Object> map);

	public boolean updateUserFromCommunityEmployee(Users user);

	public List<Users> getCommunityUsers(Page<Users> page);

	public int getRegisterCountByDate(Map<String, Object> map);

	public List<UserStatistics> getRegisteCommunityUserList(Map<String, Object> map);

	public UserStatistics getRegisterCountByCharts(Map<String, Object> map);

	public UserStatistics getActiveUserStatisticss(Map<String, Object> map);

	public UserStatistics getActiveUserStatisticssByYear(Map<String, Object> map);

	public UserStatistics getRegisterUserStatisticss(Map<String, Object> map);

	public UserStatistics getRegisterUserStatisticssByYear(Map<String, Object> map);

	public Users checkBankUserExsit(String userName);
}
