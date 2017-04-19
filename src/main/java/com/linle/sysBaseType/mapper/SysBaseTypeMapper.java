package com.linle.sysBaseType.mapper;

import java.util.List;
import java.util.Map;

import com.linle.sysBaseType.model.SysBaseType;

import component.BaseMapper;

public interface SysBaseTypeMapper extends BaseMapper<SysBaseType> {

	String getTypeNameByModule(Map<String, Object> map);

}