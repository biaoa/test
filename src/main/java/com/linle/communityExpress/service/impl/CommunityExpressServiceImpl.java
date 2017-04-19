package com.linle.communityExpress.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.communityExpress.mapper.CommunityExpressMapper;
import com.linle.communityExpress.service.CommunityExpressService;
import com.linle.entity.sys.CommunityExpress;
import com.linle.mobileapi.v1.model.ExpressListVO;


@Transactional
@Service
public class CommunityExpressServiceImpl extends CommonServiceAdpter<CommunityExpress> implements CommunityExpressService {

	@Autowired
	private CommunityExpressMapper mapper;
	
	@Override
	public Page<CommunityExpress> findAllExpress(Page<CommunityExpress> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<ExpressListVO> getExpressList(Long id) {
		return mapper.getExpressList(id);
	}


}
