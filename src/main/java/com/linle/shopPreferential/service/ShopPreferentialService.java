package com.linle.shopPreferential.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.ShopPreferential;

public interface ShopPreferentialService extends BaseService<ShopPreferential> {

	Page<ShopPreferential> findAllPreferential(Page<ShopPreferential> page);
	
	//查询根据ids 查询优惠列表
	List<ShopPreferential> selectPreferentials(String[] ids);

}
