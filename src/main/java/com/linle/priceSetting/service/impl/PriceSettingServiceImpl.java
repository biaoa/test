package com.linle.priceSetting.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.priceSetting.mapper.PriceSettingMapper;
import com.linle.priceSetting.model.PriceSetting;
import com.linle.priceSetting.service.PriceSettingService;
@Service
@Transactional
public class PriceSettingServiceImpl extends CommonServiceAdpter<PriceSetting>  implements PriceSettingService {
	@Autowired
	private PriceSettingMapper mapper;

	@Override
	public Page<PriceSetting> getAllForPage(Page<PriceSetting> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	@Override
	public PriceSetting selectByType(long communityId,String type, Integer floor){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("communityId", communityId);
		map.put("type", type);
		if(floor!=null){
			map.put("floor", floor);
		}
		return mapper.selectByType(map);
	}
	
	
}
