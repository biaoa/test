package com.linle.shop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.statistics.UserStatistics;
import com.linle.entity.sys.Shop;
import com.linle.mobileapi.v1.model.ShopInfoVO;
import com.linle.mobileapi.v1.model.ShopItem;
import com.linle.mobileapi.v1.model.ShopVO;

public interface ShopService extends BaseService<Shop> {

	Page<Shop> findAllShops(Page<Shop> page);

	boolean createShop(Shop shop);

	Shop selectByUserID(Long id);

	List<ShopItem> getShopListForAPI(Map<String, Object> map);
	
	//API 获得商铺信息
	ShopInfoVO selectShopInfoAPI(Map<String, Object> map);
	//获得店铺销量（订单数）
	int selectShopSales(Map<String, Object> map);
	//获得店铺销量（按卖出商品数）
	int selectShopSaleCommunity(Map<String, Object> map);
	//获得热销店铺
	List<ShopItem> getHomeAD(Map<String, Object> map);
	//获得商家总收益
	BigDecimal getShopTotalIncome(Long id);
	//获得商家待结算金额
	BigDecimal getShopWaitIncome(Long id);
	//获得商家可提现余额
	BigDecimal getShopBalance(Long id);

	int getShopCountByDate(Map<String, Object> map);

	List<UserStatistics> getShopListByDate(Map<String, Object> map);
	/**
	 * 
	 * @Description 获得商家提现总金额
	 * @param id
	 * @return BigDecimal
	 * $
	 */
	BigDecimal getShopToatlFunds(Long id);

	List<Shop> getAllActivityShop(long communityId);
	
}
