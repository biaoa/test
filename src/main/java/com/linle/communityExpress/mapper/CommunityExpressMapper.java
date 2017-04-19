package com.linle.communityExpress.mapper;

import java.util.List;

import com.linle.entity.sys.CommunityExpress;
import com.linle.mobileapi.v1.model.ExpressListVO;

import component.BaseMapper;

public interface CommunityExpressMapper extends BaseMapper<CommunityExpress>{

	List<ExpressListVO> getExpressList(Long id);
}