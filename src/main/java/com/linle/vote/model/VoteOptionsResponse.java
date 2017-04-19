package com.linle.vote.model;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.voteOptions.model.VoteOptions;

public class VoteOptionsResponse extends BaseResponse{
	
	private List<VoteOptions> voteOptionsList;

	private long voteOptionsCounts;

	public List<VoteOptions> getVoteOptionsList() {
		return voteOptionsList;
	}

	public void setVoteOptionsList(List<VoteOptions> voteOptionsList) {
		this.voteOptionsList = voteOptionsList;
	}

	public long getVoteOptionsCounts() {
		return voteOptionsCounts;
	}

	public void setVoteOptionsCounts(long voteOptionsCounts) {
		this.voteOptionsCounts = voteOptionsCounts;
	}
	
	

	
}