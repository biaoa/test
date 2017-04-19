package com.linle.cSupport.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CSupport;

import component.BaseMapper;

public interface CSupportMapper extends BaseMapper<CSupport>{
	public int selectByTopicIdAndUserid(Map<String, Object> map);

	public List<CSupport> selectSupportUsers(long topicId);
}