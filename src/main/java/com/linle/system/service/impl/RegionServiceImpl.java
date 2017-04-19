package com.linle.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linle.entity.enumType.RegionLevelType;
import com.linle.entity.sys.SysRegion;
import com.linle.mobileapi.v1.model.ProvinceVO;
import com.linle.system.mapper.RegionMapper;
import com.linle.system.service.RegionService;


@Service("regionService")
@Transactional
public class RegionServiceImpl implements RegionService{

	@Autowired
	private RegionMapper regionMapper;

	@Override
	public List<SysRegion> getCitys(SysRegion parent) {
		return regionMapper.findByLevel(RegionLevelType.city, parent);
	}

	@Override
	public List<SysRegion> getCountys(SysRegion parent) {
		return regionMapper.findByLevel(RegionLevelType.county, parent);
	}

	@Override
	public List<SysRegion> getProvinces() {
		return regionMapper.findByLevels(RegionLevelType.province);
	}

	@Override
	public SysRegion getById(Long id) {
		return regionMapper.getById(id);
	}

	@Override
	public List<ProvinceVO> getAllData() {
		return regionMapper.getAllData();
	}
}
