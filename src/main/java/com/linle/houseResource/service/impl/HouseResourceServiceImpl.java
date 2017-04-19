package com.linle.houseResource.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.HouseResource;
import com.linle.houseResource.mapper.HouseResourceMapper;
import com.linle.houseResource.service.HouseResourceService;
import com.linle.mobileapi.v1.model.HouseResouceVO;

@Service("HouseResourceService")
@Transactional
public class HouseResourceServiceImpl extends CommonServiceAdpter<HouseResource> implements HouseResourceService {

	@Autowired
	private HouseResourceMapper mapper;
	
	@Override
	public List<HouseResouceVO> getHouseResouceList(Map<String, Object> map) {
		return mapper.getHouseResouceList(map);
	}

	@Override
	public Page<HouseResource> findAllHouseResource(Page<HouseResource> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public boolean delHouseResource(Long uid, Long hid) {
		Map<String, Object> map = new HashMap<>();
		map.put("uid", uid);
		map.put("id", hid);
		return mapper.delHouseResource(map)>0;
	}


}
