package com.linle.communitySuggestions.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CommunitySuggestions;
import com.linle.mobileapi.v1.model.AdviceListVO;
import com.linle.mobileapi.v1.model.AdviceVO;

import component.BaseMapper;

public interface CommunitySuggestionsMapper extends BaseMapper<CommunitySuggestions> {

	List<AdviceListVO> getAdviceForAPI(Map<String, Object> map);

	AdviceVO selectById(long id);

	CommunitySuggestions getOneNewAdvice(Map<String, Object> map);

	long getCountUnAdvice(long communityId);

	int updateStatus(Map<String, Object> map);

	int communityDel(CommunitySuggestions communitySuggestions);
}