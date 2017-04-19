package com.linle.priceSetting.mapper;

import java.util.Map;

import com.linle.priceSetting.model.PriceSetting;

import component.BaseMapper;

public interface PriceSettingMapper extends BaseMapper<PriceSetting>{

	PriceSetting selectByType(Map<String, Object> map);

}