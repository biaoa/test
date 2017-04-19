package com.linle.housekeeperAD.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.Housekeeperad;
import com.linle.housekeeperAD.mapper.HousekeeperadMapper;
import com.linle.housekeeperAD.service.HousekeeperadService;
import com.linle.mobileapi.v1.model.BannerVO;

@Service
@Transactional
public class HousekeeperadServiceImpl extends CommonServiceAdpter<Housekeeperad> implements HousekeeperadService {
	
	@Autowired
	private HousekeeperadMapper mapper;

	@Override
	public Page<Housekeeperad> getAllHousekeeperAD(Page<Housekeeperad> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public List<BannerVO> getHousekeeperadForAPI(Map<String, Object> map) {
		return mapper.getHousekeeperadForAPI(map);
	}

}
