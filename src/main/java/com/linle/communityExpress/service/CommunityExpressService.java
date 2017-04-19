package com.linle.communityExpress.service;

import java.util.List;

import com.linle.common.base.BaseService;
import com.linle.common.util.Page;
import com.linle.entity.sys.CommunityExpress;
import com.linle.mobileapi.v1.model.ExpressListVO;

public interface CommunityExpressService extends BaseService<CommunityExpress> {

	Page<CommunityExpress> findAllExpress(Page<CommunityExpress> page);

	List<ExpressListVO> getExpressList(Long id);

}
