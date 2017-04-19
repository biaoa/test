package com.linle.shopMainType.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopMainType;
import com.linle.mobileapi.v1.model.Sort;
import com.linle.mobileapi.v1.model.SortSales;


public interface ShopMainTypeService extends BaseService<ShopMainType> {

	Page<ShopMainType> findAllMainType(Page<ShopMainType> page);
	
	List<ShopMainType> findAllMainType();

	List<Sort> getSortList(Map<String, Object> map);
	/**
	 * 
	 * @Description 获得各大分类下的销量
	 * @param map
	 * @return List<SortSales>
	 * $
	 */
	List<SortSales> selectTypeSales(Map<String, Object> map);
	
	/**
	 * 
	 * @Description 根据type名称查询分类
	 * @param typeName
	 * @return ShopMainType
	 * $
	 */
	ShopMainType selectByTypeName(String typeName);

}
