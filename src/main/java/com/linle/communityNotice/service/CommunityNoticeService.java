package com.linle.communityNotice.service;

import java.util.List;
import java.util.Map;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.community.model.Community;
import com.linle.entity.sys.CommunityNotice;
import com.linle.mobileapi.base.BaseResponse;
import com.linle.mobileapi.v1.model.CommunityNoticeVO;

public interface CommunityNoticeService extends BaseService<CommunityNotice> {

	Page<CommunityNotice> getAllCommunityNotice(Page<CommunityNotice> page);

	BaseResponse send(Long noticeId,Community community );
	
	//社长/小区删除通知
	boolean del(Long id);
	
	//通过小区ID 获得未发送的通知或公告
	List<CommunityNotice> selectCommunityNoticeByCommunityId(Map<String, Object> map);
	
	public List<CommunityNoticeVO> getAllForAPI(Map<String, Object> map) ;

	public CommunityNotice selectByIdForAPI(Map<String, Object> map);

	List<CommunityNoticeVO> getAllPublicForAPI(Map<String, Object> map);

	
}
