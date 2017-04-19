package com.linle.communitySuggestions.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.AdviceListVO;
import com.linle.mobileapi.v1.model.AdviceVO;

public interface CommunitySuggestionsService extends BaseService<CommunitySuggestions>{

	Page<CommunitySuggestions> findAllCommunitySuggestions(Page<CommunitySuggestions> page);
	
	//app 获得意见反馈列表
	List<AdviceListVO> getAdviceForAPI(Map<String, Object> map);
	
	AdviceVO selectById(long id);

	CommunitySuggestions getOneNewAdvice(long communityId, long begin);

	long getCountUnAdvice(long communityId);

	BaseResponse updateStatus(Map<String, Object> map);
	//物业删除意见反馈
	boolean communityDel(CommunitySuggestions communitySuggestions);

}
