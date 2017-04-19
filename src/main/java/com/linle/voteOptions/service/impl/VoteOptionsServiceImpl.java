package com.linle.voteOptions.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.DateKit;
import com.linle.voteOptions.mapper.VoteOptionsMapper;
import com.linle.voteOptions.model.VoteOptions;
import com.linle.voteOptions.service.VoteOptionsService;

@Service
@Transactional
public class VoteOptionsServiceImpl extends CommonServiceAdpter<VoteOptions> implements VoteOptionsService {
	@Autowired
	private VoteOptionsMapper mapper;
	
	//获得参与投票成员
	public List<VoteOptions> getVoteOptionsById(long themeId,long optionsId) {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("themeId", themeId);
		map.put("optionsId", optionsId);
		List<VoteOptions> list=mapper.getVoteOptionsById(map);
		for (VoteOptions voteOptions : list) {
			voteOptions.setCreateDateStr(DateKit.friendlyFormat(voteOptions.getCreateDate()));
		}
	    return list;
	}
	
}
