package com.linle.commodity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.commodity.mapper.CommodityMapper;
import com.linle.commodity.service.CommodityService;
import com.linle.common.base.CommonServiceAdpter;
import com.linle.common.util.Page;
import com.linle.entity.sys.Commodity;
import com.linle.entity.vo.HotCommodityVO;
import com.linle.mobileapi.v1.model.GetAllGoods;
import com.linle.mobileapi.v1.model.ShopGoods;
import com.linle.shop.mapper.ShopMapper;

@Service
@Transactional
public class CommodityServiceImpl extends CommonServiceAdpter<Commodity> implements CommodityService {
	
	@Autowired
	private CommodityMapper mapper;
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Override
	public Page<Commodity> findAllCommodity(Page<Commodity> page) {
		page.setResults(mapper.getAllData(page));
		return page;
	}

	@Override
	public GetAllGoods getAllCommodityForAPI(Map<String, Object> map) {
		return shopMapper.getAllCommodityForAPI(map);
	}

	@Override
	public int verifyQuantity(ShopGoods shopGoods) {
		return mapper.verifyQuantity(shopGoods);
	}

	@Override
	public int updateQuantity(Commodity commodity) {
		return mapper.updateQuantity(commodity);
	}

	@Override
	public int selectCommodityCount(Long id) {
		return mapper.selectCommodityCount(id);
	}

	@Override
	public List<HotCommodityVO> selectHotCommodityList(Long id) {
		return mapper.selectHotCommodityList(id);
	}
	

}
