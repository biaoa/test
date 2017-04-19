package com.linle.commodityType.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommodityType;
import com.linle.mobileapi.base.BaseResponse;

public interface CommodityTypeService extends BaseService<CommodityType> {

	Page<CommodityType> findAllCommodityType(Page<CommodityType> page);

	List<CommodityType> getAllTypeByShopID(Long id);
	
	public BaseResponse del(long id,long shopId);

}
