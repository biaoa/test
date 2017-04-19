package com.linle.voteOptions.mapper;

import java.util.HashMap;
import java.util.List;

import com.linle.voteOptions.model.VoteOptions;

import component.BaseMapper;

public interface VoteOptionsMapper extends BaseMapper<VoteOptions>{
	public List<VoteOptions> getVoteOptionsById(HashMap<String, Object> map);
	
	void deleteByThemeId(long themeId);
}