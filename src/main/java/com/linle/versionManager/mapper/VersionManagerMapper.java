package com.linle.versionManager.mapper;

import java.util.Map;

import com.linle.versionManager.model.VersionManager;

import component.BaseMapper;

public interface VersionManagerMapper extends BaseMapper<VersionManager>{

	VersionManager selectBySoftwareFlag(Map<String, Object> map);
 
}