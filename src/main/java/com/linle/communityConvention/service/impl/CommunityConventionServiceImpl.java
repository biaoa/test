package com.linle.communityConvention.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.communityConvention.mapper.CommunityConventionMapper;
import com.linle.communityConvention.model.CommunityConvention;
import com.linle.communityConvention.service.CommunityConventionService;

@Service
@Transactional
public class CommunityConventionServiceImpl extends CommonServiceAdpter<CommunityConvention>
		implements CommunityConventionService {

	@Autowired
	private CommunityConventionMapper mapper;

}
