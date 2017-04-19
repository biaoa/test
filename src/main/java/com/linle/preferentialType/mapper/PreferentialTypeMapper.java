package com.linle.preferentialType.mapper;

import java.util.List;

import com.linle.entity.sys.PreferentialType;
import com.linle.mobileapi.v1.model.Privilege;

import component.BaseMapper;

public interface PreferentialTypeMapper extends BaseMapper<PreferentialType>{

	List<PreferentialType> getallType();

	List<Privilege> getAllTypeForAPI();
}