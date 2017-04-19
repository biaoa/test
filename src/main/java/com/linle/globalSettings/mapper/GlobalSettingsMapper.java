package com.linle.globalSettings.mapper;

import java.util.Map;

import com.linle.globalSettings.model.GlobalSettings;

import component.BaseMapper;

public interface GlobalSettingsMapper extends BaseMapper<GlobalSettings>{

	GlobalSettings selectBySettingKey(Map<String, Object> map);
  
}