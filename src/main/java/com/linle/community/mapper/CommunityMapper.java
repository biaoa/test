package com.linle.community.mapper;

import java.util.List;
import java.util.Map;

import com.linle.community.model.Community;
import com.linle.community.model.CommunityVo;
import com.linle.entity.statistics.Select2Statistics;
import com.linle.entity.statistics.UserStatistics;
import com.linle.mobileapi.v1.model.CommunityListVO;

import component.BaseMapper;

public interface CommunityMapper extends BaseMapper<Community> {

	Community getCommunityByuserID(Long id);

	List<CommunityListVO> getCommunityListByCityName(String cityName);

	List<Community> getCommunityByAgency(Long id);

	List<Community> getCommunityByPresident(Long presidentId);

	List<Community> selectAllCommunity();

	List<Community> getNoTemplateCommunity();

	List<CommunityVo> getRegionAndCommunityListByPresident(Long presidentId);

	int getCommunityCountByDate(Map<String, Object> map);

	List<UserStatistics> getCommunityListByDate(Map<String, Object> map);

	List<Select2Statistics> selectAllCommunityText();

	int updateWithdrawalFee(Community community);

 
}