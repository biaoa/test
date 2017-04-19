package com.linle.shopChildType.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopChildType;

public interface ShopChildTypeService extends BaseService<ShopChildType> {

	Page<ShopChildType> findAllChildType(Page<ShopChildType> page);
	
	/**
	 * 
	 * @Description 根据主分类ID 查询子分类
	 * @param mid
	 * @return List<ShopChildType>
	 * $
	 */
	List<ShopChildType> selectByMain(Long mid);

	ShopChildType selectByTypeName(String typeName);

}
