package com.linle.regionalAgency.mapper;

import com.linle.entity.sys.RegionalAgency;

import component.BaseMapper;

public interface RegionalAgencyMapper extends BaseMapper<RegionalAgency> {

	RegionalAgency selectByUserid(Long userid);
   
}