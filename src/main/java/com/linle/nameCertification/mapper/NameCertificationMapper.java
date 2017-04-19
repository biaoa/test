package com.linle.nameCertification.mapper;

import com.linle.entity.sys.NameCertification;

import component.BaseMapper;

public interface NameCertificationMapper extends BaseMapper<NameCertification>{

	NameCertification selectByUserId(Long uid);
}