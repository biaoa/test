package com.linle.vote.model;

import com.linle.mobileapi.base.BaseResponse;

public class VoteResponse extends BaseResponse{
	
	private Vote vote;

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	
}