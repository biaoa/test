package com.linle.commodityAppMenu.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CommodityAppMenu;
import com.linle.mobileapi.v1.model.AppMenuVO;

import component.BaseMapper;

public interface CommodityAppMenuMapper extends BaseMapper<CommodityAppMenu>{

	List<CommodityAppMenu> getCommodityAppMenu(Long commodityId);

	int delCommodityAppMenu(Long commodityId);

	List<AppMenuVO> getAppMenuForAPI(Map<String, Object> map);
   
}