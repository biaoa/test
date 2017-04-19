package com.linle.shop.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.Shop;
import com.linle.mobileapi.v1.model.GetAllGoods;
import com.linle.mobileapi.v1.model.ShopInfoVO;
import com.linle.mobileapi.v1.model.ShopItem;
import com.linle.mobileapi.v1.model.ShopVO;

import component.BaseMapper;

public interface ShopMapper extends BaseMapper<Shop>{

	Shop selectByUserID(Long id);

	List<ShopItem> getShopListForAPI(Map<String, Object> map);

	GetAllGoods getAllCommodityForAPI(Map<String, Object> map);

	ShopInfoVO selectShopInfoAPI(Map<String, Object> map);
	//获得店铺销量(订单)  shopId,year,month(如果查全部 则不传year,month)
	int selectShopSales(Map<String, Object> map);
	//获得店铺销量(商品数量)  shopId,year,month(如果查全部 则不传year,month)
	int selectShopSaleCommunity(Map<String, Object> map);
	
	List<ShopItem> getHomeAD(Map<String, Object> map);
	
	BigDecimal getShopTotalIncome(Long id);
	
	BigDecimal getShopWaitIncome(Long id);

	int getShopCountByDate(Map<String, Object> map);

	List<UserStatistics> getShopListByDate(Map<String, Object> map);

	BigDecimal getShopToatlFunds(Long id);

	List<Shop> getAllActivityShop(Object map);
}