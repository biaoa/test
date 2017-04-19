package com.linle.commodityType.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CommodityType;

import component.BaseMapper;

public interface CommodityTypeMapper extends BaseMapper<CommodityType>{

	List<CommodityType> getAllTypeByShopID(Long id);

	void update(Map map);

	long selectChildCommodityCount(Map<String, Object> params);
}