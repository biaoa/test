package com.linle.communityPresident.mapper;

import com.linle.entity.sys.CommunityPresident;

import component.BaseMapper;

public interface CommunityPresidentMapper extends BaseMapper<CommunityPresident>{

	CommunityPresident selectByUserId(Long uid);
}