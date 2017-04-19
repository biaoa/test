package com.linle.voteUser.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linle.voteUser.model.VoteUser;

import component.BaseMapper;

public interface VoteUserMapper extends BaseMapper<VoteUser>{
	
	public List<VoteUser> getVoteUsersById(HashMap<String, Object> map);

	public long selectCountByThemeIdAndUserId(Map<String, Object> map);

	public long selectCountByThemeIdAndAddressDetails(Map<String, Object> map);
	
	
}