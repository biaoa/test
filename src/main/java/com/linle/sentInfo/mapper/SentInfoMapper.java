package com.linle.sentInfo.mapper;

import com.linle.entity.sys.SentInfo;

import component.BaseMapper;

public interface SentInfoMapper extends BaseMapper<SentInfo>{

	int updateStatus(SentInfo sent);

	int cancelSent(String sentId);
}