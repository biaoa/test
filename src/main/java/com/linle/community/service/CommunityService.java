package com.linle.community.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.community.model.CommunityVo;
import com.linle.entity.statistics.Select2Statistics;
import com.linle.entity.statistics.UserStatistics;
import com.linle.mobileapi.v1.model.CommunityListVO;

public interface CommunityService extends BaseService<Community>{
	
	public Page<Community> findAllCommunity(Page<Community> page);

	public boolean addCommunity(Community company);

	public Community getCommunityByuserID(Long id);

	public List<CommunityListVO> getCommunityListByCityName(String cityName);
	
	/**
	 * 
	 * @Description 通过物业ID 获得 小区列表
	 * @param id
	 * @return List<Community>
	 * $
	 */
	public List<Community> getCommunityByAgency(Long id);
	
	/**
	 * 
	 * @Description 通过社长ID 获得小区列表
	 * @param presidentId
	 * @return List<Community>
	 * $
	 */
	public List<Community> getCommunityByPresident(Long presidentId);
	
	
	/**
	 * 
	 * @Description 通过社长ID 获得小区列表和区域列表
	 * @param presidentId
	 * @return List<Community>
	 * $
	 */
	public List<CommunityVo> getRegionAndCommunityListByPresident(Long presidentId) ;
	
	/**
	 * 
	 * @Description 获得所有小区
	 * @return List<Community>
	 * $
	 */
	public List<Community> selectAllCommunity();
	
	/**
	 * 
	 * @Description 获得小区的总收益
	 * @param communityId
	 * @return BigDecimal
	 * $
	 */
	public BigDecimal getCommunityIncome(Long communityId,String orderType);
	/**
	 * 
	 * @Description 获得没有模板的小区列表
	 * @return List<Community>
	 * $
	 */
	public List<Community> getNoTemplateCommunity();

	int getCommunityCountByDate(Map<String, Object> map);

	List<UserStatistics> getCommunityListByDate(Map<String, Object> map);

	List<Select2Statistics> selectAllCommunityText();
	/**
	 * 
	 * @Description 设置商家提现手续费
	 * @param community
	 * @return boolean
	 * $
	 */
	public boolean updateWithdrawalFee(Community community);
	
}
