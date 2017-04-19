package com.linle.communitySuggestionsComment.mapper;

import java.util.List;
import java.util.Map;

import com.linle.communitySuggestionsComment.model.CommunitySuggestionsComment;

import component.BaseMapper;

public interface CommunitySuggestionsCommentMapper extends BaseMapper<CommunitySuggestionsComment>{

	List<CommunitySuggestionsComment> getSuggestionsDetailForApi(Map<String, Object> map);

	List<CommunitySuggestionsComment> selectCommentListById(Map<String, Object> map);
   
}