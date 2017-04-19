package com.linle.commodity.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.Commodity;
import com.linle.entity.vo.HotCommodityVO;
import com.linle.mobileapi.v1.model.GetAllGoods;
import com.linle.mobileapi.v1.model.ShopGoods;

public interface CommodityService extends BaseService<Commodity> {

	Page<Commodity> findAllCommodity(Page<Commodity> page);

	GetAllGoods getAllCommodityForAPI(Map<String, Object> map);
	
	//验证库存数量
	int verifyQuantity(ShopGoods shopGoods);
	//下单后 修改商品数量
	int updateQuantity(Commodity commodity);
	//根据店铺ID 获得商品数量
	int selectCommodityCount(Long id);
	//根据店铺ID 获得热销商品列表
	List<HotCommodityVO> selectHotCommodityList(Long id);

}
