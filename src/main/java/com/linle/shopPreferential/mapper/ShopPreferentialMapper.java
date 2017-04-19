package com.linle.shopPreferential.mapper;

import java.util.List;

import com.linle.entity.sys.ShopPreferential;

import component.BaseMapper;

public interface ShopPreferentialMapper extends BaseMapper<ShopPreferential>{

	List<ShopPreferential> selectPreferentials(String[] str);
}