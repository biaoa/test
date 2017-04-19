package com.linle.vote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.response.AppLoadVoteListResponse;
import com.linle.vote.model.Vote;

public interface VoteService extends BaseService<Vote> {
	
	public Page<Vote> getAllData(Page<Vote> page);
	
	public BaseResponse add(Vote vote,Users user);
	
	public void updateIsDelById(long id) ;
	
	public Vote getVoteById(Long id,long userId);
	
	public BaseResponse operateVoteOptions(Vote vote,Users user);
	
	public List<Vote> getAllDataForApi(HashMap<Object, Object> map);
	
	public AppLoadVoteListResponse LoadVoteList(Map<String, Object> map);

	public List<Vote> getAllDataByStatus(Map<String, Object> map);
	
	public void updateStatusById(Map<String, Object> map) ;
	
	public List<Vote> getRemindVoteList(Map<String, Object> map);
	
	public void updatePushFlgById(long id) ;
}
