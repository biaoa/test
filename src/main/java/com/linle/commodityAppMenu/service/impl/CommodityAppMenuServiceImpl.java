package com.linle.commodityAppMenu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.commodityAppMenu.mapper.CommodityAppMenuMapper;
import com.linle.commodityAppMenu.service.CommodityAppMenuService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.StringUtil;
import com.linle.entity.sys.CommodityAppMenu;
import com.linle.mobileapi.v1.model.AppMenuVO;

@Service
@Transactional
public class CommodityAppMenuServiceImpl extends CommonServiceAdpter<CommodityAppMenu> implements CommodityAppMenuService {

	@Autowired
	private CommodityAppMenuMapper mapper;

	@Override
	public List<CommodityAppMenu> getCommodityAppMenu(Long commodityId) {
		return mapper.getCommodityAppMenu(commodityId);
	}

	@Override
	public boolean delCommodityAppMenu(Long commodityId) {
		return mapper.delCommodityAppMenu(commodityId)>0;
	}

	@Override
	public boolean addCommodityAppMenu(String menuids, Long commodityId,String sorts,Long uid) {
		String menuArrays[] = menuids.split(",");
		String sortArrays[] = StringUtil.arrayTrim(sorts.split(","));
		for (int i=0;i<menuArrays.length;i++) {
			CommodityAppMenu menu = new CommodityAppMenu();
			menu.setCommodityId(commodityId);
			menu.setMenuId(Long.valueOf(menuArrays[i]));
			menu.setSort(Integer.valueOf(sortArrays[i]));
			menu.setCreateDate(new Date());
			menu.setuId(uid);
			mapper.insertSelective(menu);
		}
		return true;
	}

	@Override
	public List<AppMenuVO> getAppMenuForAPI(Map<String, Object> map) {
		return mapper.getAppMenuForAPI(map);
	}

}
