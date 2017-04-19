package com.linle.voteUser.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateKit;
import com.linle.voteUser.mapper.VoteUserMapper;
import com.linle.voteUser.model.VoteUser;
import com.linle.voteUser.service.VoteUserService;

@Service
@Transactional
public class VoteUserServiceImpl extends CommonServiceAdpter<VoteUser> implements VoteUserService {
	@Autowired
	private VoteUserMapper mapper;
	
	//获得参与投票成员
	public List<VoteUser> getVoteUsersById(Long id) {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("themeId", id);
		List<VoteUser> list=mapper.getVoteUsersById(map);
		for (VoteUser voteUser : list) {
			voteUser.setCreateDateStr(DateKit.friendlyFormat(voteUser.getCreateDate()));
		}
	    return list;
	}
	
	public long selectCountByThemeIdAndUserId(Map<String, Object> map){
		return mapper.selectCountByThemeIdAndUserId(map);
	}
	
	public long selectCountByThemeIdAndAddressDetails(Map<String, Object> map){
		return mapper.selectCountByThemeIdAndAddressDetails(map);
	}
	
}
