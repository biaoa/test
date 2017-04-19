package com.linle.shopMainType.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.ShopMainType;
import com.linle.mobileapi.v1.model.Sort;
import com.linle.mobileapi.v1.model.SortSales;

import component.BaseMapper;

public interface ShopMainTypeMapper extends BaseMapper<ShopMainType> {

	List<ShopMainType> findAllMainType();

	List<Sort> getSortList(Map<String, Object> map);

	List<SortSales> selectTypeSales(Map<String, Object> map);

	ShopMainType selectByTypeName(String typeName);
}