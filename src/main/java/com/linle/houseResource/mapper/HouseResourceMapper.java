package com.linle.houseResource.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.HouseResource;
import com.linle.mobileapi.v1.model.HouseResouceVO;

import component.BaseMapper;

public interface HouseResourceMapper extends BaseMapper<HouseResource>{

	List<HouseResouceVO> getHouseResouceList(Map<String, Object> map);

	int delHouseResource(Map<String, Object> map);
}