package com.linle.housekeeperAD.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.Housekeeperad;
import com.linle.mobileapi.v1.model.BannerVO;

import component.BaseMapper;

public interface HousekeeperadMapper extends BaseMapper<Housekeeperad> {

	List<BannerVO> getHousekeeperadForAPI(Map<String, Object> map);

}