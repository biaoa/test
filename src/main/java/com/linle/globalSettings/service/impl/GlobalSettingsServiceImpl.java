package com.linle.globalSettings.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.common.base.BaseServiceAdpter;
import com.linle.common.util.Page;
import com.linle.globalSettings.mapper.GlobalSettingsMapper;
import com.linle.globalSettings.model.GlobalSettings;
import com.linle.globalSettings.service.GlobalSettingsService;

@Service
@Transactional
public class GlobalSettingsServiceImpl extends BaseServiceAdpter<GlobalSettings> implements GlobalSettingsService {

	@Autowired
	private GlobalSettingsMapper mapper;
	
	@Override
	public Page<GlobalSettings> getAllForPage(Page<GlobalSettings> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}
	
	@Override
	public GlobalSettings selectBySettingKey(long communityId,String settingKey){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("communityId", communityId);
		map.put("settingKey", settingKey);
		return mapper.selectBySettingKey(map);
	}
	
	
}
