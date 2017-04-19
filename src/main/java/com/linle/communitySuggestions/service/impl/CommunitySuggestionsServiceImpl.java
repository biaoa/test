package com.linle.communitySuggestions.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.communitySuggestions.mapper.CommunitySuggestionsMapper;
import com.linle.communitySuggestions.service.CommunitySuggestionsService;
import com.linle.entity.sys.CommunitySuggestions;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.AdviceListVO;
import com.linle.mobileapi.v1.model.AdviceVO;

@Service
@Transactional
public class CommunitySuggestionsServiceImpl extends CommonServiceAdpter<CommunitySuggestions>
		implements CommunitySuggestionsService {
	
	@Autowired
	private CommunitySuggestionsMapper mapper;

	@Override
	public Page<CommunitySuggestions> findAllCommunitySuggestions(Page<CommunitySuggestions> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<AdviceListVO> getAdviceForAPI(Map<String, Object> map) {
		return mapper.getAdviceForAPI(map);
	}
	
	@Override
	public AdviceVO selectById(long id){
		return mapper.selectById(id);
	}
	
	@Override
	public long getCountUnAdvice(long communityId){
		return mapper.getCountUnAdvice(communityId);
	}
	
	
	@Override
	public CommunitySuggestions getOneNewAdvice(long communityId,long begin){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("communityId", communityId);
		map.put("begin", begin);
		return mapper.getOneNewAdvice(map);
	}
	
	@Override
	public BaseResponse updateStatus(Map<String, Object> map) {
		boolean isok = mapper.updateStatus(map)>0;
		return isok?BaseResponse.OperateSuccess:BaseResponse.OperateFail;
	}

	@Override
	public boolean communityDel(CommunitySuggestions communitySuggestions) {
		return mapper.communityDel(communitySuggestions)>0;
	}
}
