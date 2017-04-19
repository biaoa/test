package com.linle.vote.model;

import java.util.List;

import com.linle.mobileapi.base.BaseResponse;
import com.linle.voteUser.model.VoteUser;

public class VoteUserResponse extends BaseResponse{
	
	private List<VoteUser> voteUserList;
    
	private long optionCounts;

	public List<VoteUser> getVoteUserList() {
		return voteUserList;
	}

	public void setVoteUserList(List<VoteUser> voteUserList) {
		this.voteUserList = voteUserList;
	}

	public long getOptionCounts() {
		return optionCounts;
	}

	public void setOptionCounts(long optionCounts) {
		this.optionCounts = optionCounts;
	}
	
	
}