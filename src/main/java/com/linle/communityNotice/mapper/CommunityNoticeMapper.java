package com.linle.communityNotice.mapper;

import java.util.List;
import java.util.Map;

import com.linle.entity.sys.CommunityNotice;
import com.linle.mobileapi.v1.model.CommunityNoticeVO;

import component.BaseMapper;

public interface CommunityNoticeMapper extends BaseMapper<CommunityNotice>{

	int del(Long id);

	List<CommunityNoticeVO> getAllForAPI(Map<String, Object> map);

	CommunityNotice selectByIdForAPI(Map<String, Object> map);

	List<CommunityNotice> selectCommunityNoticeByCommunityId(Map<String, Object> map);

	List<CommunityNoticeVO> getAllPublicForAPI(Map<String, Object> map);
}