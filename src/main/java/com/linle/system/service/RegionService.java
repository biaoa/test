package com.linle.system.service;

import java.util.List;

import com.linle.entity.sys.SysRegion;
import com.linle.mobileapi.v1.model.ProvinceVO;

public interface RegionService {
	public SysRegion getById(Long id);
	public List<SysRegion> getCitys(SysRegion parent);
	public List<SysRegion> getCountys(SysRegion parent);
	public List<SysRegion> getProvinces();
	
	public List<ProvinceVO> getAllData();
}
