package com.linle.voteOptions.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.voteOptions.model.VoteOptions;

public interface VoteOptionsService extends BaseService<VoteOptions> {
	
	public List<VoteOptions> getVoteOptionsById(long themeId,long optionsId);
}
