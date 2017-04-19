package com.linle.parkingspace.mapper;

import com.linle.entity.sys.ParkingSpace;

import component.BaseMapper;

public interface ParkingSpaceMapper extends BaseMapper<ParkingSpace>{
	
	ParkingSpace selectByGarageId(Long id);

	int del(Long id);

}