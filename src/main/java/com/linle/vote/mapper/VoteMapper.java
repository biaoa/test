package com.linle.vote.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linle.vote.model.Vote;

import component.BaseMapper;

public interface VoteMapper extends BaseMapper<Vote>{
	public void updateIsDelById(long id) ;
	
	public Vote getVoteById(HashMap<Object, Object> map);
	
	public List<Vote> getAllDataForApi(HashMap<Object, Object> map);

	public List<Vote> LoadVoteList(Map<String, Object> map);

	public int getVoteCountByCommunityId(Object object);
	
	public List<Vote> getAllDataByStatus(Map<String, Object> map);
	
	public void updateStatusById(Map<String, Object> map) ;

	public List<Vote> getRemindVoteList(Map<String, Object> map);

	public void updatePushFlgById(long id);
}