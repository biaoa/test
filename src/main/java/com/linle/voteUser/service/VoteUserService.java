package com.linle.voteUser.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.voteUser.model.VoteUser;

public interface VoteUserService extends BaseService<VoteUser> {
	public List<VoteUser> getVoteUsersById(Long id);
	
	public long selectCountByThemeIdAndUserId(Map<String, Object> map);
	
	public long selectCountByThemeIdAndAddressDetails(Map<String, Object> map);
	
}
