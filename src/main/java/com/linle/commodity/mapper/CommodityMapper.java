package com.linle.commodity.mapper;

import java.util.List;

import com.linle.entity.sys.Commodity;
import com.linle.entity.vo.HotCommodityVO;
import com.linle.mobileapi.v1.model.ShopGoods;

import component.BaseMapper;

public interface CommodityMapper extends BaseMapper<Commodity>{
	//验证商品数量
	int verifyQuantity(ShopGoods shopGoods);
	//修改商品数量
	int updateQuantity(Commodity commodity);
	
	//根据店铺ID 获得商品数量
	int selectCommodityCount(Long id);
	//根据店铺ID 获得热销商品列表
	List<HotCommodityVO> selectHotCommodityList(Long id);

	
}