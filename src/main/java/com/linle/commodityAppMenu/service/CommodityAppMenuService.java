package com.linle.commodityAppMenu.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.entity.sys.CommodityAppMenu;
import com.linle.mobileapi.v1.model.AppMenuVO;

public interface CommodityAppMenuService extends BaseService<CommodityAppMenu> {
	
	public List<CommodityAppMenu> getCommodityAppMenu(Long commodityId);
	
	public boolean delCommodityAppMenu(Long commodityId);
	
	public boolean addCommodityAppMenu(String menuids,Long commodityId,String sorts,Long uid);
	
	public List<AppMenuVO> getAppMenuForAPI(Map<String, Object> map);

}
