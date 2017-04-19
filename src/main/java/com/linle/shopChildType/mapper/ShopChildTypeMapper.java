package com.linle.shopChildType.mapper;

import java.util.List;

import com.linle.entity.sys.ShopChildType;

import component.BaseMapper;

public interface ShopChildTypeMapper extends BaseMapper<ShopChildType>{

	List<ShopChildType> selectByMain(Long mid);

	ShopChildType selectByTypeName(String typeName);
	
}