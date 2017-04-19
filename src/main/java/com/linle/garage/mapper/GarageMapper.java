package com.linle.garage.mapper;

import java.util.List;

import com.linle.common.util.Page;
import com.linle.entity.sys.Garage;
import com.linle.entity.vo.SpaceOrder;

import component.BaseMapper;

public interface GarageMapper extends BaseMapper<Garage>{
	//根据小区ID 获得车库列表
	List<Garage> getGarageList(Long id);

	List<SpaceOrder> getAllGarageOrder(Page<SpaceOrder> page);

	int del(Garage garage);
}