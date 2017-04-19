package com.linle.communityPresident.service;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommunityPresident;
import com.linle.entity.sys.Users;
import com.linle.mobileapi.base.BaseResponse;

public interface CommunityPresidentService extends BaseService<CommunityPresident> {

	Page<CommunityPresident> findAllCommunityPresident(Page<CommunityPresident> page);
	//添加小区社长
	BaseResponse addCommunityPresident(CommunityPresident communityPresident);
	
	//通过用户ID查询社长信息
	CommunityPresident selectByUserId(Long uid);
		
	//通过社长 获得二级代理ID
	public Users getlevel2Proxy(CommunityPresident president);
	
	//通过社长 获得一级代理ID
	public Users getlevel1Proxy(CommunityPresident president);
	
	//通过小区用户ID 获得对应的社长信息
	CommunityPresident selectByCommunityUserId(Long id);
	

}
