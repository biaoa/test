package com.linle.communitySuggestionsComment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.BaseServiceAdpter;
import com.linle.communitySuggestionsComment.mapper.CommunitySuggestionsCommentMapper;
import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;
import com.linle.communitySuggestionsComment.service.CommunitySuggestionsCommentService;
import com.linle.mobileapi.v1.model.AdviceListVO;
@Service
@Transactional
public class CommunitySuggestionsCommentServiceImpl extends BaseServiceAdpter<CommunitySuggestionsComment> 
implements CommunitySuggestionsCommentService {
	
	@Autowired
	private CommunitySuggestionsCommentMapper mapper;
	
	@Override
	public List<CommunitySuggestionsComment> getSuggestionsDetailForApi(Map<String, Object> map){
		return mapper.getSuggestionsDetailForApi(map);
	}
	
	@Override
	public List<CommunitySuggestionsComment> selectCommentListById(Map<String, Object> map) {
		return mapper.selectCommentListById(map);
	}
}
