package com.linle.priceSetting.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.priceSetting.model.PriceSetting;

public interface PriceSettingService  extends BaseService<PriceSetting>  {
	
	public Page<PriceSetting> getAllForPage(Page<PriceSetting> page);

	PriceSetting selectByType(long communityId, String type, Integer floor);
	
	
	
}
